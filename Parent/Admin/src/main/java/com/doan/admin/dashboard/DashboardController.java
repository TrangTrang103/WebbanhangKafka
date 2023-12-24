package com.doan.admin.dashboard;

import com.doan.admin.clickViewProduct.ClickViewProductRepository;
import com.doan.admin.clickViewProduct.ClickViewProductServiceImpl;
import com.doan.admin.clickcart.ClickCartRepository;
import com.doan.admin.clickcart.ClickCartServiceImpl;
import com.doan.admin.oder.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private ClickCartServiceImpl clickCartService;

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private ClickCartRepository clickCartRepository;

    @Autowired
    private ClickViewProductRepository clickViewProductRepository;
    @Autowired
    private ClickViewProductServiceImpl viewProductService;
    @GetMapping("/dashboard")
    public String ClickCart(Model model) throws SQLException {
        List<Object[]> clickCarts =  clickCartService.getTop10Products();
        List<Object[]> clickViewProduct = viewProductService.getTop10Products();
        List<Long> clickCartPie = Arrays.asList(clickCartRepository.getCountTimeMorning(), clickCartRepository.getCountTimeNoon(),
                clickCartRepository.getCountAfternoon(), clickCartRepository.getCountEvening(), clickCartRepository.getCountNight());
        List<Long> clickViewPie = Arrays.asList(clickViewProductRepository.getCountTimeMorning(), clickViewProductRepository.getCountTimeNoon(),
                clickViewProductRepository.getCountAfternoon(), clickViewProductRepository.getCountEvening(), clickViewProductRepository.getCountNight());


        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();

        List<Object[]> orderInMonth = orderService.getOrderInMonth(11);
        List<Object[]> orderCountInMonth = orderService.getCountOrderInMonth(11);

        Long countProduct = clickViewProductRepository.getCountProduct(11);
        Long sumProductOrder = clickViewProductRepository.getCountProductOrder(11);
        model.addAttribute("clickCarts", clickCarts);
        model.addAttribute("clickViewProduct", clickViewProduct);
        model.addAttribute("clickCartPie", clickCartPie);
        model.addAttribute("clickViewPie", clickViewPie);
        model.addAttribute("orderInMonth", orderInMonth);
        model.addAttribute("orderCountInMonth",orderCountInMonth);
        model.addAttribute("countProduct", countProduct);
        model.addAttribute("sumProductOrder", sumProductOrder);
        return "dashboard/dashboard";
    }
}
