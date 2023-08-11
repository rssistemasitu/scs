package com.rogerio.servicesecurity.controller;

import com.rogerio.servicesecurity.dto.request.SignInRequest;
import com.rogerio.servicesecurity.dto.request.SignUpDependentRequest;
import com.rogerio.servicesecurity.dto.request.SignUpRequest;
import com.rogerio.servicesecurity.dto.response.SignInResponse;
import com.rogerio.servicesecurity.dto.response.StatusAndRoleResponse;
import com.rogerio.servicesecurity.dto.response.UserFull;
import com.rogerio.servicesecurity.entity.User;
import com.rogerio.servicesecurity.jwt.JwtUtils;
import com.rogerio.servicesecurity.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.rogerio.servicesecurity.utils.Constants.CREATE_USER_MESSAGE;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    private static Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest request) {
        userService.signUp(request);
        log.info("auth-controller - [flow: signup-user]");
        return ResponseEntity.status(HttpStatus.OK).body(CREATE_USER_MESSAGE);
    }

    @PostMapping("/signup-dependent")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> registerUserDependent(@RequestBody SignUpDependentRequest request) {
        userService.signUpDependent(request);
        log.info("auth-controller - [flow: signup-dependent]");
        return ResponseEntity.status(HttpStatus.OK).body(CREATE_USER_MESSAGE);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> authenticateUser(@RequestBody SignInRequest request) {
        SignInResponse jwtResponse = userService.signIn(request);
        log.info("auth-controller - [flow: signin-user]");
        return ResponseEntity.ok(jwtResponse);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        jwtUtils.validateJwtToken(jwt);
        log.info("auth-controller - [flow: validate-user]");
        return ResponseEntity.ok("Token is valid");
    }

    @GetMapping("/logout")
    @PreAuthorize("hasRole('USER') or hasRole('DEPENDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String logout() {
        log.info("auth-controller - [flow: signout]");
        return "Clear session"; // Redireciona para a página de login após o logout
    }
}
