package com.example.myapp.controllers;

import com.example.myapp.controllers.exception.PersonNotFoundException;
import com.example.myapp.models.Person;
import com.example.myapp.services.PersonService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/person")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    private WebClient webClient;

    @GetMapping("/username/{username}")
    public ResponseEntity<Person> get(@PathVariable String username) throws PersonNotFoundException {

        if(personService.findPersonByName(username) == null) {
            throw PersonNotFoundException.create(username);
        }
        Person rsp = personService.findPersonByName(username);
        return  new ResponseEntity<Person>(personService.findPersonByName(username), HttpStatus.OK);
    }

    @RequestMapping (value = "/id/{_id}", method = RequestMethod.GET)
    @ResponseBody
    Optional<Person> findById(@PathVariable ObjectId _id){
        return personService.findById(_id);
    }

    @RequestMapping (value="/all", method = RequestMethod.GET)
    @ResponseBody
    List<Person> findAll(){
        return personService.findAll();
    }

    @RequestMapping (method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    Person createPerson(@RequestBody Person person){
        return personService.save(person);
    }

    @RequestMapping (value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    List<Person> findByName(@PathVariable String name){
        return personService.findByName(name);
    }

    @RequestMapping (value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    Person updateById(@PathVariable ObjectId id, @RequestBody Person updatedPerson){
        updatedPerson.set_id(id);
        return personService.save(updatedPerson);
    }

    @RequestMapping (value = "/{name}", method = RequestMethod.DELETE)
    @ResponseBody
    List<Person> deleteByName(@PathVariable String name){
        return personService.removeByName(name);
    }

    @RequestMapping (value = "/{name}/{age}", method = RequestMethod.GET)
    @ResponseBody
    List<Person> deleteByName(@PathVariable("name") String name, @PathVariable("age") int age){
        return personService.findByNameAndAge(name, age);
    }

    @RequestMapping (value = "/age/{age}", method = RequestMethod.GET)
    @ResponseBody
    Person findByAge(@PathVariable int age){
        return personService.findByAge(age);
    }

    @RequestMapping (value="/hello", method = RequestMethod.GET)
    @ResponseBody
    String hello(){
        return "HELOO";
    }

    @RequestMapping (value="/slow", method = RequestMethod.GET)
    @ResponseBody
    private String getAllPersons() throws InterruptedException {
        Thread.sleep(5000L); // delay
        return "MERGE";
    }

    @RequestMapping (value="/blocking", method = RequestMethod.GET)
    @ResponseBody
    public String getTweetsBlocking() throws InterruptedException {
        logger.info("Starting BLOCKING Controller!");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/person/slow", HttpMethod.GET, null,
                new ParameterizedTypeReference<String>(){});

        String result = response.getBody();
        logger.info(result);
        logger.info("Exiting BLOCKING Controller!");
        return result;
    }

    @GetMapping(value = "/non-blocking",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getTweetsNonBlocking() {
        logger.info("Starting NON-BLOCKING Controller!");
        Flux<String> tweetFlux = WebClient.create()
                .get()
                .uri("http://localhost:8080/person")
                .retrieve()
                .bodyToFlux(String.class);

        tweetFlux.subscribe(tweet -> logger.info(tweet));
        logger.info("Exiting NON-BLOCKING Controller!");
        return tweetFlux;
    }

    @RequestMapping (value="/allWEB", method = RequestMethod.GET)
    @ResponseBody
    public Flux<Person> getAllPersonsWEB() {
        Flux<Person> persons = WebClient.create()
                .get()
                .uri("http://localhost:8080/person/all")
                .retrieve()
                .bodyToFlux(Person.class);
        return persons;
    }

}
