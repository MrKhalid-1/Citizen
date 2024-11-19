package com.citizen.server.dao;

import com.citizen.server.entity.TFamilyMember;
import com.citizen.server.entity.TWitness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WitnessRepo extends JpaRepository<TWitness, Long> {

    @Query("SELECT witness FROM TWitness witness WHERE witness.citizenId = ?1")
    public List<TWitness> findWitnessByCitizenId(Long citizenId);

}
