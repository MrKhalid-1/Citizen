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

    @Column(name = "card", nullable = false)
    private String card;

    @Column(name = "created_at")
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
        return "TWitness{" +
                "id=" + id +
                ", citizenId=" + citizenId +
                ", name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", card='" + card + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
