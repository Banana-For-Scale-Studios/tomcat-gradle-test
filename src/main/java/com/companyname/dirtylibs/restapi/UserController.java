package com.companyname.dirtylibs.restapi;

import com.companyname.dirtylibs.persistence.User;
import com.companyname.dirtylibs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("rest/" + UserController.uriExtension)
@Transactional
public class UserController {
    public static final String uriExtension = "users";

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @RequestMapping
    @ResponseBody
    public List<User> getUserByUsername(@RequestParam List<String> usernames) {
        return userService.findByUsernames(usernames);
    }

    @RequestMapping("{id}")
    @ResponseBody
    public User getUserById(@PathVariable int id) {
        return userService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public User createUser(@RequestParam String username, @RequestParam String password) {
        return userService.save(new User(username, password));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public User newPassword(@PathVariable int id, @RequestParam String password) {
        User user = userService.findById(id);
        user.setPassword(password);
        return userService.save(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteUser(@PathVariable int id) {
        userService.delete(userService.findById(id));
    }
}
