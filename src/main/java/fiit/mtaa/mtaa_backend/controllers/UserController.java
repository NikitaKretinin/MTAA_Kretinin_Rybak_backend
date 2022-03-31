package fiit.mtaa.mtaa_backend.controllers;

import fiit.mtaa.mtaa_backend.models.Contact;
import fiit.mtaa.mtaa_backend.services.ContactService;
import fiit.mtaa.mtaa_backend.artifacts_data.TokenManager;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fiit.mtaa.mtaa_backend.models.User;
import fiit.mtaa.mtaa_backend.services.UserService;

import java.util.*;

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
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long userID)
            throws ResourceNotFoundException{
        try {
            User user = userService.getUserById(userID);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUserByLogin(@RequestParam String login)
            throws ResourceNotFoundException {
        try {
            User user = userService.getUserByLogin(login);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            if (user.getContact() != null) {
                contactService.saveContact(user.getContact());
            }
            User saved = userService.saveUser(user);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getToken")
    public Object getToken(@RequestBody JSONObject req)
            throws ResourceNotFoundException {
        try {
            String login = req.getAsString("login");
            String pass = req.getAsString("password");

            User user = userService.getUserByLogin(login);

            if (Objects.equals(user.getPassword(), pass)) {
                return new ResponseEntity<>(TokenManager.createToken(user), HttpStatus.OK);
            } else {
                throw new ResourceNotFoundException("Not found");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editUser/{token}")
    public Object editUser(@RequestBody (required=false) User user,
                           @RequestHeader(value = "Authorization") String token) {
        try {
            if (TokenManager.validToken(token, "manager")) {
                if (user == null) { // if json body of request is empty
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
                User edit_user = userService.getUserById(TokenManager.getIdByToken(token));
                if (user.getUser_role() != null && !user.getUser_role().equals(edit_user.getUser_role())) { // if we want to change user's role -> error
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
                if (user.getLogin() != null) {
                    edit_user.setLogin(user.getLogin());
                }
                if (user.getPassword() != null) {
                    edit_user.setPassword(user.getPassword());
                }
                if (user.getContact() != null) {
                    if (contactService.contactExists(user.getContact()) == 0){ // if contact doesn't exist
                        contactService.saveContact(user.getContact());
                        edit_user.setContact(user.getContact());
                    } else if (contactService.contactExists(user.getContact()) == 1) { // if contact is already in DB
                        Contact new_cont = contactService.getContact(user.getContact());
                        edit_user.setContact(new_cont);
                    }
                }
                edit_user = userService.updateUser(edit_user);
                return new ResponseEntity<>(edit_user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
