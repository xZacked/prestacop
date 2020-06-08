# Prestacop

## Context

Prestacop, a company specializing in service delivery for police forces, wants to create a drone service  to help police systems make parking tickets.
The drone have camera with a pattern recognition software identifies license plates and characterizes infractions.

### Goals
* Have a solution which stream drones informations in a scalable way
* Use historical data to make statistics in order to improve their services 

## Architecture 

* **ProducerCSV**: Reads the NYPD CSV and publishes the rows as messages to the stream
* **ProducerDrone**: Simulates a drone, sends messages of different forms to the stream
*  **ConsumerAlert**: Consumes stream messages, raises an alarm when human interaction is required
* **SparkStreaming**: Consumes stream messages, stores them
* **SparkAnalysis**: Reads messages out of storage, performs the analysis
* **StreamAlert**: Filter data in order to send alert on the alert-topic
* **mysite**: Django web app in order to display alerts messages
* ~~**StreamToStorage**~~: Our old way of consume stream messages using Apach Flume (which was not viable)

![](https://i.imgur.com/qqfZ8yx.png)


## How to run the project

#### Being hadoop/HDFS ready

1. Follow this tutorial: https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/SingleCluster.html
2. /!\ Don't forget to add this line in hadoop-env.sh file in the etc/hadoop/ directory 
3. 
##### Being Django ready
1. You should have a python3.7 (or higher)
2. Install Django (python -m pip install Django)

#### How to run Zookeeper and Kafka 
1. Run Zookeeper: **bin/zookeeper-server-start.sh config/zookeeper.properties**
2. Run Kafka: **bin/kafka-server-start.sh config/server.properties**

#### How to run HDFS
1. **bin/hdfs namenode -format**
2. **sbin/start-dfs.sh**
3. You can use the **jps** command to verify if your Datanode and Namenode have been created
4. Don't forget to make the HDFS directory required to execute MapReduce jobs with the name **prestacop-storage/**

#### How to run the web site
    python manage.py runserver
    

## Launch the project
1. Run Zookeeper and Kafka
2. Run HDFS
3. Run your producers (CSV or Drone simulator or both)
4. Run AlertStream
5. Run the web site in order to view the alert messages
6. Run ConsumerAlert main which will display alert message on terminal 
7. Run SparkStreaming main
8. Run SparkAnalysis main
