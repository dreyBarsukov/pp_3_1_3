package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserController() {
    }

    @GetMapping("/admin/users")
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @GetMapping("/admin/users/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping("/admin/users/new")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new";
        } else {
            user.setRoles(new HashSet<Role>(Set.of(new Role("ROLE_USER"))));
            userService.save(user);
            return "redirect:/admin/users";
        }
    }

    @GetMapping("/admin/users/edit")
    public String edit(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "edit";
    }

    @PostMapping("/admin/users/update")
    public String update(@RequestParam(value = "id") Long id, @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        } else {
            userService.update(id, user);
            return "redirect:/admin/users";
        }

    }

    @PostMapping("/admin/users/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/user")
    public String showUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "admin";
    }
}
