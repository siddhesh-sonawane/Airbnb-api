package com.Siddhesh.Airbnb.Services;


import com.Siddhesh.Airbnb.Model.User;
import com.Siddhesh.Airbnb.Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.awt.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*function for insertion of single user*/
    public User insertUser(User user){
        return userRepository.save(user);
    }

    public List<User>fetchAllUSers(){
        List<User> users = userRepository.findAll();
        return userRepository.findAll();

    }


}
