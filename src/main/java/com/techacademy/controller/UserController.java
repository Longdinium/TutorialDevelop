package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
    public final UserService service; // この一文はなぜいる？
    
    public UserController(UserService service) { // コンストラクタ
        this.service = service;
    }
    
    /** 一覧画面の表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全体検索の結果をModelに登録
        model.addAttribute( "userlist", service.getUserList() ); // userlistという名前で登録する
        // user/list.htmlに画面遷移
        return "user/list";
    }

}
