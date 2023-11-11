package com.doan.admin.category;

import com.doan.admin.security.UserDetailsImpl;
import com.doan.mutual.entity.Category;
import com.doan.mutual.entity.Product;
import com.doan.mutual.entity.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    HttpSession session;

    @GetMapping("/category")
    private String getCategory(Model model){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> pageCategory = categoryService.getAll(pageable);
        model.addAttribute("pageCategory", pageCategory);
        String addCategory = (String) session.getAttribute("addCategory");
        String editCategory = (String) session.getAttribute("editCategory");
        model.addAttribute("addCategory", addCategory);
        model.addAttribute("editCategory", editCategory);
        session.setAttribute("addCategory", null);
        session.setAttribute("editCategory", null);
        return "category/category";
    }

    @GetMapping("/category/{page}")
    public String getCategory(Model model, @PathVariable int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Category> pageCategory = categoryService.getAll(pageable);
        model.addAttribute("pageCategory", pageCategory);
        return "category/category";
    }

    @PostMapping("/category/search")
    public String getCategory(@ModelAttribute("search-input") String search_input, Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> pageCategory = categoryService.searchCategory(search_input, pageable);
        model.addAttribute("pageCategory", pageCategory);
        model.addAttribute("search_dashboard", "search_dashboard");
        model.addAttribute("search_input", search_input);
        session.setAttribute("search_input_category", search_input);
        return "category/category";
    }

    @GetMapping("/category/search")
    public String getCategorySearch(Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        String search_input = (String) session.getAttribute("search_input_category");
        Page<Category> pageCategory = categoryService.searchCategory(search_input, pageable);
        model.addAttribute("pageCategory", pageCategory);
        model.addAttribute("search_input", search_input);
        model.addAttribute("search_dashboard", "search_dashboard");
        session.setAttribute("search_input_category", search_input);
        return "category/category";
    }

    @GetMapping("/category/search/{page}")
    public String getCategorySearch(@PathVariable int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        String search_input = (String) session.getAttribute("search_input_category");
        Page<Category> pageCategory = categoryService.searchCategory(search_input, pageable);
        model.addAttribute("pageCategory", pageCategory);
        model.addAttribute("search_input", search_input);
        model.addAttribute("search_dashboard", "search_dashboard");
        session.setAttribute("search_input_category", search_input);
        return "category/category";
    }

    @GetMapping("/category/add")
    public String addCategory(Model model) {
      model.addAttribute("category",new Category());
        return "category/category_add";
    }

    @PostMapping("/category/addc")
    public String addCategory( @ModelAttribute("category") Category category){
        categoryService.saveCategory(category);
        session.setAttribute("addCategory", "addCategorySuccess");
     return "redirect:/category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return "redirect:/category";
    }

    @GetMapping("/category/edit/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "category/category_edit";
    }

    @PostMapping("/category/editp")
    public String editCategory(@ModelAttribute("category") Category category) {
        categoryService.saveEditCategory(category);
        session.setAttribute("editCategory", "editCategorySuccess");
        return "redirect:/category";
    }


}
