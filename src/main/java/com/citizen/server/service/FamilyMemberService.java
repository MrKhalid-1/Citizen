package com.citizen.server.service;

import com.citizen.server.dao.FamilyMemberRepo;
import com.citizen.server.entity.TFamilyMember;
import com.citizen.server.model.VFamilyMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyMemberService {

    @Autowired
    FamilyMemberRepo familyMemberRepository;

    public VFamilyMember createResource(VFamilyMember vFamilyMember) {
        TFamilyMember familyMember = MapViewToEntity(vFamilyMember);
        TFamilyMember familyMember1 = familyMemberRepository.save(familyMember);
        return mapEntityToView(familyMember1);
    }


    private VFamilyMember mapEntityToView(TFamilyMember familyMember) {
        VFamilyMember vFamilyMember = new VFamilyMember();
        vFamilyMember.setId(familyMember.getId());
        vFamilyMember.setCitizenId(familyMember.getCitizenId());
        vFamilyMember.setName(familyMember.getName());
        vFamilyMember.setAge(familyMember.getAge());
        vFamilyMember.setImage(familyMember.getImage());
        vFamilyMember.setCreatedDate(familyMember.getCreatedDate());
        return vFamilyMember;
    }

    private TFamilyMember MapViewToEntity(VFamilyMember vFamilyMember) {
        TFamilyMember familyMember = new TFamilyMember();
        familyMember.setId(vFamilyMember.getId());
        familyMember.setCitizenId(vFamilyMember.getCitizenId());
        familyMember.setName(vFamilyMember.getName());
        familyMember.setAge(vFamilyMember.getAge());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        familyMember.setCreatedDate(LocalDate.now().format(formatter));
        return familyMember;
    }

    public List<VFamilyMember> getMemberByCitizenId(Long citizenId) {
        List<VFamilyMember> familyMembers = new ArrayList<>();
        List<TFamilyMember> TFamilyMembers = familyMemberRepository.findMemberByCitizenId(citizenId);
        for (TFamilyMember TFamilyMember : TFamilyMembers) {
            VFamilyMember VFamilyMember = mapEntityToView(TFamilyMember);
            familyMembers.add(VFamilyMember);
        }
        return familyMembers;
    }
}