package com.doan.customer;

import com.doan.mutual.entity.AuthenticationType;
import com.doan.mutual.entity.City;
import com.doan.mutual.entity.Customer;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private CityRepository cityRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public void updateAuthenticationType(Customer customer, AuthenticationType type){
        if (!customer.getAuthenticationType().equals(type)){
            customerRepository.updateAuthenticationType(customer.getId(),type);
        }
    }
    public List<City> listAllCity(){
        return cityRepository.findAllByOrderByNameAsc();
    }

    public void saveRegister(Customer customer){
        Customer cust = new Customer();
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        cust.setPassword(encodedPassword);
        cust.setEnabled(true);
        cust.setFirstName(customer.getFirstName());
        cust.setLastName(customer.getLastName());
        cust.setEmail(customer.getEmail());
        cust.setAddressLine1(customer.getAddressLine1());
        cust.setPhoneNumber(customer.getPhoneNumber());
        cust.setAuthenticationType(AuthenticationType.DATABASE);
        cust.setCity(customer.getCity());
        String randomCode = RandomString.make(64);
        cust.setVerificationCode(randomCode);
        customerRepository.save(cust);

    }

    private void encodePassword(Customer customer){
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
    }
}
