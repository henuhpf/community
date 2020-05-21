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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QuestionService questionService;

	@GetMapping("/")
	public String index(HttpServletRequest request, Model model) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null || cookies.length == 0) {
			return "index";
		}
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("token")){
				String token = cookie.getValue();
				User user = userMapper.findByToken(token);
				if (token != null) {
					request.getSession().setAttribute("user", user);
				}
				break;
			}
		}
		List<QuestionDTO> questionList = questionService.list();
		model.addAttribute("questions", questionList);
		return "index";
	}


}