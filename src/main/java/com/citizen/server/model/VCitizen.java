package com.citizen.server.model;

import com.citizen.server.common.AppConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public class VCitizen {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mobileNo;
    private String email;
    private String area;
    private String streetNumber;
    private String houseNumber;
    @Enumerated(EnumType.STRING)
    private AppConstants.HousingStatus housingStatus;
    private String uniqueCode;
    private String nationalId;
    private String residenceCard;
    private String passport;
    private String voterId;
    @Enumerated(EnumType.STRING)
    private AppConstants.Status status;
    private List<VFamilyMember> familyMembers;
    //    private List<VWitness> witnesses;
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

    public AppConstants.HousingStatus getHousingStatus() {
        return housingStatus;
    }

    public void setHousingStatus(AppConstants.HousingStatus housingStatus) {
        this.housingStatus = housingStatus;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getResidenceCard() {
        return residenceCard;
    }

    public void setResidenceCard(String residenceCard) {
        this.residenceCard = residenceCard;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public AppConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppConstants.Status status) {
        this.status = status;
    }

    public List<VFamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<VFamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "VCitizen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", area='" + area + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", housingStatus=" + housingStatus +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", residenceCard='" + residenceCard + '\'' +
                ", passport='" + passport + '\'' +
                ", voterId='" + voterId + '\'' +
                ", status=" + status +
                ", familyMembers=" + familyMembers +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}