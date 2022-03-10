package com.example.walletapi.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;


@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@MappedSuperclass
public abstract class BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

}
