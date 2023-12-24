package com.doan.admin.clickViewProduct;

import com.doan.admin.clickcart.ClickCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClickViewProductServiceImpl {
    @Autowired
    private ClickViewProductRepository viewProductRepository;

    public List<Object[]> getTop10Products() {
        return viewProductRepository.getTop10Products();
    }

    public  List<Object[]> getOrderInMonth(Integer month){
        return viewProductRepository.getOrderInMonth(month);
    }

    public Long getCountProduct(Integer month){
        return viewProductRepository.getCountProduct(month);
    }

    public Long getCountProductOrder(Integer month){return viewProductRepository.getCountProductOrder(month);}
}
