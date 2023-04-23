package com.techmaster.securitysession3;

import com.mysql.cj.xdevapi.SessionFactory;
import com.techmaster.securitysession3.entity.Role;
import com.techmaster.securitysession3.entity.User;
import com.techmaster.securitysession3.repository.RoleRepository;
import com.techmaster.securitysession3.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;

@SpringBootTest
class SecuritySession3ApplicationTests {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Test
    void createRoles(){
        Role roleUser = Role.builder().name("USER").build();
        Role roleAdmin = Role.builder().name("ADMIN").build();
        Role roleAuthor = Role.builder().name("AUTHOR").build();
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
        roleRepository.save(roleAuthor);
    }
    @Test
    void contextLoads() {
        Role roleUser = roleRepository.findById(2).orElse(null);
//        Role roleAdmin = roleRepository.findById(1).orElse(null);
//        Role roleAuthor = roleRepository.findById(3).orElse(null);
//        Role roleUser = Role.builder().name("USER").build();
        System.out.println(roleUser.getName());
        User user = User.builder()
                .name("quang")
                .email("user")
                .password(encoder.encode("123456"))
                .enable(true)
                .roles(List.of(roleUser)).build();
//        User admin = User.builder()
//                .name("mai")
//                .email("admin")
//                .password(encoder.encode("123456"))
//                .roles(List.of(roleUser, Objects.requireNonNull(roleAdmin))).build();
//        User author = User.builder()
//                .name("Ha")
//                .email("author")
//                .password(encoder.encode("234234"))
//                .roles(List.of(Objects.requireNonNull(roleAuthor))).build();
        userRepository.save(user);
//        userRepository.save(admin);
//        userRepository.save(author);
    }

}
