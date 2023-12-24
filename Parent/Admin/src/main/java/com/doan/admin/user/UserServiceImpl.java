package com.doan.admin.user;

import com.doan.mutual.entity.Category;
import com.doan.mutual.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public List<User> getAllUser(){
      return (List<User>) userRepository.findAll();
    }
    public Page<User> getAll(Pageable page){
        return userRepository.findAll(page);
    }

    public User saveUser(User user){
        User u = new User();
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setEnabled(true);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        u.setPassword(encodedPassword);
        u.setEmail(user.getEmail());
        u.setRoles(user.getRoles());
        userRepository.save(u);
        return u;
    }
}
