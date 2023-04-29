package mk.ukim.finki.brainboost.service;

import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.domain.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService  extends UserDetailsService{
    void register(String username, String password, String repeatPassword, String firstName, String lastName, String email);
    User login(String username, String password);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
