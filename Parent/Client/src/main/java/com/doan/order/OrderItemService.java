package com.doan.order;


import com.doan.mutual.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

	List<OrderItem> getAllByOrder_Id(int id);
	public void saveOrder_Item(OrderItem order_Item);
	void deleteById(int id);
}
