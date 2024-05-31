You can start temporary Neo4j server with this command:
```bash
docker run --rm \
 --publish=7474:7474 \
 --publish=7687:7687 \
 --env NEO4J_AUTH=neo4j/password \
 --volume=/home/igor/neo4j:/data \
 neo4j:latest
```
