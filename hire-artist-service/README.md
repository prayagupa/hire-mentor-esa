Hire Artists Engine
---------------------

```
./gradlew idea
./gradlew compileJava
```

start streaming cluster
-----------------------

```
## https://github.com/prayagupd/vagrant-kafka-scala/tree/master/vagrant

$KAFKA/zookeeper-server-start.sh $KAFKA_CONFIG/zookeeper.properties

$KAFKA/kafka-server-start.sh $KAFKA_CONFIG/server.properties
```

run application
---------------

```
./gradlew jettyRun
```


produce
-------

```
http://localhost:8081/hireartists/send

--header HireArtistRequestEvent
<HireArtistEvent>
   <name>prayagupd</name>
   <date>10/28/2016</date>
</HireArtistEvent>
```



consume
-------

```
http://localhost:8081/hireartists/consume
```

```
$ /usr/local/kafka_2.11-0.10.1.1/bin/kafka-topics.sh --list --zookeeper 127.0.0.1:2181
__consumer_offsets
topic.artists

$ /usr/local/kafka_2.10-0.8.2.2/bin/kafka-consumer-offset-checker.sh --zookeeper 127.0.0.1:2181 --group artists_group --topic topic.artists
Group           Topic                          Pid Offset          logSize         Lag             Owner
artists_group   topic.artists                  0   14              15              1               none
```
