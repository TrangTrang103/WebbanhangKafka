package com.doan.admin.oder;

import com.doan.mutual.entity.Order;
import com.doan.mutual.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Page<Order> findAll(Pageable pageable);
    List<Order> findAllByCustomerId(Integer customerId);

    @Query(value = "select * from doanspringboot.order where doanspringboot.order.customer_id like %?1% or doanspringboot.order.id like %?1%", nativeQuery = true)
    Page<Order> findByOrder_NameAndId(String inputSearch, Pageable pageable);

    @Query(value = "select * from doanspringboot.order where doanspringboot.order.id like %?1%", nativeQuery = true)
    Order findOrderId(Integer id);

    @Query(value = "SELECT o.booking_date, count(o.id)  as amount  from `order` o group by o.booking_date having month(o.booking_date) = ?1", nativeQuery = true)
    List<Object[]> getOrderInMonth(int month);

    @Query(value = "SELECT o.booking_date, sum(i.count)  as amount  from `order` o, `order_item` i where o.id = i.order_id group by o.booking_date having month(o.booking_date) = ?1", nativeQuery = true)
    List<Object[]> getCountOrderInMonth(int month);
}
