package webres.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import webres.hibernate.service.UserImplem;
import webres.web.model.User;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FirstController {

    @Autowired
    private UserImplem userImplem;

    @GetMapping("/")
    public String firstPage(ModelMap modelMap){
        modelMap.addAttribute("usersList", returnList());
        return "index";
    }

    @GetMapping("/delete")
    public String secondPage(HttpServletRequest httpServletRequest, ModelMap modelMap){
        Long id = Long.valueOf(httpServletRequest.getParameter("id"));
        System.out.println(id);
        try{
            userImplem.delete(id);
        }catch (IllegalArgumentException e){

        }
        modelMap.addAttribute("usersList", returnList());
        return "index";
    }

    @GetMapping("/add")
    public String thirdPage(HttpServletRequest httpServletRequest, ModelMap modelMap){
        User user = new User(httpServletRequest.getParameter("first_name"),
                httpServletRequest.getParameter("last_name"),
                httpServletRequest.getParameter("email"));
        userImplem.add(user);
        modelMap.addAttribute("usersList", returnList());
        return "index";
    }

    @GetMapping("/update")
    public String fourthPage(HttpServletRequest httpServletRequest, ModelMap modelMap){
        User user = new User(httpServletRequest.getParameter("first_name"),
                httpServletRequest.getParameter("last_name"),
                httpServletRequest.getParameter("email"));
        user.setId(Long.valueOf(httpServletRequest.getParameter("id")));
        try{
            userImplem.update(user);
        }catch (Exception e){

        }
        modelMap.addAttribute("usersList", returnList());
        return "index";
    }

    public List<User> returnList(){
        return userImplem.getListUsers().stream().sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
    }
}
