package com.citizen.server.controller;

import com.citizen.server.mgr.CitizenManager;
import com.citizen.server.model.VCitizen;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @Tag(name = "Citizen")
    @Operation(summary = "Register new citizen")
    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<VCitizen> register(@RequestBody VCitizen vCitizen) {
        LOG.debug("==> addCitizen()");
        return ResponseEntity.ok(citizenManager.createCitizen(vCitizen));
    }

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

