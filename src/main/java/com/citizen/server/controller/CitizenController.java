package com.citizen.server.controller;

import com.citizen.server.mgr.CitizenManager;
import com.citizen.server.model.VCitizen;
import com.citizen.server.model.VWitness;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 1. register ( unique code generate)
 * 2. add witness
 * 3. show all register users
 * 4. show all with witness
 * 5. show single register user ( only  for register)
 */
@RestController
@RequestMapping("/citizen")
//@RequestMapping("/api/citizens")
public class CitizenController {
    private static final Logger LOG = LoggerFactory.getLogger(com.citizen.server.controller.CitizenController.class);

    @Autowired
    private CitizenManager citizenManager;

//    @Tag(name = "Citizen")
//    @Operation(summary = "Register new citizen")
//    @RequestMapping(method = RequestMethod.POST, path = "/register")
//    public ResponseEntity<VCitizen> register(@RequestBody VCitizen vCitizen) {
//        LOG.debug("==> addCitizen()");
//        return ResponseEntity.ok(citizenManager.createCitizen(vCitizen));
//    }

    @Tag(name = "Citizen")
    @Operation(summary = "Register new citizen")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE , path = "/register")
    public ResponseEntity<?> createCitizen(
            @RequestPart("citizen") String citizenJson,  // accept JSON as a string
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestPart(value = "nationalId", required = false) MultipartFile nationalIdFile){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            VCitizen vCitizen = objectMapper.readValue(citizenJson, VCitizen.class);
            VCitizen savedCitizen = citizenManager.createCitizen(vCitizen, imageFile, nationalIdFile);
            return ResponseEntity.ok(savedCitizen);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving citizen or images: " + e.getMessage());
        }
    }

    @Tag(name = "Citizen")
    @Operation(summary = "Retrieve citizen image")
    @RequestMapping(method = RequestMethod.GET, path = "/image/{citizenID}")
    public ResponseEntity<?> getCitizenImage(@PathVariable Long citizenID) {
        try {
            byte[] imageBytes = citizenManager.getCitizenImage(citizenID);

            // Set the content type to image/jpeg for a JPEG image
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

            return ResponseEntity.ok()
                    .headers(headers) // Add headers
                    .body(imageBytes); // Send the image bytes in the response
        } catch (IOException e) {
            return ResponseEntity.status(404)
                    .body("Image not found for citizen ID: " + citizenID);
        }
    }

    @Tag(name = "Citizen")
    @Operation(summary = "Retrieve Citizen national ID image")
    @RequestMapping(method = RequestMethod.GET, path = "/image/nationalId/{citizenID}")
    public ResponseEntity<?> getNationalIdImage(@PathVariable Long witnessID) {
        try {
            byte[] imageBytes = citizenManager.getNationalIdImage(witnessID);

            // Set the content type to image/jpeg for a JPEG image
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

            return ResponseEntity.ok()
                    .headers(headers) // Add headers
                    .body(imageBytes); // Send the image bytes in the response
        } catch (IOException e) {
            return ResponseEntity.status(404)
                    .body("National ID image not found for Citizen ID: " + witnessID);
        }
    }

//   @Tag(name = "Citizen")
//    @Operation(summary = "Retrieve familyMember ID image")
//    @RequestMapping(method = RequestMethod.GET, path = "/image/familyMember/{CitizenID}")
//    public ResponseEntity<?> getFamilyMemberIdImage(@PathVariable Long citizenID) {
//        try {
//            byte[] imageBytes = citizenManager.getFamilyMemberImage(citizenID);
//
//            // Set the content type to image/jpeg for a JPEG image
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
//
//            return ResponseEntity.ok()
//                    .headers(headers) // Add headers
//                    .body(imageBytes); // Send the image bytes in the response
//        } catch (IOException e) {
//            return ResponseEntity.status(404)
//                    .body("familyMember image not found for Citizen ID: " + citizenID);
//        }
//    }

//




    @Tag(name = "Citizen")
    @Operation(summary = "update citizen")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<VCitizen> updateCitizen(@RequestBody VCitizen vCitizen) {
        LOG.debug("==> addCitizen()");
        return ResponseEntity.ok(citizenManager.updateCitizen(vCitizen));
    }

    @Tag(name = "Citizen")
    @Operation(summary = "Get Citizen")
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<VCitizen> getCitizen(@PathVariable Long id) {
        LOG.debug("==> getCitizenInfo()");
        return ResponseEntity.ok(citizenManager.getCitizenInfo(id));
    }

    @Tag(name = "Citizen")
    @Operation(summary = "Delete Citizen info")
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteCitizen(@PathVariable Long id) {
        LOG.debug("==> deleteCitizen()");
        citizenManager.deleteCitizenInfo(id);
        return ResponseEntity.ok("Successfully deleted Citizen!");
    }

    @Tag(name = "Citizen")
    @Operation(summary = "Search order")
    @RequestMapping(method = RequestMethod.GET, path = "/search")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List> searchCitizen(@ParameterObject Pageable pageable, @Context HttpServletRequest request) {
    public ResponseEntity<List> searchCitizen() {
        LOG.debug("==> searchCitizen()");
        List<VCitizen> citizenList = citizenManager.searchOrder();
        return ResponseEntity.ok(citizenList);
    }


}

