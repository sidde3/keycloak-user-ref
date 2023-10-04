package org.example.demo;


import javax.json.Json;
import javax.json.JsonObject;

public class App {
    public static void main(String[] args) {
        String value = null;
        JsonObject empObject = Json.createObjectBuilder()
                .add("empName", "Jai")
                .add("empType", value != null ? Json.createObjectBuilder()
                        .add("Hello", "World") : Json.createObjectBuilder().add("name",""))
                .build();
        System.out.println(empObject);
    }

}
