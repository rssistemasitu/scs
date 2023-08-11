package com.rogerio.servicesecurity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ownerId;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String resetPasswordToken;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpf;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateBirth;

    private String cardName;

    private String cardNumber;

    private String expirationDate;

    private Integer cvv;

    private Boolean status;

    private Boolean isOwner = Boolean.TRUE;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<String> userDependents = new HashSet<>();
}
