package com.rogerio.servicesecurity.service;

import com.rogerio.servicesecurity.config.CustomUserDetails;
import com.rogerio.servicesecurity.dto.request.SignInRequest;
import com.rogerio.servicesecurity.dto.request.SignUpDependentRequest;
import com.rogerio.servicesecurity.dto.request.SignUpRequest;
import com.rogerio.servicesecurity.dto.response.SignInResponse;
import com.rogerio.servicesecurity.dto.response.StatusAndRoleResponse;
import com.rogerio.servicesecurity.dto.response.UserDependent;
import com.rogerio.servicesecurity.dto.response.UserFull;
import com.rogerio.servicesecurity.entity.Role;
import com.rogerio.servicesecurity.entity.User;
import com.rogerio.servicesecurity.enums.ERole;
import com.rogerio.servicesecurity.exception.error.ConflictException;
import com.rogerio.servicesecurity.exception.error.NotFoundException;
import com.rogerio.servicesecurity.jwt.JwtUtils;
import com.rogerio.servicesecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rogerio.servicesecurity.utils.Constants.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Usuário com id %s não foi encontrado.", id)));
    }

    public List<User> getAll(String role) {
        jwtService.verifyPermissionFromUserLogged(PERMISSION_ADMIN);
        return userRepository.findAll();
    }

    public User signUp(SignUpRequest request) {
        verifyIfExistsByEmail(request.getEmail());
        User user = buildUserOwner(request);
        Set<Role> roles = new HashSet<>();
        Role role = roleService.getByName(ERole.ROLE_USER);
        roles.add(role);
        user.setRoles(roles);
        return saveUser(user);
    }

    public User signUpDependent(SignUpDependentRequest request) {
        User user = buildUserDependent(request);
        Set<Role> roles = new HashSet<>();
        Role role = roleService.getByName(ERole.ROLE_DEPENDENT);
        roles.add(role);
        user.setRoles(roles);
        return saveUser(user);
    }

    public SignInResponse signIn(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return SignInResponse.builder()
                .id(userDetails.getId())
                .token(jwt)
                .type("Bearer")
                .email(userDetails.getUsername())
                .roles(roles)
                .build();
    }

    private User buildUserOwner(SignUpRequest request) {
        request.setPassword(encoder.encode(request.getPassword()));
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .cpf(request.getCpf())
                .dateBirth(request.getDateBirth())
                .cardName(request.getCardName())
                .cardNumber(request.getCardNumber())
                .cvv(request.getCvv())
                .expirationDate(request.getExpirationDate())
                .status(true)
                .roles(null)
                .isOwner(true)
                .build();
        return user;
    }

    private User buildUserDependent(SignUpDependentRequest request) {
        CustomUserDetails userFromSession = jwtService.getUserFromSession();
        request.setPassword(encoder.encode(request.getPassword()));
        User user = User.builder()
                .ownerId(userFromSession.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .cpf(request.getCpf())
                .dateBirth(request.getDateBirth())
                .status(true)
                .roles(null)
                .isOwner(false)
                .build();
        return user;
    }

    public UserFull getUserWithDependentsFull() {
        CustomUserDetails userFromSession = jwtService.getUserFromSession();

        User user = getById(userFromSession.getId());

        UserFull userFull = buildUserFull(user);

        Set<User> usersDependents = user.getUserDependents()
                .stream()
                .map(dep -> getById(dep))
                .collect(Collectors.toSet());

        Set<UserDependent> usersDependentsFull = usersDependents
                .stream()
                .map(userDep -> convertToUserDependent(userDep))
                .collect(Collectors.toSet());

        userFull.setUserDependents(usersDependentsFull);

        System.out.println(userFull.getUserDependents());

        return userFull;
    }
    private UserDependent convertToUserDependent(User request) {
        UserDependent userDependent = UserDependent.builder()
                .ownerId(request.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .cpf(request.getCpf())
                .dateBirth(request.getDateBirth())
                .status(request.getStatus())
                .isOwner(request.getIsOwner())
                .build();
        return userDependent;
    }


    private UserFull buildUserFull(User user) {
        UserFull userFull = UserFull.builder()
                .ownerId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .cpf(user.getCpf())
                .dateBirth(user.getDateBirth())
                .cardName(user.getCardName())
                .cardNumber(user.getCardNumber())
                .cvv(user.getCvv())
                .expirationDate(user.getExpirationDate())
                .status(user.getStatus())
                .isOwner(user.getIsOwner())
                .build();
        return userFull;
    }

    public User saveUser (User user) {
        final User userSaved = userRepository.save(user);
        if(Objects.nonNull(userSaved.getIsOwner()) && !userSaved.getIsOwner()) {
            User userOwner = getById(userSaved.getOwnerId());
            userOwner.getUserDependents().add(userSaved.getId());
            userRepository.save(userOwner);
        }
        return userSaved;
    }

    public boolean verifyIfExistsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(CONFLICT_EXCEPTION_MESSAGE,"Usuário", email));
        }
        return true;
    }

    public StatusAndRoleResponse getRoleFromUserLogged() {
        final CustomUserDetails userFromSession = jwtService.getUserFromSession();

        final User user = getById(userFromSession.getId());
        Set<String> roles = user.getRoles().stream().map(item -> item.getName().name()).collect(Collectors.toSet());

        return StatusAndRoleResponse.builder().roles(roles).status(user.getStatus()).build();
    }
}
