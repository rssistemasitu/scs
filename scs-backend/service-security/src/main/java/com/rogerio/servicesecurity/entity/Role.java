package com.rogerio.servicesecurity.entity;

import com.rogerio.servicesecurity.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    private ERole name;
}
