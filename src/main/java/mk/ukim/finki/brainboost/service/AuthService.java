package mk.ukim.finki.brainboost.service;

import mk.ukim.finki.brainboost.domain.User;

public interface AuthService {

    User login(String username, String password);
}
