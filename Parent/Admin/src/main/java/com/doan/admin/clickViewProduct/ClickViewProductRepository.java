package com.doan.admin.clickViewProduct;

import com.doan.mutual.entity.ClickProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ClickViewProductRepository extends JpaRepository<ClickProduct,Integer> {
    @Query(value = "SELECT c.id_product, count(c.id_product) as amount  FROM click_product AS c GROUP BY c.id_product ORDER BY amount DESC LIMIT 10", nativeQuery = true)
    List<Object[]> getTop10Products();

    @Query(value = "SELECT count(c.id_product) FROM click_product AS c WHERE create_time BETWEEN '04:00:00' AND '09:59:59'", nativeQuery = true)
    Long getCountTimeMorning();


    @Query(value = "SELECT count(c.id_product)  FROM click_product AS c WHERE create_time BETWEEN '10:00:00' AND '13:59:59'",nativeQuery = true)
    Long getCountTimeNoon();

    @Query(value = "SELECT count(c.id_product)  FROM click_product AS c WHERE create_time BETWEEN '14:00:00' AND '17:59:59'",nativeQuery = true)
    Long getCountAfternoon();

    @Query(value = "SELECT count(c.id_product)  FROM click_product AS c WHERE create_time BETWEEN '18:00:00' AND '23:59:59'",nativeQuery = true)
    Long getCountEvening();

    @Query(value = "SELECT count(c.id_product)  FROM click_product AS c WHERE create_time BETWEEN '00:00:00' AND '03:59:59'",nativeQuery = true)
    Long getCountNight();

//    @Query(value = "SELECT o.booking_date, count(o.id)  as amount  from order o group by o.booking_date having month(o.booking_date) = ?1", nativeQuery = true)
//    List<Object[]> getOrderInMonth(Integer month);

@Query(value = "SELECT o.booking_date, COUNT(o.id) AS amount FROM order o GROUP BY o.booking_date HAVING MONTH(o.booking_date) = :month", nativeQuery = true)

       List<Object[]> getOrderInMonth(@Param("month") int month);

@Query(value = "SELECT count(p.id)  FROM `product` p WHERE month(p.created_time) = ?1", nativeQuery = true)
       Long getCountProduct(int month);

    @Query(value = "SELECT sum(i.count)  as amount  from `order` o, `order_item` i where o.id = i.order_id and month(o.booking_date) = ?1", nativeQuery = true)
    Long getCountProductOrder(int month);
}


