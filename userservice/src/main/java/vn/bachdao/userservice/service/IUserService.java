package vn.bachdao.userservice.service;

import vn.bachdao.userservice.dto.CreateUserRequestDTO;
import vn.bachdao.userservice.dto.UserResponseDTO;

import java.util.List;

public interface IUserService {
    UserResponseDTO createUser(CreateUserRequestDTO dto);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, CreateUserRequestDTO dto);
    void deleteUser(Long id);
}