package com.example.walletapi.models;

import com.example.walletapi.models.BaseClass;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "customer")
public class Customer extends BaseClass {
    @NotNull
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;


    @NotNull
    @Column(nullable = false)
    private String gender;

    @Enumerated(EnumType.STRING)
    private KycLevel level;

    @Builder.Default
    private LocalDateTime joined = LocalDateTime.now();


    @OneToOne
    private Wallet wallets;

    public Double getLimit() {
        return level.getTransactionLimit();
    }
}
