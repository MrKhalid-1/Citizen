package com.citizen.server.dao;

import com.citizen.server.entity.TCitizen;
import com.citizen.server.entity.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CitizenRepo extends JpaRepository<TCitizen, Long> {
    @Query("Select t from TCitizen t where t.email = ?1")
    TCitizen findByEmail(String email);
}

