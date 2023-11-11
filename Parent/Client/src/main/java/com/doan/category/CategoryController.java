package com.doan.category;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.doan.mutual.entity.Category;
import com.doan.mutual.entity.Product;
import com.doan.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    HttpSession session;

    @GetMapping("/category/{id}")
    public String category(@PathVariable int id, Model model) {
        Pageable pageable = PageRequest.of(0, 12);
        Page<Product> page = productRepository.findAllByCategory_id(id, pageable);
        Category category = categoryService.getCategoryById(id);
        List<Category> listCategory = categoryService.findAll();
        List<Product> listProduct = null;
        if (category != null) {
            listProduct = category.getProduct();
        }
        int TotalPro = listProduct.size();
        model.addAttribute("TotalPro",TotalPro);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("search_input", null);
        model.addAttribute("listProduct", page);
        model.addAttribute("idCate", id);
        model.addAttribute("noPageable", "category");
        return "shop/shop";
    }

    @GetMapping("/category/{id}/{p}")
    public String categoryPage(@PathVariable int id,@PathVariable int p, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        Pageable pageable = PageRequest.of(p, 12);
        Page<Product> page = productRepository.findAllByCategory_id(id, pageable);
        Category category = categoryService.getCategoryById(id);
        List<Category> listCategory = categoryService.findAll();
        List<Product> listProduct = null;
        if (category != null) {
            listProduct = category.getProduct();
        }
        int TotalPro = listProduct.size();
        model.addAttribute("TotalPro",TotalPro);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("search_input", null);
        model.addAttribute("listProduct", page);
        model.addAttribute("referer", referer);
        model.addAttribute("idCate", id);
//		model.addAttribute("noPageable", "noPageable");
        model.addAttribute("noPageable", "category");
        return "shop/shop";
    }
}
