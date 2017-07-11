package com.obss.Model.Rep;

import com.obss.Model.Jpa.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by arnold on 7/10/2017.
 */
public interface UserRep extends CrudRepository<User,Integer> {
    public User findByEmail(String email);
}
