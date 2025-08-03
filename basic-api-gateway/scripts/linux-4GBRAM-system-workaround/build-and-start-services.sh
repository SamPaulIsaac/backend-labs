#!/bin/bash

set -e

# ========================== CONFIGURATION ==========================
SERVICES=(
    "user-service"
    "product-service"
    "api-gateway"
)
SERVICE_REGISTRY="service-registry"
LOG_DIR="logs"
JVM_OPTS="-Xms128m -Xmx384m"
EUREKA_HEALTH_URL="http://localhost:8761/actuator/health"
SERVICE_HEALTH_ENDPOINT="/actuator/health"
MAX_RETRIES=30
RETRY_DELAY=2

# ========================== FUNCTION TO WAIT FOR SERVICE HEALTH ==========================
wait_for_health() {
    local service_name=$1
    local port=$2
    local attempt=1
    local url="http://localhost:${port}${SERVICE_HEALTH_ENDPOINT}"

    echo "Waiting for $service_name to become healthy at $url..."

    until curl -s "$url" | grep -q '"status":"UP"'; do
        if (( attempt >= MAX_RETRIES )); then
            echo "$service_name did not become healthy in time. Check logs."
            return 1
        fi
        echo "Attempt $attempt/$MAX_RETRIES: $service_name not ready yet..."
        ((attempt++))
        sleep "$RETRY_DELAY"
    done

    echo "$service_name is healthy."
    return 0
}

# ========================== BUILD SECTION ==========================
echo "========== Building All Services =========="
for service in "$SERVICE_REGISTRY" "${SERVICES[@]}"; do
    echo "----> Building $service"
    (
        cd "$service" || exit 1
        ./gradlew clean build -x test
    )
    if [[ $? -ne 0 ]]; then
        echo "Build failed for $service. Aborting startup."
        exit 1
    fi
done
echo "✅ Build phase completed successfully."

# ========================== SETUP LOGS ==========================
mkdir -p "$LOG_DIR"

# ========================== START EUREKA SERVER ==========================
echo ""
echo "========== Starting Service Registry ($SERVICE_REGISTRY) =========="
(
    cd "$SERVICE_REGISTRY" || exit 1
    JAR_FILE=$(find build/libs -type f -name "*.jar" ! -name "*plain*.jar" | head -n 1)
    if [[ ! -f "$JAR_FILE" ]]; then
        echo "Runnable JAR not found for $SERVICE_REGISTRY. Aborting."
        exit 1
    fi
    nohup java $JVM_OPTS -jar "$JAR_FILE" > "../$LOG_DIR/${SERVICE_REGISTRY}.log" 2>&1 &
)
echo "$SERVICE_REGISTRY starting... log: $LOG_DIR/${SERVICE_REGISTRY}.log"

# ========================== WAIT FOR EUREKA TO BE HEALTHY ==========================
echo "Waiting for Eureka to be healthy at $EUREKA_HEALTH_URL..."

attempt=1
until curl -s "$EUREKA_HEALTH_URL" | grep -q '"status":"UP"'; do
    if (( attempt >= MAX_RETRIES )); then
        echo "Eureka did not become healthy in time. Aborting."
        exit 1
    fi
    echo "Attempt $attempt/$MAX_RETRIES: Eureka not ready yet..."
    ((attempt++))
    sleep "$RETRY_DELAY"
done
echo "✅ Eureka is healthy and accepting service registrations."

# ========================== START EACH APPLICATION SERVICE ==========================
for service in "${SERVICES[@]}"; do
    echo ""
    echo "========== Starting $service =========="

    (
        cd "$service" || exit 1
        JAR_FILE=$(find build/libs -type f -name "*.jar" ! -name "*plain*.jar" | head -n 1)
        if [[ ! -f "$JAR_FILE" ]]; then
            echo "Runnable JAR not found for $service. Skipping."
            exit 1
        fi
        nohup java $JVM_OPTS -jar "$JAR_FILE" > "../$LOG_DIR/${service}.log" 2>&1 &
    )
    echo "$service started... log: $LOG_DIR/${service}.log"
    sleep 2
done

# ========================== OPTIONAL: WAIT FOR HEALTH OF SERVICES ==========================
# You can hardcode ports or extract them from application.properties if dynamic
declare -A SERVICE_PORTS
SERVICE_PORTS=(
    ["user-service"]=8081
    ["product-service"]=8082
    ["api-gateway"]=8989
)

for service in "${SERVICES[@]}"; do
    port=${SERVICE_PORTS[$service]}
    if [[ -n "$port" ]]; then
        wait_for_health "$service" "$port"
    else
        echo "No port mapping found for $service. Skipping health check."
    fi
done

# ========================== FINAL STATUS ==========================
echo ""
echo "✅✅✅ All services started and healthy ✅✅✅"
echo "Logs available under: $LOG_DIR/"
