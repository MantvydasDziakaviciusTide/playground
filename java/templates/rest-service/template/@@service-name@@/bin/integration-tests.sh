#!/bin/sh

# This script runs the integration tests in Bitbucket pipelines (see `bitbucket-pipeline.yml`).

# Execute script which generates codertifact token with aws credentials in master container images
/app/codeartifact.sh

# Export the token generated to this project by storing in temporary file
export CODEARTIFACT_AUTH_TOKEN=`cat /tmp/CODEARTIFACT_TOKEN`

# Remove the temporary file once token is exported
rm /tmp/CODEARTIFACT_TOKEN

# Create a clean database.
apt-get update && apt-get install postgresql-client -y
psql -h 127.0.0.1 -U postgres -a ./sql/create-db.sql

# The AWS variables must exist as environment variables in Bitbucket.
./gradlew --build-cache integrationTest
