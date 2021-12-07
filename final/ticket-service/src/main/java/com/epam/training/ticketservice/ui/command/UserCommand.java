package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Objects;
import java.util.Optional;

@ShellComponent
public class UserCommand {
    private final UserService userService;

    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod(key = "sign in privileged", value = "Signing in as an admin.")
    public String signInPrivileged(String username, String password) {
        Optional<UserDto> user = userService.singInPrivileged(username, password);
        if (user.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }
        return user.get() + "signed in.";
    }

    @ShellMethod(key = "sign out", value = "Signing out.")
    public String signOut() {
        Optional<UserDto> user = userService.signOut();
        if (user.isEmpty()) {
            return "You need to sign in first!";
        }
        return user.get() + " is signed out!";
    }

    @ShellMethod(key = "describe account",
            value = "You can query the type and state of the currently signed in account.")
    public String describeAccount() {
        Optional<UserDto> user = userService.describeAccount();
        if (user.isEmpty()) {
            return "You are not signed in";
        }
        if (Objects.equals(userService.getRole(), User.Role.ADMIN)) {
            return "Signed in with privileged account" + user.get();
        } else {
            return "Signed in with account" + user.get();
        }
    }

    @ShellMethod(key = "sign up", value = "Create a non-admin account")
    public String signUp(String userName, String password) {
        try {
            userService.signUp(userName, password);
            return "Signing up was successful!";
        } catch (Exception e) {
            return "Signing up failed!";
        }
    }

    @ShellMethod(key = "sign in", value = "Sign in as non-admin user")
    public String signIn(String userName, String password) {
        Optional<UserDto> user = userService.signIn(userName, password);
        if (user.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }
        return "Signed in as:" + user.get();
    }
}