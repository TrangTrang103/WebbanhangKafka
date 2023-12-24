package com.doan.admin.clickcart;

import com.doan.mutual.entity.ClickCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClickCartServiceImpl {

    @Autowired
    private ClickCartRepository clickCartRepository;

    public List<Object[]> getTop10Products() {
        return clickCartRepository.getTop10Products();
    }

    public List<ClickCart[]> getCountByCountry() {
        return clickCartRepository.getCountByCountry();
    }

}
