# news-seeder-client

### Build the project
- **build project**
  - sbt package

- **build docker image**
  - docker build -t news-seeder-client .

- **run/kill service in docker**
  - docker-compose up -d
  - docker-compose down -v

### Check the API
- **check the connection**
  - curl -i http://0.0.0.0:8181/seeder/health
- **get list of rss**
  - curl -i http://0.0.0.0:8181/seeder/rss-list
- **save rss**
  - curl -H "Content-Type:application/json" -X POST -d '{"url": "http://you.url"}' http://localhost:8080/seeder/rss
- **delete rss**
  - curl -H "Content-Type:application/json" -X DELETE -d '{"url": "http://you.url"}' http://localhost:8080/seeder/rss

### Test coverage
- sbt clean coverage test

### Dependency tree
- dependencyTree
