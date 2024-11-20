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
        // Step 1: Map View to Entity
        TCitizen tCitizen = mapViewToEntity(vCitizen);

        // Step 2: Save the citizen
//        TCitizen savedCitizen = citizenRepository.save(tCitizen);

        // Step 3: Generate the unique code
        String uniqueCode = generateRandomString(6);
        tCitizen.setUniqueCode(uniqueCode);
        sendCurrentPassword(uniqueCode,vCitizen.getEmail());
        tCitizen.setStatus(AppConstants.Status.valueOf("PENDING"));

        // Step 2: Save the citizen
        TCitizen savedCitizen = citizenRepository.save(tCitizen);

        List<VFamilyMember> retMembers = new ArrayList<>();
        for (VFamilyMember familyMember : vCitizen.getFamilyMembers()) {
            System.out.println(savedCitizen.getId() + "  saved citizen id");
            familyMember.setCitizenId(savedCitizen.getId());
            VFamilyMember savedMember = familyMemberService.createResource(familyMember);
            retMembers.add(savedMember);
        }
        vCitizen.setFamilyMembers(retMembers);

        // Step 4: Save images and store their paths
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveFile(imageFile, "image", savedCitizen.getId());
            savedCitizen.setImage(imagePath);
        }

        if (nationalIdFile != null && !nationalIdFile.isEmpty()) {
            String nationalIdPath = saveFile(nationalIdFile, "nationalId", savedCitizen.getId());
            savedCitizen.setNationalId(nationalIdPath);
        }

        // Step 5: Save the updated citizen entity
        savedCitizen = citizenRepository.save(savedCitizen);

        // Step 6: Create and save TUser
        TUser tUser = new TUser();
        tUser.setName(vCitizen.getName());
        tUser.setUserName(vCitizen.getEmail());
        tUser.setEmail(vCitizen.getEmail());
        tUser.setPassword(uniqueCode);
        tUser.setUserRole(AppConstants.UserRoles.CITIZEN);

        // Set creation date for TUser
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        tUser.setCreatedDate(LocalDate.now().format(formatter));

        // Save the user
        userRepo.save(tUser);

        // Step 7: Return the mapped view
        return mapEntityToView(savedCitizen);
    }


    private String saveFile(MultipartFile file, String subFolder, Long citizenID) throws IOException {
        // Ensure the file is a .jpg image
//        if (!file.getOriginalFilename().endsWith(".jpg")) {
//            throw new IOException("Only .jpg files are allowed");
//        }
        String fileName = citizenID + "_" + System.currentTimeMillis() + ".jpg"; // Save as .jpg
        Path filePath = Paths.get(storagePath, subFolder, fileName);
        Files.createDirectories(filePath.getParent()); // Ensure the directory exists
        file.transferTo(filePath.toFile()); // Save the file to disk
        return filePath.toString();
    }


    public byte[] getImage(String witnessID, String imageType) throws IOException {
        TCitizen citizen = citizenRepository.findById(Long.valueOf(witnessID))
                .orElseThrow(() -> new IOException("Witness not found"));
        String filePath = "";
        switch (imageType) {
            case "image":
                filePath = citizen.getImage();
                break;
            case "nationalId":
                filePath = citizen.getNationalId();
                break;
        }

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        } else {
            throw new IOException(imageType + " file not found");
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
        TCitizen updatedCitizen = citizenRepository.save(citizen);
        return mapEntityToView(updatedCitizen);
    }

    public VCitizen searchByCitizen(String userName) {
        TCitizen citizens = citizenRepository.findByEmail(userName);
        Long citizenId = citizens.getId();
//        readResource(citizenId);
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
        citizen.setNationalId(vCitizen.getNationalId());
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

//    // Get a list of all citizens with family members
//    public List<VCitizen> getAllCitizens() {
//        return citizenRepository.findAll().stream()
//                .map(this::toView)
//                .collect(Collectors.toList());
//    }
//
//    // Get a single citizen by ID with family members
//    public Optional<VCitizen> getCitizenById(Long id) {
//        return citizenRepository.findById(id)
//                .map(this::toView);
//    }
//
//    // Update a citizen and its family members
//    @Transactional
//    public Optional<VCitizen> updateCitizen(Long id, VCitizen vCitizenDetails) {
//        return citizenRepository.findById(id).map(existingCitizen -> {
//            TCitizen updatedCitizen = toEntity(vCitizenDetails);
//            updatedCitizen.setId(existingCitizen.getId());
//            // Persist updates for citizen and associated family members
//            return toView(citizenRepository.save(updatedCitizen));
//        });
//    }
//
//    // Delete a citizen and associated family members
//    @Transactional
//    public void deleteCitizen(Long id) {
//        if (citizenRepository.existsById(id)) {
//            citizenRepository.deleteById(id);
//        } else {
//            throw new IllegalArgumentException("Citizen with ID " + id + " not found.");
//        }
//    }
//

}
