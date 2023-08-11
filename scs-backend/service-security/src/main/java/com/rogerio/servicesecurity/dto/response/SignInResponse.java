package com.rogerio.servicesecurity.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SignInResponse {
    private String token;
    private String type;
    private String id;
    private String email;
    private List<String> roles;
}
