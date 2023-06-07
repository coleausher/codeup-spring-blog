package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/posts")
    public String viewPosts(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "/posts/index";
    }

    @GetMapping("/posts/{id}")
    public String singlePost(@PathVariable long id, Model model) {
        Post post = postDao.findById(id).orElse(null);
        model.addAttribute("post", post);
        return "/posts/show";
    }

    @GetMapping("/posts/create")
    public String showPostForm(Model model) {
        model.addAttribute("users", userDao.findAll());
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String submitNewPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body,
            Model model) {
        User user = userDao.findAll().get(0); // Retrieve the first user from the UserRepository
        Post post = new Post(title, body);
        post.setUser(user);
        postDao.save(post);
        return "redirect:/posts";
    }
}
