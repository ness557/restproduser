package simple.microservices.restproduser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simple.microservices.restproduser.producer.UserActivityProducer;

@Service
public class UserActivityMQService implements UserActivityService {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    private UserActivityProducer producer;

    @Autowired
    public UserActivityMQService(UserActivityProducer activityProducer){
        this.producer = activityProducer;
    }

    @Override
    public void onActivity(String username) {
        logger.info("Producing user's activity: " + username);

        try {
            producer.produceActivity(username);
            logger.info("Done!");
        } catch (Exception e) {
            logger.warn("Caught: " + e);
        }
    }
}
