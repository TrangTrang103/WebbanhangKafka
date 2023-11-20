package com.doan.admin.customer;

import com.doan.mutual.entity.Cart;
import com.doan.mutual.entity.Customer;
import com.doan.mutual.entity.Product;
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
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    HttpSession session;
    @GetMapping("/customer")
    public String getCustomer(Model model){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> pageCustomer = customerService.findAllCustomerPage(pageable);
        model.addAttribute("pageCustomer",pageCustomer);
        String deleteCustomer = (String) session.getAttribute("deleteCustomer");
        model.addAttribute("deleteCustomer",deleteCustomer);
        session.setAttribute("deleteCustomer", null);
        return "customer/customer";
    }

    @GetMapping("/customer/{page}")
    public String getCustomer(Model model, @PathVariable int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Customer> pageCustomer = customerService.findAllCustomerPage(pageable);
        model.addAttribute("pageCustomer",pageCustomer);
        return "customer/customer";
    }

    @PostMapping("/customer/search")
    public String getCustomer(@ModelAttribute("search-input") String search_input, Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> pageCustomer = customerService.searchCustomer(search_input,pageable);
        model.addAttribute("pageCustomer", pageCustomer);
        model.addAttribute("search_dashboard", "search_dashboard");
        model.addAttribute("search_input", search_input);
        session.setAttribute("search_input_customer", search_input);
        return "customer/customer";
    }

    @GetMapping("/customer/search")
    public String getCustomers(Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        String search_input = (String) session.getAttribute("search_input_customer");
        Page<Customer> pageCustomer = customerService.searchCustomer(search_input,pageable);
        model.addAttribute("pageCustomer", pageCustomer);
        model.addAttribute("search_input", search_input);
        model.addAttribute("search_dashboard", "search_dashboard");
        session.setAttribute("search_input_customer", search_input);
        return "customer/customer";
    }

    @GetMapping("/customer/search/{page}")
    public String getCustomerSearch(@PathVariable int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        String search_input = (String) session.getAttribute("search_input_customer");
        Page<Customer> pageCustomer = customerService.searchCustomer(search_input,pageable);
        model.addAttribute("pageCustomer", pageCustomer);
        model.addAttribute("search_input", search_input);
        model.addAttribute("search_dashboard", "search_dashboard");
        session.setAttribute("search_input_customer", search_input);
        return "customer/customer";
    }

    @GetMapping("/customer/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        session.setAttribute("deleteCustomer", "deleteCustomerSuccess");
        customerService.deleteById(id);
        return "redirect:/customer";
    }
}
