package com.example.walletapi.models;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "wallets")
public class Wallet extends BaseClass {

    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Builder.Default
    private Double balance = 0D;

    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

    @Column(unique = true)
    private String acc;

    @ToString.Exclude
    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;

}
