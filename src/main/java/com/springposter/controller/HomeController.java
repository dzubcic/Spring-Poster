package com.springposter.controller;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.springposter.entity.Post;
import com.springposter.service.PostService;

@Controller
@ControllerAdvice
public class HomeController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/")
	public String home(Model model){
		model.addAttribute("posts", postService.getSomePosts(0));
		return "home";
	}
	
	@GetMapping("/more={count}")
	public String more(Model model, @PathVariable int count){
		model.addAttribute("posts", postService.getSomePosts(count));
		return "partial";
	}
	
	@PostMapping("/")
	@ResponseBody
	public Object upload(Model model, @RequestParam String title, @RequestParam MultipartFile img) throws IOException{
		
		String imgName = img.getOriginalFilename();
		if(title.equals(null) || img == null || title.length() < 3 || title.length() > 50 
				|| imgName.length() < 3 || imgName.length() > 50)
			return "e1";
		if(!img.getContentType().startsWith("image/"))
			return "e2";
		imgName = postService.checkName(imgName);
		
		Post[] p = new Post[1];
		p[0] = new Post();
		p[0].setTitle(HtmlUtils.htmlEscape(title));
		p[0].setImageName(imgName);
		
		postService.add(p[0]);
		postService.resizeAndSave(img, imgName);
		
		model.addAttribute("posts", p);
		return new ModelAndView("partial");
	}
	
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public String handleError() {
    	return "e3";
    }
    
    @Transactional
    @GetMapping("/remove/{id}")
    public String removeOne(@PathVariable long id){
    	postService.removeOne(id);
    	return "redirect:/";
    }
    
    @GetMapping("/remove")
	public String remove(){
		postService.clear();
		return "redirect:/";
	}

}
