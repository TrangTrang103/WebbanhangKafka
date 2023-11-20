package com.doan.order.serviceImpl;


import com.doan.mutual.entity.Cart;
import com.doan.mutual.entity.Order;
import com.doan.order.OrderRepository;
import com.doan.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	@Override
	public void saveOrder(Order order) {
		// TODO Auto-generated method stub
		orderRepository.save(order);
	}
	@Override
	public List<Order> getAllOrderByCustomerId(Integer id) {
		// TODO Auto-generated method stub
		return orderRepository.findAllByCustomerId(id);
	}
	@Override
	public Order findById(int id) {
		return orderRepository.findById(id);
	}
	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	@Override
	public List<Order> findTop5RecentOrder() {
		return orderRepository.findTop5RecentOrder();
	}
	@Override
	public List<String> findTop5RecentCustomer() {
		return orderRepository.findTop5RecentCustomer();
	}
	@Override
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}
	@Override
	public void deleteById(int id) {
		orderRepository.deleteById(id);
	}
	@Override
	public List<Order> findAllByPayment_Method(String payment_Method) {
		return orderRepository.findAllByPayment_Method(payment_Method);
	}
	@Override
	public List<Order> findTop5OrderByPaymentMethod(String payment_method) {
		return orderRepository.findTop5OrderByPaymentMethod(payment_method);
	}
	
	
}
