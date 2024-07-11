package com.chamika.books_project;

import com.chamika.books_project.role.Role;
import com.chamika.books_project.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BooksProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {

            if (roleRepository.findByRoleName("USER").isEmpty())
                roleRepository.save(
                        Role.builder()
                                .roleName("USER")
                                .build()
                );

        };
    }

}
