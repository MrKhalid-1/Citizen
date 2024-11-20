package com.citizen.server.entity;

import com.citizen.server.common.AppConstants.HousingStatus;
import com.citizen.server.common.AppConstants.Status;
import jakarta.persistence.*;

import java.util.Date;

/*
{
  "citizen": {
    "name": "John Doe",
    "phone": "+1234567890",
    "email": "johndoe@example.com",
    "address": {
      "area": "Downtown",
      "streetNumber": "123",
      "houseNumber": "456",
      "housingStatus": "Owner"
    },
    "documents": {
      "nationalId": "national_id.jpg",
      "anotherId": "ration_card.jpg"
    },
    "selfImage": "self_image.jpg",
    "uniqueCode": "A1B2C3",
  "status":"citizen"}
}
 */
@Entity
@Table(name = "citizen")
public class TCitizen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mobile_no", nullable = false)
    private String mobileNo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "area")
    private String area;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "housing_status")
    @Enumerated(EnumType.STRING)
    private HousingStatus housingStatus;

    @Column(name = "unique_code")
    private String uniqueCode;

    @Column(name = "image")
    private String image;

    @Column(name = "national_id")
    private String nationalId;

//    @Column(name = "residence_card")
//    private String residenceCard;
//
//    @Column(name = "passport")
//    private String passport;
//
//    @Column(name = "voter_id")
//    private String voterId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public HousingStatus getHousingStatus() {
        return housingStatus;
    }

    public void setHousingStatus(HousingStatus housingStatus) {
        this.housingStatus = housingStatus;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "TCitizen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", area='" + area + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", housingStatus=" + housingStatus +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", image='" + image + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", status=" + status +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}