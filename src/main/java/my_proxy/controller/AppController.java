package my_proxy.controller;

import my_proxy.dao.ProxyDAO;
import my_proxy.model.ProxyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppController {

    @Autowired
    private ProxyDAO dao;

    @GetMapping(value = "/tasks")
    public List<ProxyModel> tasks(){
        List<ProxyModel> tasks = dao.getAll();
        return tasks;
    }
}
