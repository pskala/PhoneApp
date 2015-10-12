package com.ixperta.phoneapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ixperta.phoneapp.dao.MyPersistenceDAO;
import com.ixperta.phoneapp.domain.User;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MyPersistenceDAO myDAO;

    @Override
    public User getUserById(String id) {
        return myDAO.findByUserId(id);
    }

    @Override
    public HashMap deleteUserById(String id) {
        return myDAO.deleteUser(id);
    }

    @Override
    public HashMap getUsers() {
        return myDAO.listUsers();
    }
    
    @Override
    public HashMap save(User user) {
        return myDAO.insert(user);
    }

}
