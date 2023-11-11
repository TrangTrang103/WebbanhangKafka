package com.doan.admin.oder;

import com.doan.mutual.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImp {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public void saveOrderItem(OrderItem order_Item) {
        // TODO Auto-generated method stub
        orderItemRepository.save(order_Item);
    }

    public List<OrderItem> getAllByOrderId(int id) {
        // TODO Auto-generated method stub
        return orderItemRepository.findAllByOrder_id(id);
    }
    public void deleteById(int id) {
        orderItemRepository.deleteById(id);
    }
}
