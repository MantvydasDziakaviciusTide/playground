#!/bin/bash
# Script to build Java projects created from templates.

ROOT=$(pwd)
OUTPUT_PREFIX="test-"

echo "Running script from \"$ROOT\""

function build_created_project {
  echo "===== BUILD PROJECT ($OUTPUT_DIR) ====="
  local projectdir="$ROOT/$OUTPUT_DIR"
  cd "$projectdir"; ./bin/unit-tests.sh; ./bin/api-tests.sh
}

for i in templates/* ; do
  if [ -d "$i" ]; then
    TEMPLATE=$(basename "$i")
    # Generated project should exist in 'output' directory
    OUTPUT_DIR="output/$OUTPUT_PREFIX$TEMPLATE"
    if [ -d "$OUTPUT_DIR" ]; then
      # Build if Java project...
      [ -f "$OUTPUT_DIR/gradlew" ] && build_created_project
    fi
  fi
done
