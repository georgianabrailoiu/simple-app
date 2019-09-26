package com.example.myapp.controllers.exception;

public class PersonNotFoundException extends Exception {

    private String name;

    private PersonNotFoundException(String name) {
        this.name = name;
    }

   public static PersonNotFoundException create(String name) {
       return new PersonNotFoundException(name);
   }
}
