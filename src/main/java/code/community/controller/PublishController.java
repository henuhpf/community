package code.community.controller;

import code.community.dto.QuestionDTO;
import code.community.mapper.QuestionMapper;
import code.community.mapper.UserMapper;
import code.community.model.Question;
import code.community.model.User;
import code.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    /**
     * 发布问题
     */
    @PostMapping("/publish")
    public String doPublish(Question question, HttpServletRequest request, Model model) {
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
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
        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        long gmtCreate = System.currentTimeMillis();
        question.setGmtCreate(gmtCreate);
        question.setGmtModified(gmtCreate);
        question.setCreator(user.getId());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

    /**
     * 编辑已发布的问题
     */
    @GetMapping("/publish/{id}")
    public String editPublish(@PathVariable("id") Integer id, Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        return "publish";
    }
}
