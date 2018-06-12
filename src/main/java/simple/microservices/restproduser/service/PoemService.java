package simple.microservices.restproduser.service;

import simple.microservices.restproduser.model.Poem;

public interface PoemService {
    void onPoem(Poem poem);
}
