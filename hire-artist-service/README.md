Hire Artists Engine
---------------------

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


```
--header HireArtistRequestEvent
<HireArtistEvent>
   <name>prayagupd</name>
   <date>10/28/2016</date>
</HireArtistEvent>
```
