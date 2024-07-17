package carval.adi.agregadorDeInvestimentos.controller;

import carval.adi.agregadorDeInvestimentos.dto.UserCreateRecordDto;
import carval.adi.agregadorDeInvestimentos.dto.UserUpdateRecordDto;
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

    private UserService service;

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
    public ResponseEntity<List<User>> get()
    {
        List<User> users = service.get();
        if(users.size() > 0){
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

}
