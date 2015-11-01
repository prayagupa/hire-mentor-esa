
```
./gradlew idea
./gradlew compileJava
```

Run
---------------

```
$KAFKA/zookeeper-server-start.sh $KAFKA_CONFIG/zookeeper.properties

$KAFKA/kafka-server-start.sh $KAFKA_CONFIG/server.properties
```

```
./gradlew jettyRun
```