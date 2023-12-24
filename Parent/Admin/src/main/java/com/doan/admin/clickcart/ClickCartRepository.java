package com.doan.admin.clickcart;

import com.doan.mutual.entity.ClickCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClickCartRepository extends JpaRepository<ClickCart,Integer> {
    @Query(value = "SELECT c.id_product, count(c.id_product) as amount  FROM click_cart AS c GROUP BY c.id_product ORDER BY amount DESC LIMIT 10", nativeQuery = true)
    List<Object[]> getTop10Products();

    @Query(value="SELECT doanspringboot.click_cart.id_product, count(*) FROM doanspringboot.click_cart GROUP BY doanspringboot.click_cart.id_product",
            nativeQuery=true)
    List<ClickCart[]> getCountByCountry();

    @Query(value = "SELECT count(c.id_product) FROM click_cart AS c WHERE create_time BETWEEN '04:00:00' AND '09:59:59'", nativeQuery = true)
    Long getCountTimeMorning();


    @Query(value = "SELECT count(c.id_product)  FROM click_cart AS c WHERE create_time BETWEEN '10:00:00' AND '13:59:59'",nativeQuery = true)
    Long getCountTimeNoon();

    @Query(value = "SELECT count(c.id_product)  FROM click_cart AS c WHERE create_time BETWEEN '14:00:00' AND '17:59:59'",nativeQuery = true)
    Long getCountAfternoon();

    @Query(value = "SELECT count(c.id_product)  FROM click_cart AS c WHERE create_time BETWEEN '18:00:00' AND '23:59:59'",nativeQuery = true)
    Long getCountEvening();

    @Query(value = "SELECT count(c.id_product)  FROM click_cart AS c WHERE create_time BETWEEN '00:00:00' AND '03:59:59'",nativeQuery = true)
    Long getCountNight();
}
