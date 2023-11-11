package com.doan.cart;


import com.doan.mutual.entity.Cart;

import java.util.List;

public interface CartService {
	
	void deleteById(Integer id);


	List<Cart> GetAllCartByCustomerId(Integer customer_id);

	void saveCart(Cart cart);
	
}
