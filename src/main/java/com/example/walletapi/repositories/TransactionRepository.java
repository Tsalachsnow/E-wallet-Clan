package com.example.walletapi.repositories;


import com.example.walletapi.models.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select transaction from Transaction transaction where transaction.wallet.id = :walletId order by transaction.time desc")
    List<Transaction> findAllforWallet(@Param("walletId") Long walletId);

}
