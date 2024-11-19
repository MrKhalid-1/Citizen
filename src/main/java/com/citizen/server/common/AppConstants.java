package com.citizen.server.common;

public class AppConstants {

    public enum UserRoles {
       CITIZEN,
        MUKHTAR,
        GOVERNOR,
        ADMIN
    }

    public enum HousingStatus {
        OWNER,
        TENANT
    }

    public enum Status {
        PENDING,
        VERIFIED_BY_MUKHTAR,
        VERIFIED_BY_GOVERNOR,
        APPROVED,
        REJECTED
    }
}

