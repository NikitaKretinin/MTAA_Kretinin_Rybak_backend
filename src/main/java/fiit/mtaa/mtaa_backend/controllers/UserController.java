package fiit.mtaa.mtaa_backend.controllers;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import fiit.mtaa.mtaa_backend.models.User;
import fiit.mtaa.mtaa_backend.services.UserService;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUsers")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/getUser/{id}")
    public JSONObject getUser(@PathVariable(value = "id") Long userID) {
        User user = userService.getUserById(userID);
        JSONObject jo = new JSONObject();
        jo.put("login", user.getLogin());
        jo.put("password", user.getPassword());
        jo.put("user_role", user.getUser_role());
        jo.put("id", user.getId());
        return jo;
    }

    @GetMapping("/getUser")
    public JSONObject getUserByLogin(@RequestParam String login) {
        User user = userService.getUserByLogin(login);
        JSONObject jo = new JSONObject();
        jo.put("login", user.getLogin());
        jo.put("password", user.getPassword());
        jo.put("user_role", user.getUser_role());
        jo.put("id", user.getId());
        return jo;
    }

    @PostMapping("/addUser")
    public JSONObject addProduct(@RequestBody User user) {
        userService.saveUser(user);
        JSONObject jo = new JSONObject();
        jo.put("login", user.getLogin());
        jo.put("password", user.getPassword());
        jo.put("user_role", user.getUser_role());
        return jo;
    }

}
