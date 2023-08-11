package com.rogerio.servicegateway.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserInfo {
    private boolean status;
    private Set<String> roles;
}
