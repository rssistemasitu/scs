package com.rogerio.servicesecurity.dto.response;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StatusAndRoleResponse {
    private boolean status;
    private Set<String> roles;
}
