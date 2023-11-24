package blog.braindose.demo.sia;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import blog.braindose.demo.sia.service.JsonMapper;
import blog.braindose.demo.sia.model.Payload;
import blog.braindose.demo.sia.model.Notification;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.inject.Inject;
//import org.eclipse.microprofile.reactive.messaging.Incoming;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;


@ApplicationScoped
public class Resources {

    private static Logger LOGGER = LoggerFactory.getLogger(Resources.class);

    @Inject
    JsonMapper mapper;

    @Inject
    ConnectionFactory connectionFactory;
    
    /* 
    @Incoming("booking")
    public void consume(Payload payload) {
        LOGGER.debug("Payload: {}", mapper.str(payload));
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            context.createProducer().send(context.createQueue("checkin"), mapper.str(payload));
        }
    }
    */

}
