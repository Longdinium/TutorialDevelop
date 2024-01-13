package com.techacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techacademy.entity.User;
    // このような一文はLesson16のRepositoryにはなかったが、なぜ？

public interface UserRepository extends JpaRepository<User, Integer>{

}
