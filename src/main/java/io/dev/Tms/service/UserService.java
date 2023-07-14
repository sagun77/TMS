package io.dev.Tms.service;

import io.dev.Tms.domain.User;
import io.dev.Tms.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
}
