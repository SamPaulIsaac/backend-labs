#!/bin/bash

API_BASE_URL="http://localhost:8080/api/alarms"

# 1. Create Alarm
echo "Creating alarm..."
curl -X POST "$API_BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Morning Alarm",
    "time": "2025-05-16T07:00:00",
    "isRecurring": true,
    "isActive": true,
    "recurrencePattern": "DAILY"
}'

echo -e "\n"

# 2. Get All Alarms
echo "Fetching all alarms..."
curl -X GET "$API_BASE_URL/all"

echo -e "\n"

# 3. Get All Active Alarms
echo "Fetching active alarms..."
curl -X GET "$API_BASE_URL/active"

echo -e "\n"

# 4. Update Alarm
# Replace this with a valid alarm ID after creation
ALARM_ID="replace-with-alarm-id"
echo "Updating alarm with ID $ALARM_ID..."
curl -X PUT "$API_BASE_URL/$ALARM_ID" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Alarm",
    "time": "2025-05-16T08:00:00",
    "isRecurring": false,
    "isActive": true,
    "recurrencePattern": "WEEKLY"
}'

echo -e "\n"

# 5. Delete Alarm
# Replace this with a valid alarm ID after creation
echo "Deleting alarm with ID $ALARM_ID..."
curl -X DELETE "$API_BASE_URL/$ALARM_ID"

echo -e "\nDone."
