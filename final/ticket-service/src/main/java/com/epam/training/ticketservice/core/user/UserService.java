package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> signIn(String username, String password);

    Optional<UserDto> signOut();

    Optional<UserDto> describeAccount();

    Optional<UserDto> singInPrivileged(String username, String password);

    User.Role getRole();


    void signUp(String username, String password);

}

