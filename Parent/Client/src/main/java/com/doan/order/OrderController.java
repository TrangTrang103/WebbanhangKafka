package com.doan.order;


import com.doan.cart.CartService;
import com.doan.mutual.entity.*;
import com.doan.product.ProductService;
import com.doan.security.CustomerUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

@Controller
public class OrderController {

	@Autowired
	CartService cartService;


	@Autowired
	ProductService productService;

	@Autowired
	OrderItemService orderItemService;

	@Autowired
	OrderService orderService;

	@Autowired
	HttpSession session;

	@GetMapping("checkout")
	public String CheckOutView(Model model, @AuthenticationPrincipal CustomerUserDetails loggerUser) {
//		User user = (User) session.getAttribute("acc");
		if (loggerUser == null) {
			session.setAttribute("AddToCartErr", "Vui lòng đăng nhập trước khi thực hiện thao tác!");
			return "redirect:/home";
		} else {
			List<Cart> Cart = cartService.GetAllCartByCustomerId(loggerUser.getCustomer().getId());
			if (!Cart.isEmpty()) {
				String a = session.getAttribute("Total").toString();
				Double Total = Double.parseDouble(a);
				System.out.println(Total);
				model.addAttribute("Total", a);
				@SuppressWarnings("unchecked")
				List<Cart> listCart = (List<Cart>) session.getAttribute("listCart");
				model.addAttribute("listCart", listCart);
				model.addAttribute("loggerUser", loggerUser);
				return "order/checkout";
			}
			else {
				session.setAttribute("CartIsEmpty", "CartIsEmpty");
				return "redirect:/cart";
			}
		}
	}

