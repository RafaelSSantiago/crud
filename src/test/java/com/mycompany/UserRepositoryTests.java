package com.mycompany;

import com.mycompany.user.User;
import com.mycompany.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNew(){
        User user = new User();
        user.setEmail("caio.aocp@gmail.com");
        user.setPassword("123456");
        user.setFirstName("caio");
        user.setLastName("santiago");

        User savedUser = repo.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<User> users = repo.findAll();
        assertThat(users).hasSizeGreaterThan(0);

        for (User user : users){
            System.out.println(user);
        }
    }
    @Test
    public void testUpdate(){
        Integer userId = 1;
        Optional<User> optionalUsers = repo.findById(userId);
        User user = optionalUsers.get();
        user.setPassword("hello200");
        repo.save(user);

        User updateUser= repo.findById(userId).get();
        assertThat(updateUser.getPassword()).isEqualTo("hello200");
    }

    @Test
    public void testGet(){
        Integer userId = 2;
        Optional<User> optionalUser = repo.findById(userId);
        assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testeDelete(){
        Integer userId = 1;
        repo.deleteById(userId);

        Optional<User> optionalUser = repo.findById(userId);
        assertThat(optionalUser).isNotPresent();
    }
}
