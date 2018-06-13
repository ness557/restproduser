package simple.microservices.restproduser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import simple.microservices.restproduser.model.Poem;
import simple.microservices.restproduser.service.PoemService;

@RestController
@RequestMapping("/poem")
public class PoemController {

    private PoemService service;

    @Autowired
    public PoemController(PoemService poemService){
        this.service = poemService;
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    private ResponseEntity handlePoem(@RequestBody Poem poem){
        service.onPoem(poem);
        return new ResponseEntity(HttpStatus.OK);
    }
}
