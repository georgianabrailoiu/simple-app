package com.example.myapp.controllers;

import com.example.myapp.models.Pet;
import com.example.myapp.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/pet")
public class PetController {

    @Autowired
    private  PetService petService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    List<Pet> findAll(){
        return petService.findAll();
    }

    @RequestMapping (method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    Pet createPet(@RequestBody Pet pet){
        return petService.save(pet);
    }

    @RequestMapping (value = "/name/{name}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    List<Pet> getPetByOwnerName(@PathVariable String name){
        return petService.findByOwnerName(name);
    }
}
