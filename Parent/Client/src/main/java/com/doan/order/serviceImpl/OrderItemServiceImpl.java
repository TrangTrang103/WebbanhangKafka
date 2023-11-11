package com.doan.order.serviceImpl;


import com.doan.mutual.entity.OrderItem;
import com.doan.order.OrderItemRepository;
import com.doan.order.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	OrderItemRepository order_ItemRepository;
	@Override
	public void saveOrder_Item(OrderItem order_Item) {
		// TODO Auto-generated method stub
		order_ItemRepository.save(order_Item);
	}
	@Override
	public List<OrderItem> getAllByOrder_Id(int id) {
		// TODO Auto-generated method stub
		return order_ItemRepository.findAllByOrder_id(id);
	}
	@Override
	public void deleteById(int id) {
		order_ItemRepository.deleteById(id);
	}
}
