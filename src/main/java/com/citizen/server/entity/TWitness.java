package com.citizen.server.entity;

import jakarta.persistence.*;

/**
 * /*
 *  When adding a witness, the Mukhtar opens a specific interface to enter the witness's details, which include:
 *      - Witness name
 *      - Phone number
 *      - Upload witness documents, such as National ID and Residence card
 *
 *      - Electronic signature for both the witness and citizen usi
 *  */

@Entity
@Table(name = "witness")
public class TWitness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "citizen_id")
    private Long citizenId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mobile", nullable = false)
    private String mobileNo;

    @Column(name = "image")
    private String image;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "citizen_signature")
    private String citizenSignature;

    @Column(name = "witness_signature")
    private String witnessSignature;

    @Column(name = "created_at")
    private String createdDate;

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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCitizenSignature() {
        return citizenSignature;
    }

    public void setCitizenSignature(String citizenSignature) {
        this.citizenSignature = citizenSignature;
    }

    public String getWitnessSignature() {
        return witnessSignature;
    }

    public void setWitnessSignature(String witnessSignature) {
        this.witnessSignature = witnessSignature;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}

