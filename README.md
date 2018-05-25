# news-seeder-client

The client communicates with the Cassandra db where the rss are stored.

### Build the project
- build project `sbt package`
- build docker image `docker build -t news-seeder-client .`
- run service in docker `docker-compose up -d`
- kill (docker) service `docker-compose down -v`
- test coverage `sbt clean ???`

### Check the API
- check the connection `curl -i http://0.0.0.0:8181/seeder/health`
- get list of rss `curl -i http://0.0.0.0:8181/seeder/rss-list`
- save rss `curl -H "Content-Type:application/json" -X POST -d '{"url": "http://you.url"}' http://localhost:8080/seeder/rss`
- delete rss `curl -H "Content-Type:application/json" -X DELETE -d '{"url": "http://you.url"}' http://localhost:8080/seeder/rss`
