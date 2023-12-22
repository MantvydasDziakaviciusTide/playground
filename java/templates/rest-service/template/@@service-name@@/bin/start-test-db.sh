#!/bin/sh

# This script starts the database in a Docker container locally. This is needed to run the tests locally, but not for
# Bitbucket Pipelines since the tests run in a container with their own database.
#
# See `../docker/test/db/docker-compose.yml` for details about the database and the container name.
# See `../sql` for the scripts that drop/create the database.

echo "Re-creating test database. All data of the existing database will be destroyed. Port 5432 must be free."

start_db() {
	docker-compose -f ../docker/test/db/docker-compose.yml up -d \
	  && sleep 10s \
	  && psql -h 127.0.0.1 -U postgres -f ../sql/drop-db.sql \
	  && psql -h 127.0.0.1 -U postgres -f ../sql/create-db.sql
}

while true; do
    read -p "Proceed? [y/n] " yn
    case $yn in
        [Yy]* ) start_db; break;;
        [Nn]* ) exit;;
        * ) echo "Please answer yes or no.";;
    esac
done