package com.citizen.server.service;

import com.citizen.server.common.AppConstants;
import com.citizen.server.dao.CitizenRepo;
import com.citizen.server.dao.UserRepo;
import com.citizen.server.entity.TCitizen;
import com.citizen.server.entity.TUser;
import com.citizen.server.entity.TWitness;
import com.citizen.server.model.VCitizen;
import com.citizen.server.model.VFamilyMember;
import com.citizen.server.model.VWitness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import java.util.Random;

@Service
public class CitizenService {

    @Autowired
    FamilyMemberService familyMemberService;

    @Autowired
    WitnessService witnessService;

    @Autowired
    CitizenRepo citizenRepository;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${upload.path.for.citizen.image}")
    private String storagePath;

//    public VCitizen createResources(VCitizen vCitizen) {
//        TCitizen citizen = mapViewToEntity(vCitizen);
//        String uniqueCode = generateRandomString(6);
//        citizen.setUniqueCode(uniqueCode);
//        sendCurrentPassword(uniqueCode,vCitizen.getEmail());
////        citizen.setUniqueCode(generateRandomString(6));
//        citizen.setStatus(AppConstants.Status.valueOf("PENDING"));
//        TCitizen citizen1 = citizenRepository.save(citizen);
//
//        TUser tUser = new TUser();
//        tUser.setName(vCitizen.getName());
//        tUser.setUserName(vCitizen.getEmail());
//        tUser.setEmail(vCitizen.getEmail());
//        tUser.setPassword(uniqueCode);
//        tUser.setUserRole(AppConstants.UserRoles.CITIZEN);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        tUser.setCreatedDate(LocalDate.now().format(formatter));
//        userRepo.save(tUser);
//
//        return mapEntityToView(citizen1);
//    }

    public VCitizen createResource(VCitizen vCitizen, MultipartFile imageFile, MultipartFile nationalIdFile) throws IOException {
        TCitizen tCitizen = mapViewToEntity(vCitizen);
        String uniqueCode = generateRandomString(6);
        tCitizen.setUniqueCode(uniqueCode);
        sendCurrentPassword(uniqueCode,vCitizen.getEmail());
        tCitizen.setStatus(AppConstants.Status.valueOf("PENDING"));
        TCitizen savedCitizen = citizenRepository.save(tCitizen);
        List<VFamilyMember> retMembers = new ArrayList<>();
        for (VFamilyMember familyMember : vCitizen.getFamilyMembers()) {
            System.out.println(savedCitizen.getId() + "  saved citizen id");
            familyMember.setCitizenId(savedCitizen.getId());
            VFamilyMember savedMember = familyMemberService.createResource(familyMember);
            retMembers.add(savedMember);
        }
        vCitizen.setFamilyMembers(retMembers);

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveFile(imageFile, "image", savedCitizen.getId());
            savedCitizen.setImage(imagePath);
        }

