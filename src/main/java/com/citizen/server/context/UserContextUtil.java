package com.citizen.server.context;

import java.util.*;

import com.citizen.server.common.AppConstants;
import com.citizen.server.mgr.VUserProfile;
import com.citizen.server.service.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextUtil {
    private static final Logger LOG = LoggerFactory.getLogger(UserContextUtil.class);
        public static VUserProfile getUserProfile() {
            VUserProfile vUserProfile = new VUserProfile();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return null;
            }
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails customUserDetails = (CustomUserDetails) principal;
                vUserProfile.setUsername(customUserDetails.getUsername());
                List<String> roles = new ArrayList<>();
                roles.add(customUserDetails.getUserRole());
                vUserProfile.setName(customUserDetails.getName().trim());
                vUserProfile.setRoles(roles);
                customUserDetails.getUserRole();
            }
            return vUserProfile;
        }

        public static boolean isAdmin(VUserProfile vUserProfile) {
            List<String> roles = vUserProfile.getRoles();
            if (roles.size() > 0) {
                return roles.contains(AppConstants.UserRoles.ADMIN.name());
            }
            return false;
        }

        public static boolean isCitizen(VUserProfile vUserProfile) {
            List<String> roles = vUserProfile.getRoles();
            if (roles.size() > 0) {
                return roles.contains(AppConstants.UserRoles.CITIZEN.name());
            }
            return false;
        }

        public static boolean isGovernor(VUserProfile vUserProfile) {
            List<String> roles = vUserProfile.getRoles();
            if (roles.size() > 0) {
                return roles.contains(AppConstants.UserRoles.GOVERNOR.name());
            }
            return false;
        }

        public static boolean isMUKHTAR(VUserProfile vUserProfile) {
            List<String> roles = vUserProfile.getRoles();
            if (roles.size() > 0) {
                return roles.contains(AppConstants.UserRoles.MUKHTAR.name());
            }
            return false;
        }
    }