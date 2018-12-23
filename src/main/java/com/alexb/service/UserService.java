package com.alexb.service;

import com.alexb.exception.UserNotFoundException;
import com.alexb.model.User;
import com.alexb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ExchangeProperty;
import org.apache.camel.Header;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public User getById(@Header("id") String id, @ExchangeProperty("value") String value) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
