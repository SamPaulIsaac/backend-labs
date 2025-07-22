#!/bin/bash

URL="http://localhost:8080/api/nrr/saveNRRPrerequisite"
HEADER="Content-Type: application/json"

echo "Submitting CSK match data to $URL..."

for match in {1..14}; do
  case $match in
    1)
      data='{
        "team": "CSK",
        "matchNumber": 1,
        "noOfRunsScored": 158,
        "noOfRunsGiven": 155,
        "noOfOversConsumed": 19.1,
        "noOfOversBowled": 20.0
      }'
      ;;
    2)
      data='{
        "team": "CSK",
        "matchNumber": 2,
        "noOfRunsScored": 146,
        "noOfRunsGiven": 196,
        "noOfOversConsumed": 20.0,
        "noOfOversBowled": 20.0
      }'
      ;;
    3)
      data='{
        "team": "CSK",
        "matchNumber": 3,
        "noOfRunsScored": 176,
        "noOfRunsGiven": 182,
        "noOfOversConsumed": 20.0,
        "noOfOversBowled": 20.0
      }'
      ;;
    4)
      data='{
        "team": "CSK",
        "matchNumber": 4,
        "noOfRunsScored": 158,
        "noOfRunsGiven": 183,
        "noOfOversConsumed": 20.0,
        "noOfOversBowled": 20.0
      }'
      ;;
    5)
      data='{
        "team": "CSK",
        "matchNumber": 5,
        "noOfRunsScored": 201,
        "noOfRunsGiven": 219,
        "noOfOversConsumed": 20.0,
        "noOfOversBowled": 20.0
      }'
      ;;
    6)
      data='{
        "team": "CSK",
        "matchNumber": 6,
        "noOfRunsScored": 103,
        "noOfRunsGiven": 107,
        "noOfOversConsumed": 20.0,
        "noOfOversBowled": 10.1
      }'
      ;;
    7)
      data='{
        "team": "CSK",
        "matchNumber": 7,
        "noOfRunsScored": 168,
        "noOfRunsGiven": 166,
        "noOfOversConsumed": 19.3,
        "noOfOversBowled": 20.0
      }'
      ;;
    8)
      data='{
        "team": "CSK",
        "matchNumber": 8,
        "noOfRunsScored": 176,
        "noOfRunsGiven": 177,
        "noOfOversConsumed": 20.0,
        "noOfOversBowled": 15.4
      }'
      ;;
    9)
      data='{
        "team": "CSK",
        "matchNumber": 9,
        "noOfRunsScored": 154,
        "noOfRunsGiven": 155,
        "noOfOversConsumed": 19.5,
        "noOfOversBowled": 18.4
      }'
      ;;
    10)
      data='{
        "team": "CSK",
        "matchNumber": 10,
        "noOfRunsScored": 190,
        "noOfRunsGiven": 194,
        "noOfOversConsumed": 19.2,
        "noOfOversBowled": 19.4
      }'
      ;;
    11)
      data='{
        "team": "CSK",
        "matchNumber": 11,
        "noOfRunsScored": 211,
        "noOfRunsGiven": 213,
        "noOfOversConsumed": 20.0,
        "noOfOversBowled": 20.0
      }'
      ;;
    12)
      data='{
        "team": "CSK",
        "matchNumber": 12,
        "noOfRunsScored": 183,
        "noOfRunsGiven": 179,
        "noOfOversConsumed": 19.4,
        "noOfOversBowled": 20.0
      }'
      ;;
    13)
      data='{
        "team": "CSK",
        "matchNumber": 13,
        "noOfRunsScored": 187,
        "noOfRunsGiven": 188,
        "noOfOversConsumed": 20.0,
        "noOfOversBowled": 17.1
      }'
      ;;
    14)
      data='{
        "team": "CSK",
        "matchNumber": 14,
        "noOfRunsScored": 230,
        "noOfRunsGiven": 147,
        "noOfOversConsumed": 20.0,
        "noOfOversBowled": 18.3
      }'
      ;;
  esac

  echo "Submitting Match $match..."
  curl -s -X POST "$URL" -H "$HEADER" -d "$data" || echo "Failed on match $match"
  done

echo "All data submitted."
