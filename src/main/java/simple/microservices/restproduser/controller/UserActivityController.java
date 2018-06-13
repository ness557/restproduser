package simple.microservices.restproduser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import simple.microservices.restproduser.service.UserActivityService;

@RestController
@RequestMapping("/activity")
public class UserActivityController {

    private UserActivityService service;

    @Autowired
    public UserActivityController(UserActivityService activityService){
        this.service = activityService;
    }

    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    public ResponseEntity handleActivity(@RequestBody String username){
        service.onActivity(username);
        return new ResponseEntity(HttpStatus.OK);
    }
}
