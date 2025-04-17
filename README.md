**RabbitMQ Batch Message Processing**

The scope of this project is to study how to configure a spring boot application that will 
consume messages from a RabbitMq queue. It will consume messages in batches besed on 
configurations done on listener. See [RabbitMqConfig.java](src/main/java/com/rbcode/rbcamqprabbitmq/config/RabbitMqConfig.java)

**RabbitMQ startup**
To start RabbitMq run the configuration #Start-RabbitMQ or from terminal run:
```bash
docker-compose up -d
```
To stop RabbitMq run
```bash
docker-compose down
```
-------------------------------------
Go in the browser to open rabbitmq admin 
http://127.0.0.1:15672/#/queues/%2F/rbc-queue
{
    user     :user
    password :pass
}

Start application using configuration #RUN_APP

Open in browser the app's UI: http://localhost:8088/

Send messages to queue by sending the number of Alerts that should be generated;

Monitor the logs to see how many messages are proccessed in a batch

Updated batch parameters in [RabbitMqConfig.java](src/main/java/com/rbcode/rbcamqprabbitmq/config/RabbitMqConfig.java)
