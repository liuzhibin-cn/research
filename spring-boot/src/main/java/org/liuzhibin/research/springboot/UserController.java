package org.liuzhibin.research.springboot;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping({ "/user" })
public class UserController {
    @Autowired
    private UserDao dao;

    @RequestMapping(value = "/create")
    @ResponseBody
    public String create() {
        String name = "test-user-";
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName(name + (i + 1));
            user.setCreateTime(new Date());
            dao.createUser(user);
        }
        return "测试用户创建成功";
    }

    @RequestMapping(value = "/find")
    @ResponseBody
    public String find(@RequestParam(name = "id", required = false) Integer id) {
        Gson gson = new Gson();
        if (id == null || id <= 0)
            return gson.toJson(this.dao.findAll());
        else
            return gson.toJson(this.dao.getUser(id));
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestParam(name = "id", required = false) Integer id) {
        System.out.println(id);
        if (id == null || id <= 0)
            return "成功删除 " + this.dao.deleteAll() + " 个测试用户";
        User user = this.dao.getUser(id);
        if (user == null)
            return "用户 " + id + " 不存在";
        this.dao.deleteUser(id);
        return "用户：" + new Gson().toJson(user) + "，已经删除";
    }
}