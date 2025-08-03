#!/bin/bash

set -e

# ========== CONFIG ==========
SERVICES=(
    "api-gateway|http://localhost:8989/actuator/health"
    "product-service|http://localhost:8082/actuator/health"
    "user-service|http://localhost:8081/actuator/health"
)

MAX_RETRIES=12
BASE_SLEEP=2  # seconds

function wait_for_service() {
    local name=$1
    local url=$2

    echo -e "\n=== Waiting for $name to be healthy at $url ==="

    for ((i=0; i<MAX_RETRIES; i++)); do
        if curl -s "$url" | grep -q '"status":"UP"'; then
            echo "✅ $name is UP and healthy (after $i attempts)"
            return 0
        else
            echo "⏳ Attempt $((i+1))/$MAX_RETRIES: Waiting for $name..."
            sleep $((BASE_SLEEP * (i+1)))
        fi
    done

    echo "❌ $name failed to become healthy after $MAX_RETRIES attempts."
    exit 1
}

# ========== HEALTH CHECK LOOP ==========
for entry in "${SERVICES[@]}"; do
    IFS='|' read -r name url <<< "$entry"
    wait_for_service "$name" "$url"
done

# ========== FUNCTIONAL TESTS ==========
echo -e "\n🚀 All services are healthy. Running functional tests...\n"

function run_test() {
    local desc=$1
    local method=$2
    local url=$3
    local data=$4

    echo -e "\n=== $desc ==="

    if [[ "$method" == "GET" ]]; then
        response=$(curl -s "$url")
    else
        response=$(curl -s -X "$method" "$url" -H "Content-Type: application/json" -d "$data")
    fi

    echo "$response" | jq . 2>/dev/null || echo "⚠️ jq failed. Raw response: $response"
}

run_test "Creating User" "POST" "http://localhost:8989/api/user/create" '{"name":"Alice"}'
run_test "Creating Product" "POST" "http://localhost:8989/api/product/create" '{"name":"Guitar"}'
run_test "Fetching All Users" "GET" "http://localhost:8989/api/user/all"
run_test "Fetching All Products" "GET" "http://localhost:8989/api/product/all"

echo -e "\n✅ Functional tests complete."
