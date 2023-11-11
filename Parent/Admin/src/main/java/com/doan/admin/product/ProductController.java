package com.doan.admin.product;

import com.doan.admin.category.CategoryServiceImpl;
import com.doan.admin.security.UserDetailsImpl;
import com.doan.mutual.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ProductImgRepository productImgRepository;
    @Autowired
    HttpSession session;

    @GetMapping("/product")
    public String getProduct(Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> pageProduct = productService.getAllProduct(pageable);
//        List<ProductImage> listImg = productImgRepository.findByProduct();
//        String productSuccess = (String) session.getAttribute("addProductSuccess");
        List<Category> listCategories = categoryService.getAllCategory();
        List<Product> products = productService.getAllProduct();
        products.get(0).getProductImage().get(0);
        model.addAttribute("pageProduct", pageProduct);
        model.addAttribute("listCategories", listCategories);
//        model.addAttribute("productSuccess",productSuccess);
        String addProduct = (String) session.getAttribute("addProduct");
        String editProduct = (String) session.getAttribute("editProduct");
        model.addAttribute("addProduct", addProduct);
        model.addAttribute("editProduct", editProduct);
        session.setAttribute("addProduct", null);
        session.setAttribute("editProduct", null);

        return "product/product";
    }

    @GetMapping("/product/{page}")
    public String getProduct(Model model, @PathVariable int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Product> pageProduct = productService.getAllProduct(pageable);
        List<Category> listCategories = categoryService.getAllCategory();
        model.addAttribute("pageProduct", pageProduct);
        model.addAttribute("listCategories", listCategories);
        return "product/product";
    }

    @PostMapping("/product/search")
    public String getProduct(@ModelAttribute("search-input") String search_input, Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> pageProduct = productService.searchProduct(search_input, pageable);
        model.addAttribute("pageProduct", pageProduct);
        model.addAttribute("search_dashboard", "search_dashboard");
        model.addAttribute("search_input", search_input);
        session.setAttribute("search_input_product", search_input);
        return "product/product";
    }

    @GetMapping("/product/search")
    public String getProducts(Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        String search_input = (String) session.getAttribute("search_input_product");
        Page<Product> pageProduct = productService.searchProduct(search_input, pageable);
        model.addAttribute("pageProduct", pageProduct);
        model.addAttribute("search_input", search_input);
        model.addAttribute("search_dashboard", "search_dashboard");
        session.setAttribute("search_input_product", search_input);
        return "product/product";
    }

    @GetMapping("/product/search/{page}")
    public String getProductSearch(@PathVariable int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        String search_input = (String) session.getAttribute("search_input_product");
        Page<Product> pageProduct = productService.searchProduct(search_input, pageable);
        model.addAttribute("pageProduct", pageProduct);
        model.addAttribute("search_input", search_input);
        model.addAttribute("search_dashboard", "search_dashboard");
        session.setAttribute("search_input_product", search_input);
        return "product/product";
    }

    @GetMapping("/product/add")
    public String addProduct(Model model) {
        String addProduct = (String) session.getAttribute("addProduct");
        model.addAttribute("addProduct", null);
        List<Category> categories = categoryService.getAllCategory();
        List<Size> sizes = productService.getProductSize();
        model.addAttribute("categories", categories);
        model.addAttribute("sizes", sizes);
        model.addAttribute("product", new Product());
        model.getAttribute(addProduct);
        return "product/product_add";
    }

    @PostMapping("/product/addp")
    public String addProduct(@ModelAttribute("product_name") String product_name, @ModelAttribute("code") String code,
                             @ModelAttribute("product") Product productsize, @ModelAttribute("category") Integer category,
                             @ModelAttribute("quantity") Long quantity, @ModelAttribute("import_rice") String import_price,
                             @ModelAttribute("sale_price") String sale_price, @ModelAttribute("description") String description,
                             @ModelAttribute("listImage") MultipartFile[] listImage, @AuthenticationPrincipal UserDetailsImpl loggerUser) {
        String email = loggerUser.getUsername();
        productService.saveProduct(product_name, code, productsize, category, quantity, import_price, sale_price, description, listImage, email);
        session.setAttribute("addProduct", "addProductSuccess");
        return "redirect:/product";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/product";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        List<Category> listCategories = categoryService.getAllCategory();
        Product product = productService.getProductId(id);
        List<Size> sizes = productService.getProductSize();
        model.addAttribute("product", product);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("sizes", sizes);
        return "product/product_edit";
    }

    @PostMapping("/product/editp")
    public String editProduct(@ModelAttribute("product_id") int productId, @ModelAttribute("product_name") String product_name, @ModelAttribute("code") String code,
                              @ModelAttribute("product") Product productsize, @ModelAttribute("category") int category,
                              @ModelAttribute("quantity") Long quantity, @ModelAttribute("import_rice") String import_price,
                              @ModelAttribute("sale_price") String sale_price, @ModelAttribute("description") String description,
                              @ModelAttribute("listImage") MultipartFile[] listImage, @AuthenticationPrincipal UserDetailsImpl loggerUser) {
        String email = loggerUser.getUsername();
        try {
            productService.saveEdit(productId,product_name, code, productsize, category, quantity, import_price, sale_price, description, listImage, email);

        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute("editProduct", "addProductSuccess");
        return "redirect:/product";
    }

    @GetMapping("/product/delete-image/{id}")
    public String DeleteImage(@PathVariable int id, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        productService.deleteProductImage(id);
        return "redirect:" + referer;

    }
}
