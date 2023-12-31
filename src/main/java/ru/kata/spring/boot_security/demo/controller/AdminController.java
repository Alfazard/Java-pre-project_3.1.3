package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AdminService;
import ru.kata.spring.boot_security.demo.service.RoleService;

/**
 * @author Alfazard on 08.07.2023
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final RoleService roleService;

    public AdminController(AdminService adminService, RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @GetMapping("/adminpage")
    public String adminPage() {
        return "/adminpage";
    }

    @GetMapping("/usersinfo")
    public String showUsersInfo(Model model, Model role){
        role.addAttribute("rolesList", roleService.getRolesList());
        model.addAttribute("users", adminService.showAllUsers());
        return "/usersinfo";
    }

    @GetMapping("/user/{id}")
    public String userPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user",adminService.getUserById(id));
        return "/user";
    }

    @GetMapping("/new")
    public String addUser(Model model, Model role) {
        role.addAttribute("rolesList", roleService.getRolesList());
        model.addAttribute("user", new User());
        return "/new";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("user") User user){

        return "redirect:/usersinfo";
    }

    @GetMapping("/{id}/edit")
    public String updateUser(Model model, @PathVariable("id") Long id, Model role) {
        role.addAttribute("rolesList", roleService.getRolesList());
        var user = adminService.getUserById(id);
        user.setPassword(null);
        model.addAttribute("user", user);

        return "/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") Long id) {
        adminService.editUser(id, user);

        return "redirect:/usersinfo";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        adminService.deleteUser(id);

        return "redirect:/usersinfo";
    }

}
