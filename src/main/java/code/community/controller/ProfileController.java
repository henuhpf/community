package code.community.controller;

import code.community.dto.PaginationDTO;
import code.community.dto.QuestionDTO;
import code.community.mapper.UserMapper;
import code.community.model.User;
import code.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String getProfile(@PathVariable(name = "action") String action,
                             HttpServletRequest request,
                             Model model,
                             @RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "2") Integer size) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        }
        PaginationDTO paginationDTO = questionService.listByUserId(user, page, size);
        model.addAttribute("pagination", paginationDTO);
        return "profile";
    }
}
