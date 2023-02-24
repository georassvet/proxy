package my_proxy.controller;

import my_proxy.dao.GroupDAO;
import my_proxy.dao.ProxyDAO;
import my_proxy.dao.ProxyTestDAO;
import my_proxy.form.*;
import my_proxy.model.GroupModel;
import my_proxy.model.ProxyModel;
import my_proxy.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private ProxyDAO proxyDAO;
    @Autowired
    private ProxyTestDAO proxyTestDAO;
    @Autowired
    private GroupDAO groupDAO;
    @Autowired
    private ProxyService service;

    @GetMapping(value = {"/proxy/{id}"})
    public String editProxy(Model model, @PathVariable("id") int id){
        ProxyModel item = proxyDAO.getById(id);
        model.addAttribute("form", new ProxyForm(item));
        model.addAttribute("groups", groupDAO.getAll());
        return "proxy";
    }


    @PostMapping(value = {"/proxy"})
    public String addProxy(Model model, ProxyForm form){
        if(form.getId()==0){
            proxyDAO.add(form);
        }else{
            proxyDAO.update(form);
        }
        return "redirect:details/" + form.getGroupId();
    }
    @GetMapping(value = {"/","/index", "/group"})
    public String addGroup(Model model){
        model.addAttribute("form", new GroupForm());
        model.addAttribute("items", groupDAO.getAll());
        return "group";
    }
    @GetMapping(value = {"/group/{id}"})
    public String editGroup(Model model, @PathVariable("id") int id){
        GroupModel item = groupDAO.getById(id);
        model.addAttribute("form", new DelayForm(item));
        model.addAttribute("items", groupDAO.getAll());
        return "group";
    }
    @DeleteMapping(value = {"/group/{id}"})
    public String group(Model model, @PathVariable("id") int id){

        model.addAttribute("items", proxyDAO.getByGroupId(id));
        return "details";
    }
    @GetMapping(value = {"/details/{id}"})
    public String groupDetails(Model model, @PathVariable("id") int id){
        List<ProxyModel> items = proxyDAO.getByGroupId(id);
        model.addAttribute("form", new ProxyForm(id));
        model.addAttribute("deleteForm", new DeleteForm());
        model.addAttribute("items", items);
        return "details";
    }
    @PostMapping(value = {"/proxy/delete"})
    public String deleteProxy(Model model, DeleteForm deleteForm){
        proxyDAO.delete(deleteForm.getId());
        return "redirect:/details/" + deleteForm.getGroupId();
    }
    @GetMapping(value = {"/tasks/{id}"})
    public String addTasks(Model model, @PathVariable("id") int id){
        model.addAttribute("form", new DelayForm(id));
        model.addAttribute("items", proxyDAO.getByGroupId(id));
        model.addAttribute("groupId", id);
        model.addAttribute("tasks", service.getDelayTasks(id));
        return "tasks";
    }
    @PostMapping(value = {"/tasks/{id}"})
    public String addTasks(Model model, @PathVariable("id") int id, DelayForm form){
        service.createTasks(form);
        return "redirect:/tasks/"+id;
    }
    @PostMapping(value = {"/tasks/{id}/delete"})
    public String deleteTasks(Model model, @PathVariable("id") int id, DelayForm form){
        service.createTasks(form);
        return "redirect:/tasks/"+id;
    }
    @PostMapping(value = {"/group"})
    public String addGroup(Model model, GroupForm form){
        if(form.getId()==0){
            groupDAO.add(form);
        }else{
            groupDAO.update(form);
        }
        return "redirect:group";
    }
}
