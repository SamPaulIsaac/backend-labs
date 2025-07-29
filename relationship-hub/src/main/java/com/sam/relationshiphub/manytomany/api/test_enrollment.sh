#!/bin/bash

# Function to perform curl and validate HTTP response code
execute_curl() {
  local description="$1"
  local cmd="$2"

  echo "Executing: $description"
  http_code=$(eval "$cmd -w \"%{http_code}\" -o /dev/null -s")

  if [ "$http_code" -eq 200 ]; then
    echo "Status - Success (HTTP 200)"
  else
    echo "Status - Failed (HTTP $http_code)"
  fi

  sleep 2
  echo "------------------------------------------------------------"
}

# 1. Create Student
execute_curl "1. Creating Student" "curl -X POST http://localhost:8080/api/students \
  -H \"Content-Type: application/json\" \
  -d '{\"name\":\"Sam Isaac\",\"email\":\"sam@example.com\"}'"

# 2. Create Course
execute_curl "2. Creating Course" "curl -X POST http://localhost:8080/api/courses \
  -H \"Content-Type: application/json\" \
  -d '{\"name\":\"Java Basics\",\"credits\":\"3\"}'"

# 3. Enroll Student into Course
execute_curl "3. Enrolling Student to Course" "curl -X POST http://localhost:8080/api/enrollments \
  -H \"Content-Type: application/json\" \
  -d '{\"studentId\":1, \"courseId\":1, \"grade\":\"A\"}'"

# 4. Fetch Student
execute_curl "4. Fetch Student by ID" "curl -X GET http://localhost:8080/api/students/1"

# 5. Fetch Course
execute_curl "5. Fetch Course by ID" "curl -X GET http://localhost:8080/api/courses/1"
