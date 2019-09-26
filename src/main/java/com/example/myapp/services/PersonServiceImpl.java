package com.example.myapp.services;

import com.example.myapp.models.Person;
import com.example.myapp.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.eq;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Optional<Person> findById(ObjectId _id) {
        return personRepository.findById(_id);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public List<Person> removeByName(String name) {
        return personRepository.removeByName(name);
    }

    @Override
    public List<Person> findByName(String name) {
        FindIterable<Document> persons = mongoTemplate
                .getCollection("person")
                .find(eq("name", name), Document.class);

        return StreamSupport.stream(persons.spliterator(), false)
                .map(s->objectMapper.convertValue(s, Person.class))
                .collect(Collectors.toList());
       // return personRepository.findByName(name);
    }

    @Override
    public Person findPersonByName(String name) {
        return personRepository.findPersonByName(name);
    }

    @Override
    public List<Person> findByNameAndAge(String name, int age) {
        FindIterable<Document> persons = mongoTemplate
                .getCollection("person")
                .find(Filters.and(eq("name", name),
                        eq("age", age)), Document.class);

        return StreamSupport.stream(persons.spliterator(), false)
                .map(s->objectMapper.convertValue(s, Person.class))
                .collect(Collectors.toList());

       // return personRepository.findByNameAndAge(name, age);
    }

    @Override
    public Person findByAge(int age) {
        Document person = mongoTemplate
                .getCollection("person")
                .find(eq("age", age), Document.class)
                .first();

        return objectMapper.convertValue(person, Person.class);
    }

}
