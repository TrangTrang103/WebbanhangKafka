package com.doan.admin.user;
import com.doan.admin.paging.SearchRepository;
import com.doan.mutual.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends SearchRepository<User,Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email);



}
