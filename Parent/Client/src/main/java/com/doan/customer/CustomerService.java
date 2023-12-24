package com.doan.customer;

import com.doan.mutual.entity.AuthenticationType;
import com.doan.mutual.entity.City;
import com.doan.mutual.entity.Customer;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerService {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private CityRepository cityRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
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

    public void savePass(Customer customer, String newpass){
        String encodedPassword = passwordEncoder.encode(newpass);
        customer.setPassword(encodedPassword);
        customerRepository.save(customer);

    }

    private void encodePassword(Customer customer){
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
    }

    public void addNewCustomerUponOAuthLogin(String name, String email,
                                             AuthenticationType authenticationType) {
        Customer customer = new Customer();
        customer.setEmail(email);
        setName(name, customer);

        customer.setEnabled(true);
        customer.setCreatedTime(new Date());
        customer.setAuthenticationType(authenticationType);
        customer.setPassword("");
        customer.setAddressLine1("");
        customer.setPhoneNumber("");


        customerRepository.save(customer);
    }
    private void setName(String name, Customer customer) {
        String[] nameArray = name.split(" ");
        if (nameArray.length < 2) {
            customer.setFirstName(name);
            customer.setLastName("");
        } else {
            String firstName = nameArray[0];
            customer.setFirstName(firstName);

            String lastName = name.replaceFirst(firstName + " ", "");
            customer.setLastName(lastName);
        }
    }

    public boolean verify(String verificationCode) {
        Customer customer = customerRepository.findByVerificationCode(verificationCode);

        if (customer == null || customer.isEnabled()) {
            return false;
        } else {
            customerRepository.enable(customer.getId());
            return true;
        }
    }
    public boolean isEmailUnique(String email) {
        Customer customer = customerRepository.findByEmail(email);
        return customer == null;
    }

}
