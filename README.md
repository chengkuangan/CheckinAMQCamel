# Camel Service for AMQ

Consumes booking information from Kafka topic, convert the JSON data into delimited string and send to AMQ queue.

>> Note: Ignore AMQ Streams as it is not applicable for your references

## Preparing for OpenShift Deployment

### Pre-Requisites

**Deploy AMQ Streams**

1. Deploy AMQ Streams from Operator Hub.

**Deploy a AMQ Broker**

1. Install AMQ Broker Operator from Operator Hub

    Refer [Deploying AMQ Broker on OpenShift Container Platform using the AMQ Broker Operator](https://access.redhat.com/documentation/en-us/red_hat_amq/2020.q4/html/deploying_amq_broker_on_openshift/deploying-broker-on-ocp-using-operator_broker-ocp) 
    <br>

2. Deploy the AMQ Broker using the [artmis.yaml](/yaml/amq/artemis.yaml) file.

    ```
    oc apply -f yaml/amq/artemis.yaml
    ```

3. Set the `${APPS_NAMESPACE}`, `${KAFKA_BOOTSTRAP_SERVER}`, `${AMQ_URL}`, `${AMQ_USERNAME}` and `${AMQ_PASSWORD}`. Run the following command to deploy to OpenShift.

    ```
    cd ../CheckinAMQCamel/
    ./mvnw clean 
    ./mvnw install \
    -Dquarkus.kubernetes.deploy=true \
    -Dquarkus.container-image.group=${APPS_NAMESPACE} \
    -Dquarkus.container-image.registry=image-registry.openshift-image-registry.svc:5000 \
    -Dquarkus.openshift.namespace=${APPS_NAMESPACE} \
    -Dquarkus.openshift.env.vars.kafka-server=${KAFKA_BOOTSTRAP_SERVER} \
    -Dquarkus.openshift.env.vars.quarkus-artemis-url=${AMQ_URL} \
    -Dquarkus.openshift.env.vars.quarkus-artemis-username=${AMQ_USERNAME} \
    -Dquarkus.openshift.env.vars.quarkus-artemis-password=${AMQ_PASSWORD} \
    -Dquarkus.log.category.\"blog.braindose\".level=DEBUG
    ```

## Running The Demo Locally for Testing and Development

### Preparing AMQ Broker Docker Image

> Note: The official AMQ Broker container image from quay.io is not multi-arc and cannot run in MacOS with M1 and M2 chip. This is the reason I build the container image.

1. Clone [Apache ActiveMQ Artemis repo](https://github.com/apache/activemq-artemis.git)
    
    ```
    git clone https://github.com/apache/activemq-artemis.git
    ```

2. Create the package

    ```
    cd ../artemis-distribution
    mvn package
    ```

3. Take note of the build relases:
    
    ```
    % Copying files to .../activemq-artemis/artemis-distribution/target/apache-artemis-2.31.0-SNAPSHOT-bin
    ```

4. Prepare the docker

    ```
    ./prepare-docker.sh --from-local-dist --local-dist-path ../artemis-distribution/target/apache-artemis-2.31.0-SNAPSHOT-bin/apache-artemis-2.31.0-SNAPSHOT
    ```
5. Build Docker Container for multiarc
    ```
    cd ../artemis-distribution/target/apache-artemis-2.31.0-SNAPSHOT-bin/apache-artemis-2.31.0-SNAPSHOT

    docker buildx build --platform linux/amd64,linux/arm64 --push -t chengkuan/apache-artemis:2.17.0-SNAPSHOT -f ./docker/Dockerfile-ubuntu-11 .
    ```
### Run and Test Locally

**Using Quakurs Dev Mode**

1. Run an instance of Artemis Broker:
    ```
    docker compose  -f docker-compose-artemis.yaml up
    ```
2. Run the Camel in DevMode. A Kafka container will be launched at the same time.

    ```
    ./mvnw quarkus:dev
    ```

**Using docker compose**

1. Run the docker compose:
    ```
    docker compose -f docker-compose.yaml up --build
    ```

**Testing the Demo**

1. Start a Kafka producer:
    ```
    docker exec -it checkinamqcamel-kafka-1 /opt/kafka_2.13-2.8.0/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic notification.booking.flights
    ```
2. Enter the following sample message:
    ```
    #--- Red Hat Build Debebzium
    {"_id": "64b92ae087a7f16ec6884394","flightNo": "1466","hotelNight": 1,"departureAirport": "SIN","trackingNo": "8445-2997-0597-1774","fee": 98.51869479413782,"scheduledDepartureTime": "2023-07-21@11:38:20.219","totalCharges": 311.1241479574915,"hotel": "Yotelaire Singapore Changi","scheduledArrivalTime": "2023-07-21@22:42:06.571","hotelCharges": 255.69,"arrivalAirport": "KWL"}
    ```
3. Check the AMQ Console. The new message should be appear in the queue named `checkin`

> Note: Running locally as production mode seems not working. But when run this in OCP, it works. The error message relates to cannot AMQ Broker due to missing `host` and `port` configurations.

## Sampel Data Format

> Note: The latest Debezium version seems to have different message content compared for Red Hat Build Debezium, when using the `unwrap` SMT with `io.debezium.connector.mongodb.transforms.ExtractNewDocumentState`.

The message format in JSON for Red Hat Build Debezium:

```json
{
    "_id": "64b92ae087a7f16ec6884394",
    "flightNo": "1466",
    "hotelNight": 1,
    "departureAirport": "SIN",
    "trackingNo": "8445-2997-0597-1774",
    "fee": 98.51869479413782,
    "scheduledDepartureTime": "2023-07-21@11:38:20.219",
    "totalCharges": 311.1241479574915,
    "hotel": "Yotelaire Singapore Changi",
    "scheduledArrivalTime": "2023-07-21@22:42:06.571",
    "hotelCharges": 255.69,
    "arrivalAirport": "KWL"
}
```

The message format in JSON for latest Debezium:

```json
{
    "schema": {
        "type": "struct",
        "fields": [
            {
                "type": "string",
                "optional": true,
                "field": "_id"
            }
            // omitted ...
        ],
        "optional": false,
        "name": "sia.booking.flights"
    },
    "payload": {
        "_id": "64b92ae087a7f16ec6884394",
        "flightNo": "1466",
        "hotelNight": 1,
        "departureAirport": "SIN",
        "trackingNo": "8445-2997-0597-1774",
        "fee": 98.51869479413782,
        "scheduledDepartureTime": "2023-07-21@11:38:20.219",
        "totalCharges": 311.1241479574915,
        "hotel": "Yotelaire Singapore Changi",
        "scheduledArrivalTime": "2023-07-21@22:42:06.571",
        "hotelCharges": 255.69,
        "arrivalAirport": "KWL"
    }
}
```

## References:
- [Quarkus Artemis JMS](https://docs.quarkiverse.io/quarkus-artemis/dev/index.html)
- [JMS and JPA: A Camel Quarkus Example](https://github.com/apache/camel-quarkus-examples/tree/main/jms-jpa)