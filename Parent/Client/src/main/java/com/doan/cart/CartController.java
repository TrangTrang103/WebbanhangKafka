package com.doan.cart;


import com.doan.kafka.service.KafkaProducerService;
import com.doan.mutual.entity.Cart;
import com.doan.mutual.entity.Customer;
import com.doan.mutual.entity.Product;
import com.doan.mutual.entity.clickDetailList;
import com.doan.product.ProductService;
import com.doan.security.CustomerUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;



@Controller
public class CartController {

	@Autowired
	CartService cartService;

	@Autowired
	ProductService productService;

	@Autowired
	HttpSession session;
	private final KafkaProducerService kafkaProducerService;
	public CartController(KafkaProducerService kafkaProducerService) {
		this.kafkaProducerService = kafkaProducerService;
	}
	@GetMapping("/cart")
	public String CartView(Model model,@AuthenticationPrincipal CustomerUserDetails loggerUser)  {
		   if (loggerUser == null){
			   session.setAttribute("NoSignIn", "Vui lòng đăng nhập trước khi thực hiện thao tác");
			   return "redirect:/home";
		   }else {
			   Integer customerId = loggerUser.getCustomer().getId();
			   List<Cart> listCart = cartService.GetAllCartByCustomerId(customerId);
			   Double Total = (double) 0;
			   for (Cart y : listCart) {
				   Double price = y.getProduct().getImportPrice();
				   Total = Total + y.getCount() * price;
			   }
			   List<Integer> priceCart = new ArrayList<>();
			   if (listCart != null) {
				   model.addAttribute("Total", Total);
				   model.addAttribute("listCart", listCart);
				   session.setAttribute("listCart", listCart);
				   session.setAttribute("Total", Total);
			   }
			   return "cart";
		   }


	}

	@GetMapping("/deleteCart/{id}")
	public String GetDeleteCart(@PathVariable int id, Model model, HttpServletRequest request,@AuthenticationPrincipal CustomerUserDetails loggerUser)  {
		String referer = request.getHeader("Referer");
		Integer customerId = loggerUser.getCustomer().getId();
			System.out.println(id);
			cartService.deleteById(id);
			session.setAttribute("CartDelete", id);
			List<Cart> listCart = cartService.GetAllCartByCustomerId(customerId);
			session.setAttribute("countCart", listCart.size());
			return "redirect:/cart";

	}

	@PostMapping("/updateCart")
	public String UpdateCart(HttpServletRequest request, Model model) {
		@SuppressWarnings("unchecked")
		List<Cart> listCart = (List<Cart>) session.getAttribute("listCart");
		int i = 0;
		for (Cart o : listCart) {
//			System.out.println("count"+i);
//			String a=(String) model.getAttribute("count" + i);
			String a = request.getParameter("count" + i);
			int count = Integer.parseInt(a);
			System.out.println(count);
			o.setCount(count);
			i++;
		}
		for (Cart o : listCart) {
			cartService.saveCart(o);
		}
		return "redirect:/cart";
	}

	@GetMapping("/addToCart/{id}")
	public String AddToCart(@PathVariable int id, Model model, HttpServletRequest request, @AuthenticationPrincipal CustomerUserDetails loggerUser)  {
		String referer = request.getHeader("Referer");

			if (loggerUser == null){
				session.setAttribute("AddToCartErr", "Vui lòng đăng nhập trước khi thực hiện thao tác");
				return "redirect:" + referer;
			}else {
				kafkaProducerService.sendClick(new clickDetailList(String.valueOf(id),"cartDetail"));
				Integer customerId = loggerUser.getCustomer().getId();
				Customer customer = loggerUser.getCustomer();
				List<Cart> listCart = cartService.GetAllCartByCustomerId(customerId);
				Product product = productService.getProductById(id);
				int cart = 0;
				for (Cart y : listCart) {
					if (y.getProduct().getId() == id) {
						cart++;
					}
				}
				if (cart > 0) {
					for (Cart y : listCart) {
						if (y.getProduct().getId() == id) {
							y.setCount(y.getCount() + 1);
							cartService.saveCart(y);
						}
					}
				} else {
					Cart newCart = new Cart();
					newCart.setCount(1);
					newCart.setSize("S");
					newCart.setProduct(product);
					newCart.setCustomer(customer);
					cartService.saveCart(newCart);
				}
				listCart = cartService.GetAllCartByCustomerId(customerId);
				session.setAttribute("countCart", listCart.size());

			return "redirect:" + referer;
			}

	}

//	@PostMapping("/addToCart")
//	public String AddToCartPost(@ModelAttribute("product_id") int product_id, @ModelAttribute("count") String a,
//			Model model, HttpServletRequest request) throws Exception {
//		int count = Integer.parseInt(a);
//		String referer = request.getHeader("Referer");
//		User user = (User) session.getAttribute("acc");
//		if (user == null) {
//			session.setAttribute("AddToCartErr", "Vui lòng đăng nhập trước khi thực hiện thao tác");
//			return "redirect:" + referer;
//		} else {
//			List<Cart> listCart = cartService.GetAllCartByUser_id(user.getId());
//			Product product = productService.getProductById(product_id);
//			int cart = 0;
//			for (Cart y : listCart) {
//				if (y.getProduct().getId() == product_id) {
//					y.setCount(y.getCount() + count);
//					cartService.saveCart(y);
//					cart++;
//				}
//			}
//			if (cart == 0) {
//				Cart newCart = new Cart();
//				newCart.setCount(count);
//				newCart.setProduct(product);
//				newCart.setUser(user);
//				cartService.saveCart(newCart);
//			}
//			listCart = cartService.GetAllCartByUser_id(user.getId());
//			session.setAttribute("countCart", listCart.size());
//			return "redirect:" + referer;
//		}
//
//	}

}