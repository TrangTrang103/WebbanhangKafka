package com.doan.product;

import com.doan.cart.CartService;
import com.doan.category.CategoryService;
import com.doan.kafka.service.KafkaProducerService;
import com.doan.mutual.entity.Cart;
import com.doan.mutual.entity.Category;
import com.doan.mutual.entity.Product;
import com.doan.mutual.entity.clickDetailList;
import com.doan.security.CustomerUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller

public class ProductController {

	@Autowired
	ProductService productService;

//	@Autowired
//	UserService userService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CartService cartService;

	@Autowired
	HttpSession session;

	private final KafkaProducerService kafkaProducerService;
//	@Autowired
//	CookieService cookie;

	@GetMapping(value = {"/","/home"})
	public String listStudents(Model model, @AuthenticationPrincipal CustomerUserDetails loggerUser) {
		List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
//		Cookie user_name = cookie.read("user_name");
//		Cookie remember = cookie.read("remember");
		String error_momo = (String) session.getAttribute("error_momo");
		String NoSignIn = (String) session.getAttribute("NoSignIn");
		session.setAttribute("NoSignIn", null);
		session.setAttribute("error_momo", null);
		String a = (String) session.getAttribute("NoSignIn");
		System.out.println(a);
		System.out.println(NoSignIn);
//		User acc = (User) session.getAttribute("acc");
//		if (remember != null) {
//			acc = userService.findByIdAndRole(user_name.getValue(), "user");
//			session.setAttribute("acc", acc);
//			List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//			session.setAttribute("countCart", listCart.size());
//		}
		if(loggerUser!=null) {
			List<Cart> listCart = cartService.GetAllCartByCustomerId(loggerUser.getCustomer().getId());
			session.setAttribute("countCart", listCart.size());
			}
		if (loggerUser==null)
			session.setAttribute("countCart", "0");
		model.addAttribute("error_momo", error_momo);
		model.addAttribute("NoSignIn", NoSignIn);
		
		List<Product> Top12ProductBestSellers = productService.findTop12ProductBestSellers();
		List<Product> Top12ProductNewArrivals = productService.findTop12ProductNewArrivals();
		model.addAttribute("Top12ProductBestSellers", Top12ProductBestSellers);
		model.addAttribute("Top12ProductNewArrivals", Top12ProductNewArrivals);

		return "index";
	}
	@GetMapping("/shop")
	public String shop(Model model) throws Exception {
		List<Product> lp = productService.getAllProduct();
		int TotalPro = lp.size();
		model.addAttribute("TotalPro",TotalPro);
		Pageable pageable = PageRequest.of(0, 12);
		Page<Product> page = productRepository.findAll(pageable);
		List<Category> listCategory = categoryService.findAll();
		String search_input = (String) session.getAttribute("search_input");
		model.addAttribute("listProduct", page);
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("search_input", search_input);
		return "shop/shop";
	}
	@GetMapping("/shop/{id}")
	public String shopPage(Model model, @PathVariable int id) {
		List<Product> lp = productService.getAllProduct();
		int TotalPro = lp.size();
		model.addAttribute("TotalPro",TotalPro);
		Pageable pageable = PageRequest.of(id, 12);
		Page<Product> page = productRepository.findAll(pageable);
		model.addAttribute("listProduct", page);
		List<Category> listCategory = categoryService.findAll();
		String search_input = (String) session.getAttribute("search_input");
//		User user = (User) session.getAttribute("acc");
//		if (user != null) {
//			model.addAttribute("user_Name", user.getUser_Name());
//		}
		if (listCategory != null)
			model.addAttribute("listCategory", listCategory);
		else
			model.addAttribute("listCategory", null);
		model.addAttribute("search_input", search_input);
		return "shop/shop";
	}

	@GetMapping("/productDetail/{id}")
	public String ProductDetailId(@PathVariable int id, Model model,HttpServletRequest request) {
//		List<String> msgs = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
//		if (msgs == null) {
//			msgs = new ArrayList<>();
//			request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
//		}
//		msgs.add(String.valueOf(id));
//		request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
		Product product = productService.getProductById(id);
		kafkaProducerService.sendClick(new clickDetailList(String.valueOf(id),"productDetail"));
			if (product != null) {
				List<Product> relatedProduct = productService.findTop4ProductByCategory_id(product.getCategory().getId());
				model.addAttribute("relatedProduct", relatedProduct);
				model.addAttribute(product);
				return "shop/shop-details";
			} else {
				return "redirect:/home";
			}

		}


//	@GetMapping("/productDetail")
//	public String ProductDetail(Model model) {
//		Product product = (Product) session.getAttribute("product");
//
//	}

	@PostMapping("/search")
	public String Search(@ModelAttribute("search-input") String search_input, Model model) {
		session.setAttribute("search_input", search_input);
		return "redirect:/search/0";
	}

	@GetMapping("/search/{id}")
	public String SearchPage(@PathVariable int id, Model model)  {
		List<Category> listCategory = categoryService.findAll();
		String search_input = (String) session.getAttribute("search_input");
		if (search_input != null) {
			Pageable pageable = PageRequest.of(id, 12);
			System.out.println(search_input);
			Page<Product> listProduct = productRepository.findByProduct_NameContaining(search_input, pageable);
			List<Product> listProductAll = productRepository.findByProduct_NameContaining(search_input);
			StringBuffer search = new StringBuffer();
			if (listProductAll.size() != 0){
				search.append("[");
				listProductAll.forEach(i ->
				{
					search.append(i.getId()+",");
				});
				search.deleteCharAt(search.lastIndexOf(","));
				search.append("]");
				kafkaProducerService.sendClick(new clickDetailList(String.valueOf(search),"searchdetail"));
			}

			int TotalPro = listProductAll.size();
			model.addAttribute("TotalPro",TotalPro);
			model.addAttribute("search_input", search_input);
			model.addAttribute("listProduct", listProduct);
			model.addAttribute("listCategory", listCategory);
			model.addAttribute("pageSearch", "pageSearch");
			model.addAttribute("noPageable", "search");
			for(Product y :listProduct) {
				System.out.println(y);
			}
			return "shop/shop";
		} else {
			model.addAttribute("TotalPro",0);
			model.addAttribute("noPageable", "search");
			model.addAttribute("listCategory", listCategory);
			model.addAttribute("search_input", null);
			model.addAttribute("listProduct", null);
			return "shop/shop";
		}
	}

	public ProductController(KafkaProducerService kafkaProducerService) {
		this.kafkaProducerService = kafkaProducerService;
	}
}
