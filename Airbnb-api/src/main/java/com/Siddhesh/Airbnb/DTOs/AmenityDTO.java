package com.Siddhesh.Airbnb.DTOs;

public class AmenityDTO {
    private long id;
    private String name;

    public AmenityDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}