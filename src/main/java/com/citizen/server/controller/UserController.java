package com.citizen.server.controller;

import com.citizen.server.context.UserContextUtil;
import com.citizen.server.mgr.UserManager;
import com.citizen.server.mgr.VUserProfile;
import com.citizen.server.model.VUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(com.citizen.server.controller.UserController.class);

    @Autowired
    private UserManager userManager;

    @Tag(name = "Home")
    @Operation(summary = "Home")
    @RequestMapping(method = RequestMethod.GET, path = "/home")
    public HashMap<Object, String> home() {
        HashMap<Object, String> hashMap = new HashMap<>();
        LOG.debug("==> Home()");
        hashMap.put("home", "WelCome Home Khalid For Citizenship");
        return hashMap;
    }

    @Tag(name = "User")
    @Operation(summary = "Get user profile")
    @RequestMapping(method = RequestMethod.GET, path = "/profile")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<VUserProfile> getProfile() {
        LOG.debug("==> getProfile()");
        VUserProfile vUserProfile = UserContextUtil.getUserProfile();
        return ResponseEntity.ok(vUserProfile);
    }

    @Tag(name = "User")
    @Operation(summary = "Add new user")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<VUser> addUser(@RequestBody VUser vUser) {
        LOG.debug("==> addUser()");
        return ResponseEntity.ok(userManager.createUser(vUser));
    }

    @Tag(name = "User")
    @Operation(summary = "Update user")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<VUser> updateUser(@RequestBody VUser vUser) {
        LOG.debug("==>  updateUser()");
        return ResponseEntity.ok(userManager.updateUser(vUser));
    }

    @Tag(name = "User")
    @Operation(summary = "Delete user")
    @RequestMapping(method = RequestMethod.DELETE, path = "/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        LOG.debug("==> deleteUser()");
        return ResponseEntity.ok(userManager.deleteUser(userId));
    }

    @Tag(name = "User")
    @Operation(summary = "User by Id")
    @RequestMapping(method = RequestMethod.GET, path = "/{userId}")
    public ResponseEntity<VUser> getUserById(@PathVariable Long userId) {
        LOG.debug("==> addUser()");
        return ResponseEntity.ok(userManager.getUserById(userId));
    }

    @Tag(name = "User")
    @Operation(summary = "All User")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<VUser>> getAllUser() {
        LOG.debug("==> allUser()");
        return ResponseEntity.ok(userManager.getAllUser());
    }
}
