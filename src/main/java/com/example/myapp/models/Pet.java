package com.example.myapp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pet")
public class Pet {

    @Id
    private String _id;
    private Person owner;
    private String name;
    private String type;

    public Pet(Person owner, String name, String type) {
        this.owner = owner;
        this.name = name;
        this.type = type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "owner=" + owner +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
