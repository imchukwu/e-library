package com.cimspace.e_library.service.implementation;

import com.cimspace.e_library.domain.Role;
import com.cimspace.e_library.domain.User;
import com.cimspace.e_library.exception.UserAlreadyExistException;
import com.cimspace.e_library.mapper.UserMapper.UserMapper;
import com.cimspace.e_library.model.UserRegistrationDTO;
import com.cimspace.e_library.model.UserRegistrationResponseDTO;
import com.cimspace.e_library.repos.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cimspace.e_library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserRegistrationDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapToDTO(user, new UserRegistrationDTO()))
                .collect(Collectors.toList());
    }

    public UserRegistrationDTO get(final Long userId) {
        return userRepository.findById(userId)
                .map(user -> mapToDTO(user, new UserRegistrationDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final UserRegistrationDTO userRegistrationDTO) {
        final User user = new User();
        mapToEntity(userRegistrationDTO, user);
        return String.valueOf(userRepository.save(user).getId());
    }

    public void update(final Long userId, final UserRegistrationDTO userRegistrationDTO) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(userRegistrationDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long userId) {
        userRepository.deleteById(userId);
    }

    private UserRegistrationDTO mapToDTO(final User user, final UserRegistrationDTO userRegistrationDTO) {
        userRegistrationDTO.setFirstname(user.getFirstname());
        userRegistrationDTO.setLastname(user.getLastname());
        userRegistrationDTO.setEmail(user.getEmail());
        userRegistrationDTO.setPassword(user.getPassword());
        return userRegistrationDTO;
    }

    private User mapToEntity(final UserRegistrationDTO userRegistrationDTO, final User user) {
        user.setFirstname(userRegistrationDTO.getFirstname());
        user.setLastname(userRegistrationDTO.getLastname());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(userRegistrationDTO.getPassword());
        return user;
    }

    @Override
    public UserRegistrationResponseDTO registerUserAccount(UserRegistrationDTO registrationDTO) throws UserAlreadyExistException {
        if (emailExists(registrationDTO.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + registrationDTO.getEmail());
        }

        User user = new User();
        user.setFirstname(registrationDTO.getFirstname());
        user.setLastname(registrationDTO.getLastname());
        user.setPassword(registrationDTO.getPassword());
        user.setEmail(registrationDTO.getEmail());
        user.setUserRole(Arrays.asList(new Role()));

        //Todo replace with mapstruct
        return UserMapper.toUserRespond(userRepository.save(user));

    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
