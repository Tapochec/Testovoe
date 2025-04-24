package com.example.Testovoe.Mapper;

import com.example.Testovoe.DTO.User_DTO;
import com.example.Testovoe.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User_DTO UserToUser_DTO(User user) {
        User_DTO user_DTO = new User_DTO();
        user_DTO.setId(user.getId());
        user_DTO.setName(user.getName());
        user_DTO.setEmails(user.getEmails());
        user_DTO.setPhones(user.getPhones());
        return user_DTO;
    }
}
