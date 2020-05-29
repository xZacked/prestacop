# Prestacop

## Being Hadoop/HDFS ready
1. Follow this tutorial: https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/SingleCluster.html
2. /!\ Don't forget to add this line in hadoop-env.sh file in the etc/hadoop/ directory 

        JAVA_HOME=/usr/lib/jvm/java-8-openjdk
        
## Being Apach Flume ready
1. Follow  this tutorial:  https://www.tutorialspoint.com/apache_flume/apache_flume_environment.htm
2. /!\ You might need to replace guava lib


## How to run 
#### Kafka and Zookeeper
1. Run Zookeeper: **bin/zookeeper-server-start.sh config/zookeeper.properties**
2. Run Kafka: **bin/kafka-server-start.sh config/server.properties**
3. Run your producer

#### Hadoop and HDFS
1. **bin/hdfs namenode -format**
2. **sbin/start-dfs.sh**
3. You can use the **jps** command to verify if you have your DataNode created

#### Flume
1. **bin/flume-ng agend --conf ./conf -f Stream_To_Storage.conf --nameagent -Dflume.root.logger=INFO,console**