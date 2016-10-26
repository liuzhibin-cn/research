package org.liuzhibin.research.springboot;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        if (id == null || id <= 0)
            return this.htmlFormat(this.dao.findAll());
        else
            return this.htmlFormat(this.dao.getUser(id));
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestParam(name = "id", required = false) Integer id) {
        System.out.println(id);
        if (id == null || id <= 0) {
            this.dao.deleteAll();
            return "成功删除了所有测试用户";
        }
        User user = this.dao.getUser(id);
        if (user == null)
            return "用户 " + id + " 不存在";
        this.dao.deleteUser(id);
        return "用户：" + this.htmlFormat(user) + "，已经删除";
    }

    private String htmlFormat(Object obj) {
        if (obj == null)
            return "null";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();
        return gson.toJson(obj).replaceAll(" ", "&nbsp;&nbsp;").replaceAll("\n", "<br />");
    }
}