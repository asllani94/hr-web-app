package com.obss.Model.Dao;

import com.obss.Model.Jpa.User;
import com.obss.Model.Rep.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by arnold on 7/10/2017.
 */
@Service
public class UserDao {

    @Autowired
    UserRep userRep;
    public void createUser(User user)
    {
        userRep.save(user);

    }


    //Id or username? need to be checked
    public User loadUserByEmail(String email) {
        return  userRep.findByEmail(email);

    }
}
