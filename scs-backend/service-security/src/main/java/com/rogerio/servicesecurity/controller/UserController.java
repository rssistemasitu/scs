package com.rogerio.servicesecurity.controller;

import com.rogerio.servicesecurity.dto.response.StatusAndRoleResponse;
import com.rogerio.servicesecurity.dto.response.UserFull;
import com.rogerio.servicesecurity.jwt.JwtUtils;
import com.rogerio.servicesecurity.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user-auth-info")
    @PreAuthorize("hasRole('USER') or hasRole('DEPENDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<StatusAndRoleResponse> getRoles(HttpServletRequest request) {
        final StatusAndRoleResponse infoUser = jwtUtils.getRoleFromUserLogged();
        log.info("auth-controller - [flow: user-auth-info]");
        return ResponseEntity.ok(infoUser);
    }

    @GetMapping("/user-full")
    @PreAuthorize("hasRole('USER') or hasRole('DEPENDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<UserFull> getUserWithDependentsFull(HttpServletRequest request) {
        final UserFull user = userService.getUserWithDependentsFull();
        log.info("auth-controller - [flow: get-user-with-dependents]");
        return ResponseEntity.ok(user);
    }
}
