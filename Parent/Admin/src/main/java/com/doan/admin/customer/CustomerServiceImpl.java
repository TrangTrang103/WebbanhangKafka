package com.doan.admin.customer;

import com.doan.mutual.entity.Category;
import com.doan.mutual.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl {
    @Autowired
    private CustomerRepository customerRepository;

    public Page<Customer> findAllCustomerPage(Pageable pageable){
        return customerRepository.findAll(pageable);
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Page<Customer> searchCustomer(String inputSearch, Pageable pageable){
        return customerRepository.findByCustomer_NameOrIdOrEmail(inputSearch,pageable);
    }

    public void deleteById(Integer id){
        customerRepository.deleteById(id);
    }
}
