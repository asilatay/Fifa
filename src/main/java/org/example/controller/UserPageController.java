package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class UserPageController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ModelAndView showUserListPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> userList = userService.fetchUserList();
        modelAndView.setViewName("userList");
        modelAndView.addObject("users", userList);
        return modelAndView;
    }

    @GetMapping("/user/add")
    public ModelAndView addNewUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAdd");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/user/save")
    public ModelAndView saveUser(@ModelAttribute("user") User user) throws Exception {
        userService.saveUser(user);
        return showUserListPage();
    }

    @GetMapping("/user/edit/{id}")
    public ModelAndView editUser(@PathVariable(value = "id") long id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getById(id);
        modelAndView.setViewName("userEdit");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/user/cancel/{id}")
    public ModelAndView cancelUser(@PathVariable(value = "id") long id) {
        userService.setPassiveUser(id);
        return showUserListPage();

    }

    @GetMapping("/user/activate/{id}")
    public ModelAndView activateUser(@PathVariable(value = "id") long id) {
        userService.activateUser(id);
        return showUserListPage();
    }
}
