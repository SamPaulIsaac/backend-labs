#!/bin/bash

# === Configuration ===
PORTS=(8989 8081 8082 8761)
echo "Stopping services bound to ports: ${PORTS[*]}"

for port in "${PORTS[@]}"; do
    pids=$(lsof -ti tcp:$port)

    if [[ -n "$pids" ]]; then
        echo "-> Port $port is active. Attempting to terminate process(es): $pids"
        for pid in $pids; do
            kill "$pid"
            sleep 1
            if ps -p "$pid" > /dev/null; then
                echo "   Process $pid did not terminate. Forcing shutdown..."
                kill -9 "$pid"
                sleep 1
            fi

            if ps -p "$pid" > /dev/null; then
                echo "   Failed to stop process $pid on port $port."
            else
                echo "   Successfully stopped process $pid on port $port."
            fi
        done
    else
        echo "-> No process is currently bound to port $port."
    fi
done

echo "All specified ports have been checked and processes terminated where applicable."