	@PostMapping("checkout")
	public String CheckOut( @ModelAttribute("note") String note,
			@RequestParam(value = "payOndelivery", defaultValue = "false") boolean payOndelivery,
			@RequestParam(value = "payWithMomo", defaultValue = "false") boolean payWithMomo, Model model,
			HttpServletResponse resp, @AuthenticationPrincipal CustomerUserDetails loggerUser) throws Exception {

		long millis = System.currentTimeMillis();
		Date booking_date = new Date(millis);
		@SuppressWarnings("unchecked")
		List<Cart> listCart = (List<Cart>) session.getAttribute("listCart");
//		Customer customer = (Customer) session.getAttribute("acc");
		String a = session.getAttribute("Total").toString();
		Double Total = Double.parseDouble(a);
		String status = "Pending";
		String payment_method = null;
		if (payOndelivery == true) {
			payment_method = "Payment on delivery";
		} else {
			payment_method = "Payment with momo";
		}
		Order newOrder = new Order();
		newOrder.setTotal(Total);
		newOrder.setBookingDate(booking_date);
		newOrder.setNote(note);
		newOrder.setPaymentMethod(payment_method);
		newOrder.setStatus(status);
		newOrder.setCustomer(loggerUser.getCustomer());
		if (payment_method == "Payment on delivery") {
//			session.setAttribute("newOrder", newOrder);
//			ObjectMapper mapper = new ObjectMapper();
//			int code = (int) Math.floor(((Math.random() * 89999999) + 10000000));
//			String orderId = Integer.toString(code);
//			MomoModel jsonRequest = new MomoModel();
//			jsonRequest.setPartnerCode(Constant.IDMOMO);
//			jsonRequest.setOrderId(orderId);
//			jsonRequest.setStoreId(orderId);
//			jsonRequest.setRedirectUrl(Constant.redirectUrl);
//			jsonRequest.setIpnUrl(Constant.ipnUrl);
//			jsonRequest.setAmount(String.valueOf(Total));
//			jsonRequest.setOrderInfo("Thanh toán Male Fashion.");
//			jsonRequest.setRequestId(orderId);
//			jsonRequest.setOrderType(Constant.orderType);
//			jsonRequest.setRequestType(Constant.requestType);
//			jsonRequest.setTransId("1");
//			jsonRequest.setResultCode("200");
//			jsonRequest.setMessage("");
//			jsonRequest.setPayType(Constant.payType);
//			jsonRequest.setResponseTime("300000");
//			jsonRequest.setExtraData("");
//			String decode = "accessKey=" + Constant.accessKey + "&amount=" + jsonRequest.amount + "&extraData="
//					+ jsonRequest.extraData + "&ipnUrl=" + Constant.ipnUrl + "&orderId=" + orderId + "&orderInfo="
//					+ jsonRequest.orderInfo + "&partnerCode=" + jsonRequest.getPartnerCode() + "&redirectUrl="
//					+ Constant.redirectUrl + "&requestId=" + jsonRequest.getRequestId() + "&requestType="
//					+ Constant.requestType;
//			String signature = Decode.encode(Constant.serectkey, decode);
//			jsonRequest.setSignature(signature);
//			String json = mapper.writeValueAsString(jsonRequest);
//			HttpClient client = HttpClient.newHttpClient();
//			ResultMoMo res = new ResultMoMo();
//
//			try {
//				HttpRequest request = HttpRequest.newBuilder().uri(new URI(Constant.Url))
//						.POST(HttpRequest.BodyPublishers.ofString(json)).headers("Content-Type", "application/json")
//						.build();
//				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//				res = mapper.readValue(response.body(), ResultMoMo.class);
//			} catch (InterruptedException | URISyntaxException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if (res == null) {
//				session.setAttribute("error_momo", "Thanh toán thất bại");
//				return "redirect:/home";
//			} else {
//					return "redirect:/shop";
//				resp.sendRedirect(res.payUrl);
//				return "redirect:" + res.payUrl;
//			}
//		} else {
			orderService.saveOrder(newOrder);
			List<Order> listOrder = orderService.getAllOrderByCustomerId(loggerUser.getCustomer().getId());
			newOrder = listOrder.get(listOrder.size()-1);
			newOrder.getId();
			for (Cart y : listCart) {
				Product product = y.getProduct();
				product.setQuantity(product.getQuantity() - y.getCount());
				product.setSold(product.getSold() + y.getCount());
				productService.saveProduct(product);
				OrderItem newOrder_Item = new OrderItem();
				newOrder_Item.setCount(y.getCount());
				newOrder_Item.setOrder(newOrder);
				newOrder_Item.setProduct(y.getProduct());
				orderItemService.saveOrder_Item(newOrder_Item);
				cartService.deleteById(y.getId());
			}
			listOrder = orderService.getAllOrderByCustomerId(loggerUser.getCustomer().getId());
			newOrder = listOrder.get(listOrder.size() - 1);
			Shipping shipping = new Shipping();
			session.setAttribute("order", newOrder);
			Order order = newOrder;
			Integer i = order.getId();
			return "redirect:/invoice";
		}
		return "order/checkout";
	}

//


	@GetMapping("invoice")
	public String Invoice(Model model,@AuthenticationPrincipal CustomerUserDetails loggerUser) {
		Customer userDetails = loggerUser.getCustomer();
		Order order = (Order) session.getAttribute("order");
		String invoiceView = (String) session.getAttribute("invoiceView");
		session.setAttribute("invoiceView", null);
		List<OrderItem> listOrder_Item = orderItemService.getAllByOrder_Id(order.getId());
		model.addAttribute("invoiceView", invoiceView);
		model.addAttribute("listOrder_Item", listOrder_Item);
		model.addAttribute("order", order);
		model.addAttribute("userDetails",userDetails);
		return "invoice";
	}

	@GetMapping("/invoice/{id}")
	public String InvoiceView(@PathVariable int id, Model model, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		Order order = orderService.findById(id);
		session.setAttribute("order", order);
		session.setAttribute("invoiceView", "view");
		return "redirect:/invoice";
	}

	@GetMapping("/myhistory")
	public String Myhistory(Model model, HttpServletRequest request,@AuthenticationPrincipal CustomerUserDetails loggerUser) {
		String referer = request.getHeader("Referer");
		if (loggerUser == null) {
			return "redirect:" + referer;
		} else {
			List<Order> listOrder = orderService.getAllOrderByCustomerId(loggerUser.getCustomer().getId());
			Collections.reverse(listOrder);
			model.addAttribute("listOrder", listOrder);
			System.out.println(listOrder);
			for (Order y : listOrder) {
				System.out.println(y.getOrderItem());
			}
		}
		return "myhistory";
	}
}
