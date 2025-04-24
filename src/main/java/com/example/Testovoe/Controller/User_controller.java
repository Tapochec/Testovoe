package com.example.Testovoe.Controller;

import com.example.Testovoe.DTO.User_DTO;
import com.example.Testovoe.Entity.User;
import com.example.Testovoe.Mapper.UserMapper;
import com.example.Testovoe.Service.User_service;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("api/user")
public class User_controller {

    private static final Logger LOG = LoggerFactory.getLogger(User_controller.class);
    private static final Lock transferLock = new ReentrantLock();


    @Autowired
    private User_service userService;
    @Autowired
    private UserMapper user_mapper;

    @GetMapping("/search")
    public ResponseEntity<Page<User>> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date_of_birth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<User> users = userService.searchUsers(name, phone, email, date_of_birth, page, size);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User_DTO userDTO, Principal principal) {


        User user = userService.updateUser(userDTO, principal);

        User_DTO userUpdated = user_mapper.UserToUser_DTO(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferFunds(
            @RequestParam("toUserId") Long toUserId,
            @RequestParam("amount") BigDecimal amount,
            Principal principal) {
        try {
            User sender = userService.getUserByPrincipal(principal);
            if (sender == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не аутентифицирован.");
            }
            Long fromUserId = sender.getId();
            transferFundsInternal(fromUserId, toUserId, amount);
            return ResponseEntity.ok("Перевод успешно выполнен.");


        }  catch (IllegalArgumentException e) {
            LOG.error("Ошибка при переводе средств (IllegalArgumentException): {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            LOG.error("Неожиданная ошибка при переводе средств: {}", e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при переводе средств.");
        }
    }


    private void transferFundsInternal(Long fromUserId, Long toUserId, BigDecimal amount) throws Exception {
        if (fromUserId == null || toUserId == null || amount == null) {
            throw new IllegalArgumentException("ID отправителя, ID получателя и сумма перевода не могут быть пустыми.");
        }

        if (fromUserId.equals(toUserId)) {
            throw new IllegalArgumentException("ID отправителя и ID получателя не могут быть одинаковыми.");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной.");
        }

        try {
            transferLock.lock();

            LOG.info("Поиск пользователя-отправителя с ID: {}", fromUserId);
            User fromUser = userService.userRepository.findById(fromUserId)
                    .orElseThrow(() -> {
                        return new IllegalArgumentException("Пользователь с ID " + fromUserId + " не найден.");
                    });
            User toUser = userService.userRepository.findById(toUserId)
                    .orElseThrow(() -> {
                        return new IllegalArgumentException("Пользователь с ID " + toUserId + " не найден.");
                    });
            BigDecimal fromUserBalance = fromUser.getAccount().getBalance();
            if (fromUserBalance == null || fromUserBalance.compareTo(amount) < 0) {
                throw new Exception("Недостаточно средств для перевода.");
            }

            fromUser.getAccount().setBalance(fromUserBalance.subtract(amount));

            BigDecimal toUserBalance = toUser.getAccount().getBalance();
            if (toUserBalance == null) {
                toUserBalance = BigDecimal.ZERO;
            }
            toUser.getAccount().setBalance(toUserBalance.add(amount));

            userService.userRepository.save(fromUser);
            userService.userRepository.save(toUser);

        } catch (Exception e) {
            LOG.error("Ошибка при переводе средств: {}", e.getMessage());
            throw e;
        } finally {
            transferLock.unlock();
        }
    }
}
