package com.citizen.server.entity;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "family_member")
public class TFamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "citizen_id")
    private Long citizenId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private Long age;

    @Column(name = "image")
    private String image;

   @Column(name = "created_at")
    private String createdDate;

//    @Lob
//    @Column(name = "photo")
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
        return "TFamilyMember{" +
                "id=" + id +
                ", citizenId=" + citizenId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", image='" + image + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}