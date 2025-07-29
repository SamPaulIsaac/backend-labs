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

  sleep 1  # Wait for 1 seconds before the next request
  echo "----------------------------------------------------------------"
}

# 1. Create employee (with embedded employee detail)
execute_curl "1. Creating Employee" "curl -X POST http://localhost:8080/api/employees/create \
  -H \"Content-Type: application/json\" \
  -d '{
    \"name\": \"Alice Johnson\",
    \"department\": \"Engineering\",
    \"email\": \"alice.j@example.com\",
    \"employeeDetail\": {
      \"address\": \"12 Elm Street\",
      \"dob\": \"1990-01-15\",
      \"aadharNumber\": \"111122223333\"
    }
  }'"

# 2. Fetch employee by ID
execute_curl "2. Fetching Employee (ID=1)" "curl -X GET http://localhost:8080/api/employees/1"

# 3. Update employee with new detail (PUT)
execute_curl "3. Updating Employee (ID=1)" "curl -X PUT http://localhost:8080/api/employees/1 \
  -H \"Content-Type: application/json\" \
  -d '{
    \"name\": \"Alice Johnson Updated\",
    \"department\": \"Engineering\",
    \"email\": \"alice.updated@example.com\",
    \"employeeDetail\": {
      \"address\": \"22 Maple Avenue\",
      \"dob\": \"1990-01-15\",
      \"aadharNumber\": \"999988887777\"
    }
  }'"

# 4. Patch employee (e.g., just the name)
execute_curl "4. Patching Employee (ID=1)" "curl -X PATCH http://localhost:8080/api/employees/1 \
  -H \"Content-Type: application/json\" \
  -d '{\"name\": \"Alice Patched\"}'"

# 5. Delete employee
execute_curl "5. Deleting Employee (ID=1)" "curl -X DELETE http://localhost:8080/api/employees/1"
