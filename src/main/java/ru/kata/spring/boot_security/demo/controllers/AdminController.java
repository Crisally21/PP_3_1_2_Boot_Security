package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Services.UserService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Controller()
@RequestMapping()
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String admin(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }


    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "crud/user-create";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "crud/user-create";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUserForm(@RequestParam("id") Long id, Model model) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "crud/user-edit";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

}
