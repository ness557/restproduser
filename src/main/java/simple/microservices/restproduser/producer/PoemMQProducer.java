package simple.microservices.restproduser.producer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import simple.microservices.restproduser.model.Poem;

import javax.jms.*;

@Component
public class PoemMQProducer implements PoemProducer {

    @Value("${activemq.address}")
    private String activeMQAddress;

    @Value("${activemq.topic.poem}")
    private String topicName;

    @Override
    public void producePoem(Poem poem) throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(activeMQAddress);

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createTopic(topicName);

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        MapMessage message = session.createMapMessage();
        message.setString("username", poem.getUsername());
        message.setString("data", poem.getData());
        message.setObject("tags", poem.getTags());

        producer.send(message);

        session.close();
        connection.close();
    }
}
