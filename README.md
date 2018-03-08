# news-seeder-client

master build: [![buddy pipeline](https://app.buddy.works/ondrakuca/news-seeder-client/pipelines/pipeline/128097/badge.svg?token=6a057046609e24997a6d5517a0be2d38976615cd70fe3ff43bc4ba09fcc5aaa5 "buddy pipeline")](https://app.buddy.works/ondrakuca/news-seeder-client/pipelines/pipeline/128097)

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

## Test coverage
- sbt clean coverage test

## Dependency tree
- dependencyTree
