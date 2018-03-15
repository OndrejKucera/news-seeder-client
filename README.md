# news-seeder-client

master build: .....

## build the project

### build project
- sbt assembly

### build docker image
docker build -t news-seeder-client .

### run/kill service in docker
- docker-compose up -d
- docker-compose down -v

### check the connection
- curl -i http://0.0.0.0:8181/seeder/health

##  Scala style
- sbt scalastyleGenerateConfig
- sbt scalastyle
- sbt scalastyle compile

## Test coverage
- sbt clean coverage test

## Dependency tree
- dependencyTree
