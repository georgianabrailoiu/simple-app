package com.example.myapp.services;

import com.example.myapp.models.Pet;
import org.bson.types.ObjectId;

import java.util.List;

public interface PetService {
    List<Pet> findAll();

    Pet save(Pet pet);

    List<Pet> findByOwnerName(String name);
}
