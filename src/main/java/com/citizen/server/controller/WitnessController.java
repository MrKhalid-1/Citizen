package com.citizen.server.controller;

import com.citizen.server.mgr.CitizenManager;
import com.citizen.server.mgr.WitnessManager;
import com.citizen.server.model.VCitizen;
import com.citizen.server.model.VWitness;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/witness")
public class WitnessController {
    private static final Logger LOG = LoggerFactory.getLogger(com.citizen.server.controller.WitnessController.class);

    @Autowired
    private WitnessManager witnessManager;


    @Tag(name = "Witness")
    @Operation(summary = "Add new witness")
    @PreAuthorize("hasRole('MUKHTAR')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<VWitness> addWitness(@RequestBody VWitness vWitness) {
        LOG.debug("==> addWitness()");
        return ResponseEntity.ok(witnessManager.createWitness(vWitness));
    }

    @Tag(name = "Witness")
    @Operation(summary = "Get witness by citizenId ")
    @RequestMapping(method = RequestMethod.GET, path = "/{citizenId}")
    public ResponseEntity<VWitness> getWitness(@PathVariable Long citizenId) {
        LOG.debug("==> getWitnessInfo()");
        return ResponseEntity.ok(witnessManager.getWitnessInfo(citizenId));
    }

}
