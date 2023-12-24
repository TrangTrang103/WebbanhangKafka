package com.doan.admin.user;

import com.doan.mutual.entity.Category;
import com.doan.mutual.entity.Role;
import com.doan.mutual.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    HttpSession session;

    @Autowired
    private RoleRepository roleRepository;
    @GetMapping("/user")
    private String getCategory(Model model){
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> pageCategory = userService.getAll(pageable);
        List<User> pa = userService.getAllUser();
        pa.get(0).getRoles();
        model.addAttribute("pageUser", pageCategory);
        String addUser = (String) session.getAttribute("addUser");
        String editUser = (String) session.getAttribute("editUser");
        model.addAttribute("addUser", addUser);
        model.addAttribute("editUser", editUser);
        session.setAttribute("addUser", null);
        session.setAttribute("editUser", null);
        return "user/user";
    }

    @GetMapping("/user/add")
    public String addCategory(Model model) {
        List<Role> listRole = (List<Role>) roleRepository.findAll();
        model.addAttribute("listRole", listRole);
        model.addAttribute("user",new User());
        return "user/user_add";
    }

    @PostMapping("/user/addc")
    public String addCategory( @ModelAttribute("user") User user){
        userService.saveUser(user);
        session.setAttribute("addUser", "addUserSuccess");
        return "redirect:/user";
    }

}
