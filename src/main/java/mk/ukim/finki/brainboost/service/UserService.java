package mk.ukim.finki.brainboost.service;

import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.domain.enumerations.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService  extends UserDetailsService{
    void register(String username, String password, String repeatPassword, String firstName, String lastName, String email);
    User login(String username, String password);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    Optional<User> edit(Long id, String name, String surname, String email, String username, Authentication authentication);
}
