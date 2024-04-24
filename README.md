to start rabbitmq run from terminal

**docker-compose up -d**

to stop rabbitmq run

**docker-compose down**

-------------------------------------
Go in the browser to open rabbitmq admin
http://localhost:15672/#/queues/%2F/rbc-queue

Send messages to queue by setting this property 

Properties: content_type = text/plain

Message: any text
