# @@service-name@@

## TODO

Welcome to your new REST service. Here's a checklist of things that you need to do:

* Update this file: follow the TODOs. Do not remove sections – simply say "None" or "N/A" if not relevant.
* Remove, or rename, the DummyController class. If you're keeping it, update the endpoint to be sensible.
* Get rid of the `TeapotException` and the mapping in `ExceptionAdvice`, but follow the pattern and packages for your exceptions
* Update the `AuthorisationService` to do whatever you need it do to.
* Update the `SwaggerConfig` class so it has some useful strings in it
* Update the dependencies. Tide policy is to keep as up-to-date as possible, so update Java, Spring Boot and any other libraries
or plugins you see. If you do update the dependencies, be #oneteam and make sure to update the template as well.
* Deploy the service. This template should produce a fully working service, with a database. It won't do much, but it will be there.
* Keep this file up to date.

## Introduction

* Team: TODO: Your team name
* Slack: TODO: #sapis-yourteam
* Swagger: [here](https://@@service-name@@.rest.wip.internal.tide.co/swagger-ui/index.html)
* TODO: [Backlog](https://link_goes_here)
* TODO: [Confluence](https://link_goes_here)

TODO: This is where you tell us all about your service. What is its reason for being? What value does it bring?
What features, if any, does it power in the frontend? Don't be shy – show off! 

## Requirements

TODO: List any non-standard requirements for running this service.

## Events & Commands Produced
TODO: What events or commands does your service produce? Tell us all about them!

| Event Name                                  | Type    | Event Description                    | Documentation        |
|---------------------------------------------|---------|--------------------------------------|----------------------|
| `application/vnd.tide.something-created.v1` | Event   | Something terribly exciting happened | The docs for it      |
| `application/vnd.tide.create-something.v1`  | Command | Something needs to happen            | Docs for the command |

## Events & Commands Consumed
TODO: What events or commands does your service consume? 

| Event Name                                  | Type    | Event Source                                                               | Documentation        |
|---------------------------------------------|---------|----------------------------------------------------------------------------|----------------------|
| `application/vnd.tide.something-created.v1` | Event   | [something-service](https://bitbucket.org/tideaccount/something-service)   | The docs for it      |
| `application/vnd.tide.create-something.v1`  | Command | This service                                                               | Docs for the command |

## Synchronous APIs Provided
TODO: What endpoints do you offer in this service? REST or gRPC? Perhaps a high-level view, with a link to your Swagger docs? 
Do not remove this section if you do not provide synchronous APIs.

## Internal Dependencies
TODO: Which services do you synchronously depend on? Do not remove this section if you have no internal dependencies.

## Scheduled Tasks
TODO: Does your service have any scheduled tasks? Describe them here, including the time (and day, if relevant) when they run. What's
the cron expression?

## Databases and Local Projections
TODO: Do you use a database? We'd love to hear about it! What projections do you maintain?

## Third Parties & External Dependencies
TODO: Who do you talk to? We need links to their documentation, the name of the relationship owner (probably a VP), who do we contact
in an emergency, etc. Tell us about their environments, along with information about how to log in (if relevant), preferably as a link
to somewhere on https://tideaccount.1password.com/.

## Project structure

    .
    ├── bin                         : Shell scripts for installing, running and building.
    ├── build.gradle                : Gradle dependencies and build script.
    ├── docker                      : Docker-related files for testing, building and deploying the service.
    │   ├── main                    : Production Docker files.
    │   └── test                    : Test Docker files.
    ├── gradle                      : Gradle wrapper.
    ├── gradlew                     : Gradle Wrapper script (Unix).
    ├── lombok.config               : Required to exclude Lombok generated code from Sonarcloud coverage reports.
    ├── README.md                   : This file you are reading right now ;).
    ├── settings.gradle             : Project's Gradle settings.
    ├── sql                         : General database scripts for starting a test database.
    └── src                         : Sources.
        ├── generated-db-entities   : Sources for generated database entities.
        ├── main                    : Main sources.
        └── test                    : Test sources.
