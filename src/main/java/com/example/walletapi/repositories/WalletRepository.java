package com.example.walletapi.repositories;

import com.example.walletapi.models.Wallet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("select wallet from Wallet wallet where wallet.customer.id = :id order by wallet.balance desc , wallet.currency asc")
    List<Wallet> findAllforCustomer(@Param("id") Long id);

    @Query("select wallet from Wallet wallet order by wallet.id asc , wallet.balance desc")
    List<Wallet> findAll();
}
