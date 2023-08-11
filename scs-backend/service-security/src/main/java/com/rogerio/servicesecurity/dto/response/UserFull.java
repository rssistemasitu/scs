package com.rogerio.servicesecurity.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rogerio.servicesecurity.entity.Role;
import com.rogerio.servicesecurity.entity.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserFull {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ownerId;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String email;

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

    private Boolean isOwner;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<UserDependent> userDependents = new HashSet<>();
}
