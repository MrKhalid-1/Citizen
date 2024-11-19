package com.citizen.server.mgr;

import com.citizen.server.model.VUser;
import com.citizen.server.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserManager {
    @Autowired
    private CustomUserDetailsService userService;

    public VUser createUser(VUser vUser) {
        return userService.createUser(vUser);
    }

    public VUser updateUser(VUser vUser) {
        return userService.updateUser(vUser);
    }

    public String deleteUser(Long userId) {
        return userService.deleteUser(userId);
    }

    public VUser getUserById(Long userId) {
        return userService.getUserById(userId);
    }

    public List<VUser> getAllUser() {
        return userService.getAllUser();
    }
}
