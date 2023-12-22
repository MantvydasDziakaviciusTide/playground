#!/bin/sh

# This script builds the final artefact that will be packaged in the Docker image. It is run automatically run by
# Bitbucket (see `bitbucket-pipeline.yml`).
# The AWS variables must exist as environment variables in Bitbucket.

# Execute script which generates codertifact token with aws credentials in master container images
/app/codeartifact.sh

# Export the token generated to this project by storing in temporary file
export CODEARTIFACT_AUTH_TOKEN=`cat /tmp/CODEARTIFACT_TOKEN`

# Remove the temporary file once token is exported
rm /tmp/CODEARTIFACT_TOKEN

./gradlew --build-cache bootJar
