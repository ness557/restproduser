package simple.microservices.restproduser.producer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class UserActivityMQProducer implements UserActivityProducer {

    @Value("${activemq.address}")
    private String activeMQAddress;

    @Value("${activemq.topic.activity}")
    private String topicName;

    @Override
    public void produceActivity(String username) throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(activeMQAddress);

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createTopic(topicName);

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        TextMessage textMessage = session.createTextMessage(username);

        producer.send(textMessage);

        session.close();
        connection.close();
    }
}
