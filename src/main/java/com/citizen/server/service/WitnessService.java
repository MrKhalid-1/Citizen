package com.citizen.server.service;

import com.citizen.server.dao.WitnessRepo;
import com.citizen.server.entity.TWitness;
import com.citizen.server.model.VWitness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class WitnessService {

    @Value("${upload.path.for.witness.image}")
    private String storagePath;

    @Autowired
    private WitnessRepo witnessRepository;

    public VWitness createResource(VWitness vWitness, MultipartFile imageFile, MultipartFile nationalIdFile,
                                   MultipartFile witnessSignatureFile, MultipartFile citizenSignatureFile) throws IOException {
        TWitness tWitness = mapViewToEntity(vWitness);
        TWitness tWitness1 = witnessRepository.save(tWitness);

        // Save images and store their paths
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveFile(imageFile, "image", tWitness1.getId());
            tWitness1.setImage(imagePath);
        }

        if (nationalIdFile != null && !nationalIdFile.isEmpty()) {
            String nationalIdPath = saveFile(nationalIdFile, "nationalId", tWitness1.getId());
            tWitness1.setNationalId(nationalIdPath);
        }

        if (witnessSignatureFile != null && !witnessSignatureFile.isEmpty()) {
            String witnessSignaturePath = saveFile(witnessSignatureFile, "witnessSignature", tWitness1.getId());
            tWitness1.setWitnessSignature(witnessSignaturePath);
        }

        if (citizenSignatureFile != null && !citizenSignatureFile.isEmpty()) {
            String citizenSignaturePath = saveFile(citizenSignatureFile, "citizenSignature", tWitness1.getId());
            tWitness1.setCitizenSignature(citizenSignaturePath);
        }

        TWitness savedWitness = witnessRepository.save(tWitness1);
        return mapEntityToView(savedWitness);
    }

    //    private String saveFile(MultipartFile file, String subFolder, Long witnessID) throws IOException {
//        String fileName = witnessID + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        Path filePath = Paths.get(storagePath, subFolder, fileName);
//        Files.createDirectories(filePath.getParent()); // Ensure the directory exists
//        file.transferTo(filePath.toFile()); // Save the file to disk
//        return filePath.toString();
//    }
    private String saveFile(MultipartFile file, String subFolder, Long witnessID) throws IOException {
        // Ensure the file is a .jpg image
        if (!file.getOriginalFilename().endsWith(".jpg")) {
            throw new IOException("Only .jpg files are allowed");
        }

        String fileName = witnessID + "_" + System.currentTimeMillis() + ".jpg"; // Save as .jpg
        Path filePath = Paths.get(storagePath, subFolder, fileName);
        Files.createDirectories(filePath.getParent()); // Ensure the directory exists
        file.transferTo(filePath.toFile()); // Save the file to disk
        return filePath.toString();
    }


    public byte[] getImage(String witnessID, String imageType) throws IOException {
        TWitness witness = witnessRepository.findById(Long.valueOf(witnessID))
                .orElseThrow(() -> new IOException("Witness not found"));
        String filePath = "";
        switch (imageType) {
            case "image":
                filePath = witness.getImage();
                break;
            case "nationalId":
                filePath = witness.getNationalId();
                break;
            case "witnessSignature":
                filePath = witness.getWitnessSignature();
                break;
            case "citizenSignature":
                filePath = witness.getCitizenSignature();
                break;
        }

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        } else {
            throw new IOException(imageType + " file not found");
        }
    }


    public VWitness readResource(long citizenId) {
        TWitness witness = witnessRepository.findWitnessByCitizenId(citizenId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Witness not found"));
        return mapEntityToView(witness);
    }

    private TWitness mapViewToEntity(VWitness vWitness) {
        TWitness witness = new TWitness();
        witness.setId(vWitness.getId());
        witness.setCitizenId(vWitness.getCitizenId());
        witness.setName(vWitness.getName());
        witness.setMobileNo(vWitness.getMobileNo());
//        witness.setCard(vWitness.getCard());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        witness.setCreatedDate(LocalDate.now().format(formatter));
        return witness;
    }

    private VWitness mapEntityToView(TWitness witness) {
        VWitness vWitness = new VWitness();
        vWitness.setId(witness.getId());
        vWitness.setCitizenId(witness.getCitizenId());
        vWitness.setName(witness.getName());
        vWitness.setMobileNo(witness.getMobileNo());
        vWitness.setNationalId(witness.getNationalId());
        vWitness.setImage(witness.getImage());
        vWitness.setCitizenSignature(witness.getCitizenSignature());
        vWitness.setWitnessSignature(witness.getWitnessSignature());
        vWitness.setCreatedDate(witness.getCreatedDate());
        return vWitness;
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
