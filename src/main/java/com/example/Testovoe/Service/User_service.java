package com.example.Testovoe.Service;

import com.example.Testovoe.DTO.User_DTO;
import com.example.Testovoe.Entity.Account;
import com.example.Testovoe.Entity.Email_data;
import com.example.Testovoe.Entity.Phone_data;
import com.example.Testovoe.Entity.User;
import com.example.Testovoe.Payload.Request.Signup_Request;
import com.example.Testovoe.Repository.User_repository;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class User_service {
    public static final Logger LOG = LoggerFactory.getLogger(User_service.class);

    public final User_repository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final Lock transferLock = new ReentrantLock();

    @Autowired
    public User_service(User_repository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(Signup_Request userIn) throws Exception {
        User user = new User();
        Account account = new Account();
        account.setBalance(new BigDecimal("100"));
        account.setUser(user);
        user.setAccount(account);
        user.setName(userIn.getName());

        List<Phone_data> phones = new ArrayList<>();
        Phone_data phoneData = new Phone_data();
        phoneData.setPhone(userIn.getPhone());
        phoneData.setUser(user);
        phones.add(phoneData);
        user.setPhones(phones);

        List<Email_data> emails = new ArrayList<>();
        Email_data emailData = new Email_data();
        emailData.setEmail(userIn.getEmail());
        emailData.setUser(user);
        emails.add(emailData);
        user.setEmails(emails);

        user.setAccount(account);

        user.setPassword(passwordEncoder.encode(userIn.getPassword()));

        try {
            LOG.info("Сохранен пользователь {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Ошибка регистрации пользователя {}", e.getMessage());
            throw new Exception("Пользователь уже существует");
        }

    }

    @Transactional
    public User updateUser(User_DTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);


        Set<String> existingEmails = userRepository.findAll().stream()
                .flatMap(u -> u.getEmails().stream().map(Email_data::getEmail))
                .collect(Collectors.toSet());

        Set<String> existingPhones = userRepository.findAll().stream()
                .flatMap(u -> u.getPhones().stream().map(Phone_data::getPhone))
                .collect(Collectors.toSet());

        if (userDTO.getEmails() != null && !userDTO.getEmails().isEmpty()) {
            List<Email_data> newEmails = new ArrayList<>();
            for (Email_data emailData : userDTO.getEmails()) {
                String email = emailData.getEmail();
                if (email != null && !email.trim().isEmpty()) {
                    if (!email.equals(emailData.getEmail()) && existingEmails.contains(email)) {
                        throw new IllegalArgumentException("Email " + email + " уже используется другим пользователем.");
                    }
                    emailData.setUser(user);
                    newEmails.add(emailData);
                }
            }
            user.setEmails(newEmails);
        }

        if (userDTO.getPhones() != null && !userDTO.getPhones().isEmpty()) {
            List<Phone_data> newPhones = new ArrayList<>();
            for (Phone_data phoneData : userDTO.getPhones()) {
                String phone = phoneData.getPhone();
                if (phone != null && !phone.trim().isEmpty()) {
                    if (!phone.equals(phoneData.getPhone()) && existingPhones.contains(phone)) {
                        throw new IllegalArgumentException("Phone " + phone + " уже используется другим пользователем.");
                    }
                    phoneData.setUser(user);
                    newPhones.add(phoneData);
                }
            }
            user.setPhones(newPhones);
        }

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    public Page<User> searchUsers(String name, String phone, String email, LocalDateTime date_of_birth, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<User> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), name + "%"));
            }

            if (phone != null && !phone.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.join("phones").get("phone"), phone));
            }

            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.join("emails").get("email"), email));
            }

            if (date_of_birth != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("date_of_birth"), date_of_birth));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return userRepository.findAll(spec, pageable);
    }


    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    public User getUserByPrincipal(Principal principal) {
        String name = principal.getName();
        return userRepository.findUserByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + name + " не найден"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Пользователь не может найтись"));
    }

}
