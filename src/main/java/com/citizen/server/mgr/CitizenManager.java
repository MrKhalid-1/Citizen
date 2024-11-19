package com.citizen.server.mgr;

import com.citizen.server.common.AppConstants;
import com.citizen.server.context.UserContextUtil;
import com.citizen.server.model.VCitizen;
import com.citizen.server.model.VFamilyMember;
import com.citizen.server.service.CitizenService;
import com.citizen.server.service.FamilyMemberService;
import com.citizen.server.service.WitnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CitizenManager {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private FamilyMemberService familyMemberService;

    @Autowired
    private WitnessService witnessService;

    public VCitizen createCitizen(VCitizen vCitizen) {
        System.out.println(vCitizen);
        VCitizen savedCitizen = citizenService.createResource(vCitizen);
        System.out.println(savedCitizen);
        List<VFamilyMember> retMembers = new ArrayList<>();
//        List<VWitness> witnessList = new ArrayList<>();
        for (VFamilyMember familyMember : vCitizen.getFamilyMembers()) {
            System.out.println(savedCitizen.getId() + "saved citizen id");
            familyMember.setCitizenId(savedCitizen.getId());
            VFamilyMember savedMember = familyMemberService.createResource(familyMember);
            retMembers.add(savedMember);
        }
//        for (VWitness witness : vCitizen.getWitnesses()) {
//            System.out.println(savedCitizen.getId() + "saved citizen id");
//            witness.setCitizenId(savedCitizen.getId());
//            VWitness savedWitness = witnessService.createResource(witness);
//            System.out.println("this is saved witness" + savedWitness);
//            witnessList.add(savedWitness);
//        }
        vCitizen.setFamilyMembers(retMembers);
        return vCitizen;
    }

    public VCitizen getCitizenInfo(long id) {
        return citizenService.readResource(id);
    }

    public void deleteCitizenInfo(long id) {
        citizenService.deleteResource(id);
    }


    public VCitizen updateCitizen(VCitizen vCitizenInfo) {
        return citizenService.updateResource(vCitizenInfo);
    }

    public List<VCitizen> searchOrder() {
        try {
            VUserProfile vUserProfile = UserContextUtil.getUserProfile();
            String userName = vUserProfile.getUsername();
            List<String> userRole = vUserProfile.getRoles();
            if (userRole.contains(AppConstants.UserRoles.CITIZEN.name())) {
                VCitizen vCitizen = citizenService.searchByCitizen(userName);
                return List.of(vCitizen);
            }else {
                return citizenService.search();
            }
        } catch (Throwable t) {
            String errorMsg = "Failed to placeOrder";

            throw new RuntimeException(errorMsg, t);
        }

//        return citizenService.search();
    }

}


