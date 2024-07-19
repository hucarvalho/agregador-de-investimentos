package carval.adi.agregadorDeInvestimentos.controller;

import carval.adi.agregadorDeInvestimentos.dto.*;
import carval.adi.agregadorDeInvestimentos.entity.User;
import carval.adi.agregadorDeInvestimentos.repository.UserRepository;
import carval.adi.agregadorDeInvestimentos.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserCreateRecordDto body)
    {
        var id = service.create(body);
        return ResponseEntity.created(URI.create("/v1/users/" + id.toString())).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(name = "id") String id)
    {
        var user = service.find(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseRecordDto>> get()
    {
        List<UserResponseRecordDto> users = service.get();
        if(!users.isEmpty()){
            return  ResponseEntity.ok(users);
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> destroy(@PathVariable(name = "id") String id)
    {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> edit(@PathVariable(name = "id") String id, @RequestBody UserUpdateRecordDto userUpdateRecordDto)
    {
        service.update(id, userUpdateRecordDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/accounts")
    public ResponseEntity<Void> storeAccount(@PathVariable(name = "id") String id,
                                             @RequestBody AccountCreateRecordDto accountCreateRecordDto)
    {
        service.createAccount(id, accountCreateRecordDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<AccountResponseDto>> getAccount(@PathVariable(name = "id") String id)
    {
        var accounts = service.getAccounts(id);
        return ResponseEntity.ok(accounts);
    }

}
