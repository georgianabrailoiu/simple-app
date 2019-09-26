package com.example.myapp.services;

import com.example.myapp.models.Person;
import com.example.myapp.models.Pet;
import com.example.myapp.repository.PersonRepository;
import com.example.myapp.repository.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PetServiceImpl implements PetService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> findByOwnerName(String name) {
        Person owner =  personRepository.findByName(name).get(0);
        FindIterable<Document> pets = mongoTemplate
                .getCollection("pet")
                .find(eq("owner", owner), Document.class);

        return StreamSupport.stream(pets.spliterator(), false)
                .map(s->objectMapper.convertValue(s, Pet.class))
                .collect(Collectors.toList());
    }
}
