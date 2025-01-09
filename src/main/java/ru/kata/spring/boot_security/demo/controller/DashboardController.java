package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.Course;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.CourseService;

import java.util.List;

@Controller
public class DashboardController {

    private final CourseService courseService;

    @Autowired
    public DashboardController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal User user) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);

        if (user != null) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("isAuthenticated", false);
        }

        return "dashboard"; // Возвращает HTML-шаблон "dashboard.html"
    }

}
