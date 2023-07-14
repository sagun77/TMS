package io.dev.Tms.service.implemenation;

import io.dev.Tms.domain.User;
import io.dev.Tms.dto.UserDTO;
import io.dev.Tms.dtomapper.UserDTOMapper;
import io.dev.Tms.repository.UserRepository;
import io.dev.Tms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;
    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromuser(userRepository.create(user));
    }
}
