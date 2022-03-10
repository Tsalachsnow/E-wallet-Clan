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
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(nullable = false)
    private String gender;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @Builder.Default
    private LocalDateTime joined = LocalDateTime.now();

    @ToString.Exclude
    @OneToMany(mappedBy = "customer")
    private List<Wallet> wallets;
}
