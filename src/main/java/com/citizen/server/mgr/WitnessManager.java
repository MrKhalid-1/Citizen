package com.citizen.server.mgr;

import com.citizen.server.model.VWitness;
import com.citizen.server.service.WitnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class WitnessManager {

    @Autowired
    private WitnessService witnessService;

    public VWitness createWitness(VWitness vWitness, MultipartFile imageFile, MultipartFile nationalIdFile,
                                  MultipartFile witnessSignatureFile, MultipartFile citizenSingnatureFile) throws IOException {
        return witnessService.createResource(vWitness, imageFile, nationalIdFile, witnessSignatureFile, citizenSingnatureFile);
    }

    public byte[] getWitnessImage(Long witnessID) throws IOException {
        return witnessService.getImage(witnessID.toString(), "image");
    }

    public byte[] getNationalIdImage(Long witnessID) throws IOException {
        return witnessService.getImage(witnessID.toString(), "nationalId");
    }

    public byte[] getWitnessSignature(Long witnessID) throws IOException {
        return witnessService.getImage(witnessID.toString(), "witnessSignature");
    }

    public byte[] getCitizenSignature(Long witnessID) throws IOException {
        return witnessService.getImage(witnessID.toString(), "citizenSignature");
    }


    public VWitness getWitnessInfo(long id) {
        return witnessService.readResource(id);
    }
}

