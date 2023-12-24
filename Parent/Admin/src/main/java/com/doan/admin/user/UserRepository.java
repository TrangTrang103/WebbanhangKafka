package com.doan.admin.user;
import com.doan.admin.paging.SearchRepository;
import com.doan.mutual.entity.Category;
import com.doan.mutual.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User,Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email);
    @Query(value = "select * from doanspringboot.users where doanspringboot.category.name like %?1% or doanspringboot.category.email like %?1%", nativeQuery = true)
    Page<Category> findBUserNameAndId(String inputSearch, Pageable pageable);

    User findUserById(Integer id);


}
