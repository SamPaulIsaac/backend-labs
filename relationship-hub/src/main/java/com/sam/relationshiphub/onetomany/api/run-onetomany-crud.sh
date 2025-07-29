#!/bin/bash

# Function to perform curl and validate HTTP response code
execute_curl() {
  local description="$1"
  local cmd="$2"

  echo "Executing: $description"
  http_code=$(eval "$cmd -w \"%{http_code}\" -o /dev/null -s")

  if [ "$http_code" -eq 200 ] || [ "$http_code" -eq 204 ]; then
    echo "Response Status - Success (HTTP $http_code)"
  else
    echo "Response Status - Failed (HTTP $http_code)"
  fi

  sleep 1  # Wait for 1  seconds before the next request
  echo "-----------------------------------------------"
}

# 1. Create project
execute_curl "1. Creating Project" "curl -X POST http://localhost:8080/api/projects/create \
  -H \"Content-Type: application/json\" \
  -d '{\"title\":\"Spring Boot Integration\",\"deadLine\":\"2025-09-15\",\"tasks\":[{\"description\":\"Setup DB\",\"status\":\"PENDING\"}]}'"

# 2. Fetch project by ID
execute_curl "2. Fetching Project (ID=1)" "curl -X GET http://localhost:8080/api/projects/1"

# 3. Update project (PUT)
execute_curl "3. Updating Project (ID=1)" "curl -X PUT http://localhost:8080/api/projects/1 \
  -H \"Content-Type: application/json\" \
  -d '{\"title\":\"Spring Boot Integration Updated\",\"deadLine\":\"2025-09-15\",\"tasks\":[{\"description\":\"Add unit tests\",\"status\":\"IN_PROGRESS\"}]}'"

# 4. Patch project title only
execute_curl "4. Patching Project Title (ID=1)" "curl -X PATCH http://localhost:8080/api/projects/1 \
  -H \"Content-Type: application/json\" \
  -d '{\"title\":\"Spring Boot Final\"}'"

# 5. Delete project
execute_curl "5. Deleting Project (ID=1)" "curl -X DELETE http://localhost:8080/api/projects/1"
