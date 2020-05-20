package code.community.controller;

import code.community.mapper.QuestionMapper;
import code.community.mapper.UserMapper;
import code.community.model.Question;
import code.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(Question question, HttpServletRequest request, Model model) {
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        if(question.getTitle() == null || "".equals(question.getTitle())){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(question.getDescription() == null || "".equals(question.getDescription())){
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if(question.getTag() == null || "".equals(question.getTag())){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        Cookie[] cookies = request.getCookies();
        User user = null;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (token != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if(user == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        long gmtCreate = System.currentTimeMillis();
        question.setGmtCreate(gmtCreate);
        question.setGmtModified(gmtCreate);
        question.setCreator(user.getId());
        questionMapper.create(question);
        return "redirect:/";
    }
}
