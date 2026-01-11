package com.example.server;

import com.example.server.model.User;
import com.example.server.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.server.repository")
@EntityScan(basePackages = "com.example.server.model")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

//    @Bean
//    @Transactional
//    public CommandLineRunner init(UserRepository userRepository) {
//        return args -> {
//            if (userRepository.count() == 0) {
//                User user1 = new User();
//                user1.setName("Настюша");
//                userRepository.saveAndFlush(user1); // сразу пишем в базу
//
//                User user2 = new User();
//                user2.setName("Вова");
//                user2.setPartnerId(user1.getId());
//                userRepository.save(user2);
//
//                user1.setPartnerId(user2.getId());
//                userRepository.save(user1);
//            }
//        };
//    }
}