        if (nationalIdFile != null && !nationalIdFile.isEmpty()) {
            String nationalIdPath = saveFile(nationalIdFile, "nationalId", savedCitizen.getId());
            savedCitizen.setNationalId(nationalIdPath);
        }
        savedCitizen = citizenRepository.save(savedCitizen);
        TUser tUser = new TUser();
        tUser.setName(vCitizen.getName());
        tUser.setUserName(vCitizen.getEmail());
        tUser.setEmail(vCitizen.getEmail());
        tUser.setPassword(uniqueCode);
        tUser.setUserRole(AppConstants.UserRoles.CITIZEN);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        tUser.setCreatedDate(LocalDate.now().format(formatter));
        userRepo.save(tUser);
        return mapEntityToView(savedCitizen);
    }


    private String saveFile(MultipartFile file, String subFolder, Long citizenID) throws IOException {
        String fileName = citizenID + "_" + System.currentTimeMillis() + ".jpg";
        Path basePath = Paths.get(storagePath).toAbsolutePath();
        Path filePath = basePath.resolve(subFolder).resolve(fileName);
        Files.createDirectories(filePath.getParent());
        file.transferTo(filePath.toFile());
        System.out.println(basePath.relativize(filePath).toString());
        return basePath.relativize(filePath).toString();
    }

    public byte[] getImage(String citizenId, String imageType) throws IOException {
        TCitizen citizen = citizenRepository.findById(Long.valueOf(citizenId))
                .orElseThrow(() -> new IOException("Citizen not found"));
        String filePath = "";
        switch (imageType) {
            case "image":
                filePath = citizen.getImage();
                break;
            case "nationalId":
                filePath = citizen.getNationalId();
                break;
            default:
                throw new IOException("Unknown image type: " + imageType);
        }
        if (filePath == null || filePath.isEmpty()) {
            throw new IOException(imageType + " path is empty or null for Citizen ID: " + citizenId);
        }
        if (filePath.startsWith("\\")) {
            filePath = filePath.substring(1);
        }
        Path fullPath = Paths.get(storagePath, filePath).toAbsolutePath();
        if (Files.exists(fullPath)) {
            System.out.println("File exists. Reading bytes...");
            return Files.readAllBytes(fullPath);
        } else {
            throw new IOException(imageType + " file not found at path: " + fullPath);
        }
    }

    public VCitizen readResource(long id) {
        TCitizen citizen = citizenRepository.findById(id).orElseThrow(() -> new RuntimeException("Citizen not found"));
        return mapEntityToView(citizen);
    }

    public void deleteResource(long id) {
        citizenRepository.deleteById(id);
    }

    public VCitizen updateResource(VCitizen vCitizen) {
        TCitizen citizen = mapViewToEntity(vCitizen);
        citizen.setImage(vCitizen.getImage());
        citizen.setNationalId(vCitizen.getNationalId());
        TCitizen updatedCitizen = citizenRepository.save(citizen);
        return mapEntityToView(updatedCitizen);
    }

    public VCitizen searchByCitizen(String userName) {
        TCitizen citizens = citizenRepository.findByEmail(userName);
        Long citizenId = citizens.getId();
        TCitizen citizen = citizenRepository.findById(citizenId).orElseThrow(() -> new RuntimeException("Citizen not found"));
        return mapEntityToView(citizen);
    }

    public List<VCitizen> search() {
        List<TCitizen> citizens = citizenRepository.findAll();
        List<VCitizen> vCitizens = new ArrayList<>();
        for (TCitizen citizen : citizens) {
            vCitizens.add(mapEntityToView(citizen));
        }
        return vCitizens;
    }

    private VCitizen mapEntityToView(TCitizen citizen) {
        VCitizen vCitizen = new VCitizen();
        vCitizen.setId(citizen.getId());
        vCitizen.setName(citizen.getName());
        vCitizen.setMobileNo(citizen.getMobileNo());
        vCitizen.setEmail(citizen.getEmail());
        vCitizen.setArea(citizen.getArea());
        vCitizen.setStreetNumber(citizen.getStreetNumber());
        vCitizen.setHouseNumber(citizen.getHouseNumber());
        vCitizen.setHousingStatus(citizen.getHousingStatus());
        vCitizen.setUniqueCode(citizen.getUniqueCode());
        vCitizen.setImage(citizen.getImage());
        vCitizen.setNationalId(citizen.getNationalId());
//        vCitizen.setResidenceCard(citizen.getResidenceCard());
//        vCitizen.setPassport(citizen.getPassport());
//        vCitizen.setVoterId(citizen.getVoterId());
        vCitizen.setStatus(citizen.getStatus());
        List<VFamilyMember> familyMembers = familyMemberService.getMemberByCitizenId(citizen.getId());
        vCitizen.setFamilyMembers(familyMembers);
//        List<VWitness> witnesses = witnessService.getWintessByCitizenId(citizen.getId());
//        vCitizen.setWitnesses(witnesses);
        vCitizen.setCreatedDate(citizen.getCreatedDate());
        return vCitizen;
    }


    private TCitizen mapViewToEntity(VCitizen vCitizen) {
        TCitizen citizen = new TCitizen();
        citizen.setId(vCitizen.getId());
        citizen.setName(vCitizen.getName());
        citizen.setMobileNo(vCitizen.getMobileNo());
        citizen.setEmail(vCitizen.getEmail());
        citizen.setArea(vCitizen.getArea());
        citizen.setStreetNumber(vCitizen.getStreetNumber());
        citizen.setHouseNumber(vCitizen.getHouseNumber());
        if (vCitizen.getHousingStatus() == null) {
            citizen.setHousingStatus(AppConstants.HousingStatus.TENANT);
        } else {
            citizen.setHousingStatus(vCitizen.getHousingStatus());
        }
//        citizen.setHousingStatus(vCitizen.getHousingStatus());
        citizen.setUniqueCode(vCitizen.getUniqueCode());
//        citizen.setNationalId(vCitizen.getNationalId());
//        citizen.setResidenceCard(vCitizen.getResidenceCard());
//        citizen.setPassport(vCitizen.getPassport());
//        citizen.setVoterId(vCitizen.getVoterId());
        if (vCitizen.getStatus() == null) {
            citizen.setStatus(AppConstants.Status.PENDING);
        } else {
            citizen.setStatus(vCitizen.getStatus());
        }
//        citizen.setStatus(vCitizen.getStatus());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        citizen.setCreatedDate(LocalDate.now().format(formatter));

        return citizen;
    }


    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }

        return randomString.toString();
    }

    private void sendCurrentPassword(String currentPassword, String email) {
        String subject = "Unique Code Request";
        String messageText = "Dear User,\n\nYour unique Code is: " + currentPassword
                + "\n\nPlease consider your Code if you did not request this.";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailUsername);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(messageText);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email. Please try again later.", e);
        }
    }
}
