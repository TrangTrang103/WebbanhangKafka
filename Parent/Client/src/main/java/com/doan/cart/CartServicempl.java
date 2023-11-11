package com.doan.cart;

import com.doan.mutual.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServicempl implements CartService{

	@Autowired
	CartRepository cartRepository;
	/**
	 *
	 */
	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		cartRepository.deleteById(id);
	}
	@Override
	public List<Cart> GetAllCartByCustomerId(Integer customer_id) {
		// TODO Auto-generated method stub
		return cartRepository.findAllByCustomerId(customer_id);
	}
	@Override
	public void saveCart(Cart cart) {
		// TODO Auto-generated method stub
		cartRepository.save(cart);
	}
}
