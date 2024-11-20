package com.citizen.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class VWitness {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long citizenId;
    private String name;
    private String mobileNo;
    private String image;
    private String nationalId;
    private String citizenSignature;
    private String witnessSignature;
    //  private Blob Card;
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

    @Override
    public String toString() {
        return "VWitness{" +
                "id=" + id +
                ", citizenId=" + citizenId +
                ", name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", image='" + image + '\'' +
                ", citizenSignature='" + citizenSignature + '\'' +
                ", witnessSignature='" + witnessSignature + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}