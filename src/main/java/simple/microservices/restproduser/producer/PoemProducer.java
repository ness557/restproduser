package simple.microservices.restproduser.producer;

import simple.microservices.restproduser.model.Poem;

public interface PoemProducer {
    void producePoem(Poem poem) throws Exception;
}
