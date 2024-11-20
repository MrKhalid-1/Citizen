package com.citizen.server.controller;

import com.citizen.server.mgr.WitnessManager;
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

@RestController
@RequestMapping("/witness")
public class WitnessController {
    private static final Logger LOG = LoggerFactory.getLogger(com.citizen.server.controller.WitnessController.class);

    @Autowired
    private WitnessManager witnessManager;

    @Tag(name = "Witness")
    @Operation(summary = "Add new witness")
    @PreAuthorize("hasRole('MUKHTAR')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createWitness(
            @RequestPart("witness") String witnessJson,  // accept JSON as a string
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestPart(value = "nationalId", required = false) MultipartFile nationalIdFile,
            @RequestPart(value = "witnessSignature", required = false) MultipartFile witnessSignatureFile,
            @RequestPart(value = "citizenSignature", required = false) MultipartFile citizenSignatureFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            VWitness vWitness = objectMapper.readValue(witnessJson, VWitness.class);
            VWitness savedWitness = witnessManager.createWitness(vWitness, imageFile, nationalIdFile, witnessSignatureFile, citizenSignatureFile);
            return ResponseEntity.ok(savedWitness);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving witness or images: " + e.getMessage());
        }
    }

    @Tag(name = "Witness")
    @Operation(summary = "Retrieve witness image")
    @RequestMapping(method = RequestMethod.GET, path = "/image/{witnessID}")
    public ResponseEntity<?> getWitnessImage(@PathVariable Long witnessID) {
        try {
            byte[] imageBytes = witnessManager.getWitnessImage(witnessID);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(404)
                    .body("Image not found for witness ID: " + witnessID);
        }
    }

    @Tag(name = "Witness")
    @Operation(summary = "Retrieve witness national ID image")
    @RequestMapping(method = RequestMethod.GET, path = "/image/nationalId/{witnessID}")
    public ResponseEntity<?> getNationalIdImage(@PathVariable Long witnessID) {
        try {
            byte[] imageBytes = witnessManager.getNationalIdImage(witnessID);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(404)
                    .body("National ID image not found for witness ID: " + witnessID);
        }
    }


    @Tag(name = "Witness")
    @Operation(summary = "Retrieve witness signature image")
    @RequestMapping(method = RequestMethod.GET, path = "/image/witnessSignature/{witnessID}")
    public ResponseEntity<?> getWitnessSignature(@PathVariable Long witnessID) {
        try {
            byte[] imageBytes = witnessManager.getWitnessSignature(witnessID);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(404)
                    .body("Witness signature image  not found for witness ID: " + witnessID);
        }
    }

    @Tag(name = "Witness")
    @Operation(summary = "Retrieve citizen signature image")
    @RequestMapping(method = RequestMethod.GET, path = "/image/citizenSignature/{witnessID}")
    public ResponseEntity<?> getCitizenSignature(@PathVariable Long witnessID) {
        try {
            byte[] imageBytes = witnessManager.getCitizenSignature(witnessID);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(404)
                    .body("Citizen signature image not found for witness ID: " + witnessID);
        }
    }

    @Tag(name = "Witness")
    @Operation(summary = "Get witness by citizenId ")
    @RequestMapping(method = RequestMethod.GET, path = "/{citizenId}")
    public ResponseEntity<VWitness> getWitness(@PathVariable Long citizenId) {
        LOG.debug("==> getWitnessInfo()");
        return ResponseEntity.ok(witnessManager.getWitnessInfo(citizenId));
    }

}
