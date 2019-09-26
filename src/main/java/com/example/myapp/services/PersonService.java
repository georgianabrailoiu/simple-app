package com.example.myapp.services;

import com.example.myapp.models.Person;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Optional<Person> findById(ObjectId _id);

    List<Person> findAll();

    Person save(Person person);

    List<Person> removeByName(String name);

    List<Person> findByName(String name);

    Person findPersonByName(String name);

    List<Person> findByNameAndAge(String name, int age);

    Person findByAge(int age);
}