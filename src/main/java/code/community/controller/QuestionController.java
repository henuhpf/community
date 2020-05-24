package code.community.controller;

import code.community.dto.QuestionDTO;
import code.community.model.User;
import code.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
