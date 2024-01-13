package com.techacademy.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    

    // Ch8にて追加
    /** Userを一件検索して返す */
    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }
    // Ch8追加ここまで
    
   
    // Chapter7にて追加 
    /** Userの登録を行う */
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
        // JpaRepositoryから定義済みメソッドsaveを呼び出す
    }
    // ch7の追加はここまで
    
    
    // Ch9追加
    /** Userの削除を行う */
    @Transactional
    public void deleteUser(Set<Integer> idck) {
        for(Integer id : idck) {
            userRepository.deleteById(id);
        }
    }
    // Ch9追加ここまで

}
