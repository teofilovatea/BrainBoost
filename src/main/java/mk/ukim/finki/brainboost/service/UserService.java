package mk.ukim.finki.brainboost.service;

import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.domain.enumerations.Role;

public interface UserService {
    void register(String username, String password, String repeatPassword, String firstName, String lastName, String email, String mobile, Role role);
    User login(String username, String password);
}
