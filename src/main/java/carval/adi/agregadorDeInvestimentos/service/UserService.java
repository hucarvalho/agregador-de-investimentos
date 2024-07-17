package carval.adi.agregadorDeInvestimentos.service;

import carval.adi.agregadorDeInvestimentos.dto.UserCreateRecordDto;
import carval.adi.agregadorDeInvestimentos.dto.UserUpdateRecordDto;
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

    public Optional<User> find(String id)
    {
        Optional<User> optionalUser = repository.findById(UUID.fromString(id));
        return optionalUser;
    }

    public List<User> get()
    {
        return  repository.findAll();
    }

    public void delete(String id)
    {
        boolean userExist = repository.existsById(UUID.fromString(id));
        if(userExist) {
            repository.deleteById(UUID.fromString(id));
        }

    }
    public void update(String id, UserUpdateRecordDto userUpdateRecordDto)
    {
        var userEntity = repository.findById(UUID.fromString(id));
        if(userEntity.isPresent()){
            var user = userEntity.get();
            if(userUpdateRecordDto.username() != null){
                user.setUsername(userUpdateRecordDto.username());
            }
            if(userUpdateRecordDto.password() != null){
                user.setPassword(userUpdateRecordDto.password());
            }
            repository.save(user);
        }
    }


}
