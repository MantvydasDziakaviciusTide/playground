#!/bin/sh

# This script runs the SonarCloud analysis in Bitbucket pipelines (see `bitbucket-pipeline.yml`).
# The AWS variables must exist as environment variables in Bitbucket, as well as a SONAR_TOKEN variable.

# This script will fail if your project doesn't already exist in Sonarcloud

# Execute script which generates codertifact token with aws credentials in master container images
/app/codeartifact.sh

# Export the token generated to this project by storing in temporary file
export CODEARTIFACT_AUTH_TOKEN=`cat /tmp/CODEARTIFACT_TOKEN`

# Remove the temporary file once token is exported
rm /tmp/CODEARTIFACT_TOKEN

./gradlew --build-cache jacocoTestReport sonarqube -x test \
              -Dsonar.projectKey=$BITBUCKET_WORKSPACE_$BITBUCKET_REPO_SLUG \
              -Dsonar.organization=$SONAR_ORGANIZATION \
              -Dsonar.host.url=$SONAR_HOST \
              -Dsonar.login=$SONAR_TOKEN
