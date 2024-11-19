package com.citizen.server.mgr;

import com.citizen.server.model.VCitizen;
import com.citizen.server.model.VWitness;
import com.citizen.server.service.WitnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WitnessManager {

    @Autowired
    private WitnessService witnessService;

    public VWitness createWitness(VWitness VWitness) {
        return witnessService.createResource(VWitness);
    }

    public VWitness getWitnessInfo(long id) {
        return witnessService.readResource(id);
    }
}

