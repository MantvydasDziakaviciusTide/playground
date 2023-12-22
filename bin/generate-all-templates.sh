#!/bin/bash
# Script to generate projects from templates.

ROOT=$(pwd)

echo "Running script from \"$ROOT\""

function create_project_from_template {
  echo "===== GENERATE PROJECT ($OUTPUT_DIR) FROM TEMPLATE ($TEMPLATE) ====="
  ./generate java test test_service
}

# Delete the 'output' directory to ensure freshness
rm -rf output/

for i in java/templates/* ; do
  if [ -d "$i" ]; then
    TEMPLATE=$(basename "$i")
    OUTPUT_DIR="test-$TEMPLATE"
    create_project_from_template
  fi
done
