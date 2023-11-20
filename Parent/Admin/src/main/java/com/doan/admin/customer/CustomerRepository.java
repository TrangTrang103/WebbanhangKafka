package com.doan.admin.customer;

import com.doan.mutual.entity.Category;
import com.doan.mutual.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "select * from doanspringboot.customers where doanspringboot.customers.first_name like %?1% or doanspringboot.customers.last_name like %?1% or doanspringboot.customers.id like %?1%  or doanspringboot.customers.email like %?1%", nativeQuery = true)
    Page<Customer> findByCustomer_NameOrIdOrEmail(String inputSearch, Pageable pageable);
}
