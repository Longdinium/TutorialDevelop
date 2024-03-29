package com.techacademy.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.User;
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

    // ---Ch7追加ここから---
    /** User登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute User user) {
        // Modelにインスタンスを登録
            // @Model~を使うことで、model.addAttribute("user", user); を省略？
        // User登録画面に遷移
        return "user/register";
    }

    // ----- 変更ここから -----
    /** User登録処理 */
    @PostMapping("/register")
    public String postRegister(@Validated User user, BindingResult res, Model model) {
        if(res.hasErrors()) {
            // エラーあり
            return getRegister(user);
        }
        // User登録
        service.saveUser(user);
        // 一覧画面にリダイレクト
        return "redirect:/user/list";
    }
    // ----- 変更ここまで -----


    // ---Ch8追加ここから---
    /** User更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getUser(User user, @PathVariable("id") Integer id, Model model) {
        if(id != null) {
            // idがnullでないとき -> サービスから取得した値をModelに登録
            model.addAttribute( "user", service.getUser(id) );
                // サービスで定義したgetUserの結果をuserという名前でModelに登録
            // User更新画面に遷移
        } else {
            // idがnullのとき（postUserから遷移したとき）
            // -> postUserから渡されたuserの値をModelに登録
            model.addAttribute("user", user);
        }
        return "user/update";
        
    }

    /** User更新処理 */
    @PostMapping("/update/{id}/")
    public String postUser(@Validated User user, BindingResult res, Model model) {
        if(res.hasErrors()) {
            // エラーありの場合
            return getUser(user, null, model);
        }
        // User登録
        service.saveUser(user);
        // 一覧画面にリダイレクト
        return "redirect:/user/list";
    }
    // ---Ch8追加ここまで---


    // ---Ch9追加ここから---
    /** User削除処理 */
    @PostMapping(path="list", params="deleteRun") // なぜ"/~"でなくpath="~"なんだ？
    public String deleteRun(@RequestParam(name="idck") Set<Integer> idck, Model model) {
        // Userを一括削除
        service.deleteUser(idck);
        // 一覧画面にリダイレクト
        return "redirect:/user/list";
    }


}
