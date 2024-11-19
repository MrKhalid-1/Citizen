package com.citizen.server.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class VWitness {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long citizenId;
    private String name;
    private String mobileNo;
    //  private Blob Card;
    private String card;
    private String createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
    }

    @Override
    public String toString() {
        return "VWitness{" +
                "id=" + id +
                ", citizenId=" + citizenId +
                ", name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", card='" + card + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}