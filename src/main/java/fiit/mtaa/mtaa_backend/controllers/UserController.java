package fiit.mtaa.mtaa_backend.controllers;

import fiit.mtaa.mtaa_backend.services.ContactService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fiit.mtaa.mtaa_backend.models.User;
import fiit.mtaa.mtaa_backend.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;

    @GetMapping("/getUsers")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable(value = "id") Long userID) {
        User user = userService.getUserById(userID);
        return user;
    }

    @GetMapping("/getUser")
    public User getUserByLogin(@RequestParam String login) {
        User user = userService.getUserByLogin(login);
        return user;
    }

    @PostMapping("/addUser")
    public User addProduct(@RequestBody User user) {
        if (user.getContact() != null) {
            contactService.saveContact(user.getContact());
        }
        User saved = userService.saveUser(user);
        return saved;
    }

}
