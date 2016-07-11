# Fibonacci Server

## Requirements

1. Scala 2.11.7
2. sbt 0.13.11

## Setup

1. clone https://github.com/vbeffa/fibsrv
2. `activator playGenerateSecret` (if deploying to production, will need
    to set `APPLICATION_SECRET` environment variable)

## Running

1. `activator run`

## Testing

1. `activator test` to run unit and integration tests
2. `./concurrent_test.sh` to run concurrent load test (against reference
    implementation deployed to Heroku)
