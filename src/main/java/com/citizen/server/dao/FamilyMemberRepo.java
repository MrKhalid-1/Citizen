package com.citizen.server.dao;

import com.citizen.server.entity.TFamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FamilyMemberRepo extends JpaRepository<TFamilyMember, Long> {

    @Query("SELECT member FROM TFamilyMember member WHERE member.citizenId = ?1")
    public List<TFamilyMember> findMemberByCitizenId(Long citizenId);
}
