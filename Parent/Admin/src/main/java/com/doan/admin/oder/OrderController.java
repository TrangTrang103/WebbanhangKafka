package com.doan.admin.oder;

import com.doan.admin.customer.CustomerRepository;
import com.doan.mutual.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private OrderItemServiceImp orderItemService;


    @Autowired
    HttpSession session;
    @GetMapping("/order")
    public String getOrder(Model model){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> pageOrder = orderService.getAll(pageable);
        model.addAttribute("pageOrder",pageOrder);
        return "order/order";
    }

    @GetMapping("/order/{page}")
    public String getProduct(Model model, @PathVariable int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Order> pageOrder = orderService.getAll(pageable);
        model.addAttribute("pageOrder",pageOrder);
        return "order/order";
    }

    @PostMapping("/order/search")
    public String getProduct(@ModelAttribute("search-input") String search_input, Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> pageOrder = orderService.searchOrder(search_input, pageable);
        model.addAttribute("pageOrder", pageOrder);
        model.addAttribute("search_dashboard", "search_dashboard");
        model.addAttribute("search_input", search_input);
        session.setAttribute("search_input_order", search_input);
        return "order/order";
    }

    @GetMapping("/order/search")
    public String getProducts(Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        String search_input = (String) session.getAttribute("search_input_order");
        Page<Order> pageOrder = orderService.searchOrder(search_input, pageable);
        model.addAttribute("pageOrder", pageOrder);
        model.addAttribute("search_input", search_input);
        model.addAttribute("search_dashboard", "search_dashboard");
        session.setAttribute("search_input_order", search_input);
        return "order/order";
    }

    @GetMapping("/order/search/{page}")
    public String getProductSearch(@PathVariable int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        String search_input = (String) session.getAttribute("search_input_order");
        Page<Order> pageOrder = orderService.searchOrder(search_input, pageable);
        model.addAttribute("pageOrder", pageOrder);
        model.addAttribute("search_input", search_input);
        model.addAttribute("search_dashboard", "search_dashboard");
        session.setAttribute("search_input_order", search_input);
        return "order/order";
    }

    @GetMapping("/getorder/{id}")
    public String getOrder(@PathVariable int id, Model model){
        Order order = orderService.getOrderById(id);
       List<OrderItem> orderItems = orderItemService.getAllByOrderId(order.getId());
        model.addAttribute("order",order);
        model.addAttribute("orderItems",orderItems);
        return "order/order_detail";
    }
}
