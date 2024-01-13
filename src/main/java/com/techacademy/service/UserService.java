package com.techacademy.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.techacademy.entity.User;
import com.techacademy.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository repository) { // コンストラクタ
        this.userRepository = repository;
    }
    
    /** 全件を検索して返す */
    public List<User> getUserList(){ //UserでListの型指定をしている
        // リポジトリのfindAllメソッドを呼び出す
        return userRepository.findAll();
    }

}
