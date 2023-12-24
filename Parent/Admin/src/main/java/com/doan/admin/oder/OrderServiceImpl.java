package com.doan.admin.oder;

import com.doan.mutual.entity.Cart;
import com.doan.mutual.entity.Order;
import com.doan.mutual.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl {

    @Autowired
    private OrderRepository orderRepository;
   public Page<Order> getAll(Pageable pageable){
      return  orderRepository.findAll(pageable);
   }
    public Page<Order> searchOrder(String inputSearch, Pageable pageable){
        return orderRepository.findByOrder_NameAndId(inputSearch,pageable);
    }

    public Order getOrderById(Integer id){
       return orderRepository.findOrderId(id);
    }

    public List<Object[]> getOrderInMonth(Integer month){
        return orderRepository.getOrderInMonth(month);
    }

    public List<Object[]> getCountOrderInMonth(Integer month){
       return orderRepository.getCountOrderInMonth(month);
    }
}
