package com.citizen.server.mgr;

import com.citizen.server.model.VWitness;
import com.citizen.server.service.WitnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class WitnessManager {

//    @Value("${upload.path.for.witness.image}")
//    private String storagePath;

    @Autowired
    private WitnessService witnessService;
//
//    public VWitness createWitness(VWitness vWitness, MultipartFile cardFile) throws IOException {
//        if (cardFile != null && !cardFile.isEmpty()) {
//            String fileName = System.currentTimeMillis() + "_" + cardFile.getOriginalFilename();
//            Path filePath = Paths.get(storagePath, fileName);
//            Files.createDirectories(filePath.getParent()); // Ensure the directory exists
//            cardFile.transferTo(filePath.toFile()); // Save the file to disk
//            vWitness.setCard(filePath.toString()); // Set the file path in the entity
//        }
//        return witnessService.createResource(vWitness);
//    }

    public VWitness createWitness(VWitness vWitness, MultipartFile imageFile, MultipartFile nationalIdFile,
                                  MultipartFile witnessSignatureFile,MultipartFile citizenSingnatureFile) throws IOException {
        return witnessService.createResource(vWitness, imageFile, nationalIdFile, witnessSignatureFile,citizenSingnatureFile);
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

