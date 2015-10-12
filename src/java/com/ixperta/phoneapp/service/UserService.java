package com.ixperta.phoneapp.service;

import com.ixperta.phoneapp.domain.User;
import java.util.HashMap;

public interface UserService {

    public User getUserById(String id);

    public HashMap deleteUserById(String id);

    public HashMap save(User person);
    
    public HashMap getUsers();
}
