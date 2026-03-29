package com.Siddhesh.Airbnb.Controllers;


import com.Siddhesh.Airbnb.Model.User;
import com.Siddhesh.Airbnb.Services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User inserUser(@RequestBody User user){
        User result = userService.insertUser(user);
        return result;
    }


    @GetMapping("AllUser")
    public List<User> fetchAllUsers(){
        return userService.fetchAllUSers();
    }
}
