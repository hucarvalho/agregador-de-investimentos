package carval.adi.agregadorDeInvestimentos.service;

import carval.adi.agregadorDeInvestimentos.dto.UserCreateRecordDto;
import carval.adi.agregadorDeInvestimentos.entity.User;
import carval.adi.agregadorDeInvestimentos.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UUID create(UserCreateRecordDto userCreateRecordDto)
    {
        User user = new User(
                UUID.randomUUID(),
                userCreateRecordDto.username(),
                userCreateRecordDto.email(),
                userCreateRecordDto.password(),
                Instant.now(),
                null
        );

        var saved = repository.save(user);

        return saved.getId();
    }

    public User find(String id)
    {
        Optional<User> optionalUser = repository.findById(UUID.fromString(id));
        return optionalUser.orElse(null);
    }

    public List<User> get()
    {
        return  repository.findAll();
    }
}
