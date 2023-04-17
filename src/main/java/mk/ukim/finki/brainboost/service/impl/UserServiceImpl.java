package mk.ukim.finki.brainboost.service.impl;

import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.domain.enumerations.Role;
import mk.ukim.finki.brainboost.domain.exceptions.InvalidArgumentsException;
import mk.ukim.finki.brainboost.domain.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.brainboost.domain.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.brainboost.domain.exceptions.UserAlreadyExistsException;
import mk.ukim.finki.brainboost.repository.UserRepository;
import mk.ukim.finki.brainboost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        //User user = new User(firstName, lastName, email, username, password);
        userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(InvalidUserCredentialsException::new);
    }
}
