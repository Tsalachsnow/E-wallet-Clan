package com.example.walletapi.repositories;

import com.example.walletapi.models.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
   Customer findCustomersByEmail(String email);
    Optional<Customer> findCustomersByEmailAndWalletsAcc(String email, String acc);


    Optional<Customer> findByUserName(String userName);
    Optional<Customer> findByEmail(String email);
//    Customer findByEmail(String email);

    void deleteByEmail(String email);

}
