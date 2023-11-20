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
}
