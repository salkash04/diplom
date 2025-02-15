package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String getUsers(Model model, @AuthenticationPrincipal User currentUser) {
        List<User> users = userService.getAllUsers();

        // Сортируем: сначала админы, потом обычные пользователи
        users.sort((u1, u2) -> {
            boolean u1IsAdmin = u1.getRole().getName().equalsIgnoreCase("ROLE_ADMIN");
            boolean u2IsAdmin = u2.getRole().getName().equalsIgnoreCase("ROLE_ADMIN");
            return Boolean.compare(u2IsAdmin, u1IsAdmin); // true (админ) будет раньше false (пользователь)
        });

        model.addAttribute("users", users);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("currentUser", currentUser);
        return "admin/users-list";
    }


    @PostMapping("/{userId}/role")
    public String changeUserRole(@PathVariable Long userId,
                                 @RequestParam Long roleId,
                                 @AuthenticationPrincipal User currentUser,
                                 RedirectAttributes redirectAttributes) {
        if (currentUser.getId().equals(userId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Вы не можете изменить свою собственную роль!");
            return "redirect:/admin/users";
        }

        userService.changeUserRole(userId, roleId);
        return "redirect:/admin/users";
    }


    @PostMapping("/{userId}/delete")
    public String deleteUser(@PathVariable Long userId,
                             @AuthenticationPrincipal User currentUser,
                             RedirectAttributes redirectAttributes) {

        if (currentUser.getId().equals(userId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Вы не можете удалить сами себя!");
            return "redirect:/admin/users";
        }

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


