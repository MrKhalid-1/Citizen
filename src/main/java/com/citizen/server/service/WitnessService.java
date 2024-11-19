package com.citizen.server.service;

import com.citizen.server.dao.WitnessRepo;
import com.citizen.server.entity.TCitizen;
import com.citizen.server.entity.TFamilyMember;
import com.citizen.server.entity.TWitness;
import com.citizen.server.model.VCitizen;
import com.citizen.server.model.VFamilyMember;
import com.citizen.server.model.VWitness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class WitnessService {

    @Autowired
    private WitnessRepo witnessRepository;

    public VWitness createResource(VWitness vWitness) {
        TWitness tWitness = MapViewToEntity(vWitness);
        TWitness witness = witnessRepository.save(tWitness);
        return mapEntityToView(witness);
    }

    public VWitness readResource(long citizenId) {
        TWitness witness = witnessRepository.findWitnessByCitizenId(citizenId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Witness not found"));
        return mapEntityToView(witness);
    }

    private VWitness mapEntityToView(TWitness witness) {
        VWitness vWitness = new VWitness();
        vWitness.setId(witness.getId());
        vWitness.setCitizenId(witness.getCitizenId());
        vWitness.setName(witness.getName());
        vWitness.setMobileNo(witness.getMobileNo());
        vWitness.setCard(witness.getCard());
        vWitness.setCreatedDate(witness.getCreatedDate());
        return vWitness;
    }

    private TWitness MapViewToEntity(VWitness vWitness) {
        TWitness witness = new TWitness();
        witness.setId(vWitness.getId());
        witness.setCitizenId(vWitness.getCitizenId());
        witness.setName(vWitness.getName());
        witness.setMobileNo(vWitness.getMobileNo());
        witness.setCard(vWitness.getCard());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        witness.setCreatedDate(LocalDate.now().format(formatter));
        return witness;
    }

    public List<VWitness> getWintessByCitizenId(Long citizenId) {
        List<VWitness> vWitnesses = new ArrayList<>();
        List<TWitness> tWitnesses = witnessRepository.findWitnessByCitizenId(citizenId);
        for (TWitness tWitness : tWitnesses) {
            VWitness vWitness = mapEntityToView(tWitness);
            vWitnesses.add(vWitness);
        }
        return vWitnesses;
    }
}
