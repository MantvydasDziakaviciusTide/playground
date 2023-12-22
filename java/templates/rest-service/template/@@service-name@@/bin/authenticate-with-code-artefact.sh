#!/bin/sh

# ===================================================================================================================
# In order for this step to be successful you must setup the ~/.aws/config file to contain the following:

#     [default]
#     sso_start_url = https://tide.awsapps.com/start
#     sso_region = eu-west-2
#     sso_account_id = <account_id>
#     sso_role_name = ReadOnly
#     region = eu-west-2
#     output = json

# ===================================================================================================================

echo "Performing AWS SSO login"
aws sso login

echo "Contacting AWS for codeartifact auth token"
export CODEARTIFACT_AUTH_TOKEN=`aws codeartifact get-authorization-token --domain common-uk-main --domain-owner 824676761403 --query authorizationToken --output text`
