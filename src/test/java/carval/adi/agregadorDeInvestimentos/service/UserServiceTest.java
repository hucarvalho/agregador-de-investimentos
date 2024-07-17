package carval.adi.agregadorDeInvestimentos.service;

import carval.adi.agregadorDeInvestimentos.dto.UserCreateRecordDto;
import carval.adi.agregadorDeInvestimentos.entity.User;
import carval.adi.agregadorDeInvestimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class UserServiceTest {

    @Mock
    private UserRepository repository;
    //garantir que classes que são usadas na classe que será testada não interfiram no teste

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @InjectMocks
    private UserService userService;
    //injeção da classe testada seguindo o mock acima


    //feature que permite abrir subclasses dentro do teste para definir como serao feitos os testes

    @Nested
    class create{

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUser()
        {
            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "Usuario",
                    "usuario@gmail.com",
                    "123",
                    Instant.now(),
                    null
            );

            doReturn(user).when(repository).save(userArgumentCaptor.capture());

            var input = new UserCreateRecordDto(
                    "Usuario",
                    "usuario@gmail.com",
                    "123"
            );


            //Act
            var output = userService.create(input);

            //Assert
            var userCaptured = userArgumentCaptor.getValue();

            assertNotNull(output);
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }
        @Test
        @DisplayName("Should throws a exception when a error occurs")
        void shouldThrowExceptionWhenErrorOccurs()
        {
            doThrow(new RuntimeException()).when(repository).save(any());
            var input = new UserCreateRecordDto(
                    "Usuario",
                    "usuario@gmail.com",
                    "123"
            );

            assertThrows(RuntimeException.class, () -> userService.create(input));
        }
    }
    @Nested
    class find{
        @Test
        void shouldFindAUserByIdWithSuccessWhenOptionalIsPresent() {
            var user = new User(
                    UUID.randomUUID(),
                    "Usuario",
                    "usuario@gmail.com",
                    "123",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(repository).findById(uuidArgumentCaptor.capture());

            //act
            var output = userService.find(user.getId().toString());

            //assert
            var uuidCapture = uuidArgumentCaptor.getValue();

            assertTrue(output.isPresent());
            assertEquals(user.getId(), uuidCapture);
        }

        @Test
        void shouldFindAUserByIdWithSuccessWhenOptionalIsEmpty() {
            var userId = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(repository)
                    .findById(uuidArgumentCaptor.capture());

            //act
            var output = userService.find(userId.toString());

            //assert
            var uuidCapture = uuidArgumentCaptor.getValue();

            assertTrue(output.isEmpty());
            assertEquals(userId, uuidCapture);
        }
    }
    @Nested
    class get{
        @Test
        void shouldReturnAllUsersWithSuccess() {
            var user = new User(
                    UUID.randomUUID(),
                    "Usuario",
                    "usuario@gmail.com",
                    "123",
                    Instant.now(),
                    null
            );
            var usersList = List.of(user);

            doReturn(usersList)
                    .when(repository)
                    .findAll();

            var output = userService.get();

            assertNotNull(output);
            assertEquals(usersList.size(), output.size());
        }
    }
    @Nested
    class delete{
        @Test
        void shouldDeleteUserWithSuccessWhenUserExists() {

            doReturn(true)
                    .when(repository)
                    .existsById(uuidArgumentCaptor.capture());
            doNothing()
                    .when(repository)
                    .deleteById(uuidArgumentCaptor.capture());
            var userId = UUID.randomUUID();

            //act
            userService.delete(userId.toString());

            //assert
            var idList = uuidArgumentCaptor.getAllValues();


            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(repository, times(1)).existsById(idList.get(0));
            verify(repository, times(1)).deleteById(idList.get(1));
        }

        @Test
        void shouldNotDeleteUserWhenUsersNotExists() {

            doReturn(false)
                    .when(repository)
                    .existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            //act
            userService.delete(userId.toString());

            //assert
            var uuid = uuidArgumentCaptor.getValue();


            assertEquals(userId, uuid);


            verify(repository, times(1)).existsById(uuid);
            verify(repository, times(0)).deleteById(any());

        }
    }


}