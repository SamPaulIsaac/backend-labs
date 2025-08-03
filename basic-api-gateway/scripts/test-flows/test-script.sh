#!/bin/bash

echo "🚀 Starting functional test flow against API Gateway..."

echo -e "\n=== Step 1: Create User ==="
curl -X POST http://localhost:8989/api/user/create \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@example.com"}'
echo -e "\n"

echo -e "\n=== Step 2: Create Product ==="
curl -X POST http://localhost:8989/api/product/create \
  -H "Content-Type: application/json" \
  -d '{"name":"Guitar"}'
echo -e "\n"

echo -e "\n=== Step 3: Fetch All Users ==="
curl http://localhost:8989/api/user/all
echo -e "\n"

echo -e "\n=== Step 4: Fetch All Products ==="
curl http://localhost:8989/api/product/all
echo -e "\n"

echo "✅ Functional test flow completed."
