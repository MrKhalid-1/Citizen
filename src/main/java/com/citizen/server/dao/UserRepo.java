package com.citizen.server.dao;

import com.citizen.server.entity.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<TUser, Long> {

    @Query("Select u from TUser u where u.userName = ?1")
    TUser findByUsername(String UserName);
}
