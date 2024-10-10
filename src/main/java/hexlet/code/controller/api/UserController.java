package hexlet.code.controller.api;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(path = "")
    public ResponseEntity<List<UserDTO>> index() {
        var users = userRepository.findAll().stream()
                .map(userMapper::map)
                .toList();
        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Count", String.valueOf(users.size()))
                .body(users);
    }

    @GetMapping(path = "/{id}")
    public UserDTO show(@PathVariable long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        var userDto = userMapper.map(user);
        return userDto;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreateDTO userData) {
        var user = userMapper.map(userData);
        userRepository.save(user);
        var userDto = userMapper.map(user);
        return userDto;
    }

    @PutMapping("/{id}")
    public UserDTO update(@RequestBody @Valid UserUpdateDTO userData, @PathVariable Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        userMapper.update(userData, user);
        userRepository.save(user);
        return userMapper.map(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
