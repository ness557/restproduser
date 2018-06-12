package simple.microservices.restproduser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import simple.microservices.restproduser.model.Poem;
import simple.microservices.restproduser.producer.PoemProducer;

@Service
public class PoemMQService implements PoemService {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    private PoemProducer producer;

    public PoemMQService(PoemProducer poemProducer){
        this.producer = poemProducer;
    }

    @Override
    public void onPoem(Poem poem) {
        logger.info("Producing poem: " + poem);

        try {
            producer.producePoem(poem);
            logger.info("Done");
        } catch (Exception e) {
            logger.warn("Caught: " + e);
        }
    }
}
