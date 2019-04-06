package com.krokogator.spring.resources.user.dto;

import com.krokogator.spring.resources.user.User;
import com.krokogator.spring.utils.EntityDTO;

public class UserDTO {

    public static class MinimalItem implements EntityDTO<User> {
        public Long id;

        @Override
        public void toDto(User user) {
            this.id = user.getId();
        }
    }

    public static class ListItem extends MinimalItem {
        public String username;

        @Override
        public void toDto(User user) {
            super.toDto(user);
            this.username = user.getUsername();
        }
    }

}
