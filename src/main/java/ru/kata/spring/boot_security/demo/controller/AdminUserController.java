package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/users-list";
    }

    @PostMapping("/{userId}/role")
    public String changeUserRole(@PathVariable Long userId, @RequestParam Long roleId) {
        userService.changeUserRole(userId, roleId);
        return "redirect:/admin/users";
    }

    @PostMapping("/{userId}/delete")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);
        return "redirect:/admin/users";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam String keyword, Model model) {
        model.addAttribute("users", userService.searchUsers(keyword));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/users-list";
    }
}


