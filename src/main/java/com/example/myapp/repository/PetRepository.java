package com.example.myapp.repository;

import com.example.myapp.models.Pet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
    List<Pet> findAll();

    Pet save(Pet pet);

    List<Pet> findByOwnerName(String name);
}
