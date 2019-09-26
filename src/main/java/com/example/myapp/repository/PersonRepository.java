package com.example.myapp.repository;

import com.example.myapp.models.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, ObjectId> {

    Optional<Person> findById(ObjectId _id);

    List<Person> findAll();

    List<Person> findByName(String name);

    Person findPersonByName(String name);

    List<Person> removeByName(String name);

    List<Person> findByNameAndAge(String name, int age);

}

