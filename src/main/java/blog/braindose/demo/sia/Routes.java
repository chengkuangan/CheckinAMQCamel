package blog.braindose.demo.sia;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import blog.braindose.demo.sia.service.JsonMapper;
import blog.braindose.demo.sia.model.Payload;
import blog.braindose.demo.sia.model.Notification;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.inject.Inject;

@ApplicationScoped
public class Routes extends RouteBuilder {

    private static Logger LOGGER = LoggerFactory.getLogger(Routes.class);

    @Inject
    JsonMapper mapper;

    @ConfigProperty(name = "payload.string.delimiter")
    String delimiter;

    @Override
    public void configure() throws Exception {
        from("kafka:{{kafka.incoming.topic}}?brokers={{kafka.server}}")
                .log("Message received from Kafka : ${body}")
                .unmarshal(new JacksonDataFormat(Payload.class))
                .process(
                        exchange -> {
                            Message in = exchange.getIn();
                            Payload p = (Payload) in.getBody();
                            in.setBody(p.toDelimited(delimiter));
                        })
                .to("jms:checkin?disableReplyTo=true&testConnectionOnStartup=true");
    }
}
