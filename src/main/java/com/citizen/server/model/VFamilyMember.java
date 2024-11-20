package com.citizen.server.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class VFamilyMember {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long citizenId;
    private String name;
    private Long age;
    private String image;
    private String createdDate;
//    private byte[] photo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "VFamilyMember{" +
                "id=" + id +
                ", citizenId=" + citizenId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}

