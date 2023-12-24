package com.doan.customer;

import com.doan.mutual.entity.AuthenticationType;
import com.doan.mutual.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.verificationCode = ?1")
    public Customer findByVerificationCode(String code);

    @Query("UPDATE Customer c SET c.enabled = true, c.verificationCode = null WHERE c.id = ?1")
    @Modifying
    public void enable(Integer id);

    public Customer findByResetPasswordToken(String token);

    @Query("UPDATE Customer c SET c.authenticationType = ?2 WHERE c.id = ?1")
    @Modifying
    public void updateAuthenticationType(Integer customerId, AuthenticationType type);
}
