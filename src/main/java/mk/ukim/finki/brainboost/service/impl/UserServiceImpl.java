package mk.ukim.finki.brainboost.service.impl;

import mk.ukim.finki.brainboost.domain.Category;
import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.domain.exceptions.*;
import mk.ukim.finki.brainboost.repository.UserRepository;
import mk.ukim.finki.brainboost.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(String username, String password, String repeatPassword, String firstName, String lastName, String email) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()
                || firstName == null || firstName.isEmpty() || lastName == null
                || lastName.isEmpty() || email == null || email.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }
        if (this.userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(username);
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(firstName, lastName, email, username, encodedPassword);
        userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(InvalidUserCredentialsException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidUserCredentialsException();
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public Optional<User> edit(Long id, String name, String surname, String email, String username, Authentication authentication) {
        User user= (User) authentication.getPrincipal();

        user.setFirstName(name);
        user.setLastName(surname);
        user.setEmail(email);
        user.setUsername(username);
        return Optional.of(this.userRepository.save(user));
    }


}
