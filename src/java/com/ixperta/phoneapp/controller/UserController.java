package com.ixperta.phoneapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ixperta.phoneapp.domain.User;
import com.ixperta.phoneapp.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Controller
@RequestMapping("api")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("user/{id}")
    @ResponseBody
    public User getById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    /* same as above method, but is mapped to
     * /api/user?id= rather than /api/user/{id}
     */
    @RequestMapping(value = "user", params = "id")
    @ResponseBody
    public User getByIdFromParam(@RequestParam("id") String id) {
        return userService.getUserById(id);
    }

    @RequestMapping("deleteUser/{id}")
    @ResponseBody
    public HashMap deleteStationById(@PathVariable String id) {
        return userService.deleteUserById(id);
    }

    /* same as above method, but is mapped to
     * /api/deleteStation?id= rather than /api/deleteStation/{id}
     */
    @RequestMapping(value = "deleteUser", params = "id")
    @ResponseBody
    public HashMap deleteStationByIdFromParam(@RequestParam("id") String id) {
        return userService.deleteUserById(id);
    }

    /**
     * Saves new/edit user.
     *
     * @param user
     * @return String indicating success or failure of save
     */
    @RequestMapping(value = "saveUser", method = RequestMethod.POST)
    @ResponseBody
    public HashMap saveUser(@RequestParam(value = "stationsId[]",required=false) String[] stationsId,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "surname") String surname,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "id") String id) {
        
        User user = new User();
        
        user.setEmail(email);
        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);
        if (stationsId != null)
            user.setStationsId(new ArrayList( Arrays.asList(stationsId )));
        return userService.save(user);
    }

    /**
     * get all users.
     *
     * @return object with array of users
     */
    @RequestMapping(value = "userslist", method = RequestMethod.GET)
    @ResponseBody
    public HashMap getUsers() {
        return userService.getUsers();
    }
}
