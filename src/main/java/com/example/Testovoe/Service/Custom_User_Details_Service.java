package com.example.Testovoe.Service;

import com.example.Testovoe.Entity.User;
import com.example.Testovoe.Repository.User_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class Custom_User_Details_Service implements UserDetailsService {
    private final User_repository userRepository;

    @Autowired
    public Custom_User_Details_Service(User_repository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmails(email).orElseThrow(()-> new UsernameNotFoundException("Не удалось найти пользователя " + email));
        return build(user);
    }

    public User loadUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }


    public static User build(User user) {
        return new User(user.getId(), user.getName(), user.getDate_of_birth(), user.getPassword(), user.getAccount(), user.getPhones(), user.getEmails());
    }
}
