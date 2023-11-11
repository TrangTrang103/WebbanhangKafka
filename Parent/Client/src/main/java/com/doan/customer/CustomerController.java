package com.doan.customer;

import com.doan.mutual.entity.City;
import com.doan.mutual.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CustomerController {


    @Autowired
    private CustomerService customerService;
    @Autowired
    HttpSession session;
    @GetMapping("/register")
    public String registerForm(Model model){
        List<City> listCity = customerService.listAllCity();
        model.addAttribute("listCity", listCity);
        model.addAttribute("pageTitle", "Customer Registration");
        model.addAttribute("customer", new Customer());

        return "register/register";

    }

    @PostMapping("/create_customer")
    public String createCustomer(@ModelAttribute("customer") Customer customer){
        customerService.saveRegister(customer);
        session.setAttribute("register","createRegisterSuccess");
        return "redirect:/login";
    }
}
