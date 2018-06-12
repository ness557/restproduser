package simple.microservices.restproduser.producer;

public interface UserActivityProducer {
    void produceActivity(String username) throws Exception;
}
