package com.example.walletapi.repositories;

import com.example.walletapi.models.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findPersonByUserNameAndPassword(String username, String password);
    Optional<Customer> findCustomersByEmailAndId(String email,Long id);

    Optional<Customer> findPersonByUserName(String userName);
}
