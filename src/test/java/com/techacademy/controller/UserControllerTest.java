package com.techacademy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;

    private final WebApplicationContext webApplicationContext;

    UserControllerTest(WebApplicationContext context) {
        this.webApplicationContext = context;
    }

    @BeforeEach
    void beforeEach() {
        // @BeforeEachで各テスト前にSpring Securityを有効にする
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }
    
    
    @Test
    @DisplayName("User一覧画面")
    @WithMockUser
    void testGetList() throws Exception {
        // このthrows Exception（スロー宣言？）は何故いる？
        
        MvcResult resultlist = mockMvc.perform(get ("/user/list") ) // 一覧画面にアクセス
           .andExpect(status().isOk() )
               // HTTPリクエストに対するレスポンスのステータスが「200 OK」であること
           .andExpect(model().attributeExists("userlist"))
               // viewに渡しているModelにuserが登録されていること
           .andExpect(model().hasNoErrors()) // Modelにエラーがないこと
           .andExpect(view().name("user/list")) // viewの名称が user/list であること
           .andReturn(); // 内容の取得

        // userlistの検証
        // そのためにModelからuserlistを取り出す
        User userlist = (User) resultlist.getModelAndView().getModel().get("userlist");
        // assertEquals(userlist.size, 3); // 件数が3件であること
        assertEquals(userlist.getId(), 1);
        assertEquals(userlist.getName(), "キラメキ太郎");
        assertEquals(userlist.getId(), 2);
        assertEquals(userlist.getName(), "キラメキ次郎");
        assertEquals(userlist.getId(), 3);
        assertEquals(userlist.getName(), "キラメキ花子");
    }

    @Test
    @DisplayName("User更新画面")
    @WithMockUser
    void testGetUser() throws Exception {
        // HTTPリクエストに対するレスポンスの検証
        MvcResult result = mockMvc.perform(get("/user/update/1/")) // URLにアクセス
            .andExpect(status().isOk()) // ステータスを確認
            .andExpect(model().attributeExists("user")) // Modelの内容を確認
            .andExpect(model().hasNoErrors()) // Modelのエラー有無の確認
            .andExpect(view().name("user/update")) // viewの確認
            .andReturn(); // 内容の取得

        // userの検証
        // Modelからuserを取り出す
        User user = (User)result.getModelAndView().getModel().get("user");
        assertEquals(user.getId(), 1);
        assertEquals(user.getName(), "キラメキ太郎");
    }
    
}