package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Park;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.PostCategories;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostCategoriesRepository;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postsDao;
    private final UserRepository userDao;
    private final PostCategoriesRepository catDao;

    public PostController(PostRepository postsDao, UserRepository userDao, PostCategoriesRepository catDao) {
        this.postsDao = postsDao;
        this.userDao = userDao;
        this.catDao = catDao;
    }

    @GetMapping("/posts")

    public String viewPosts(Model model) {
        model.addAttribute("posts", postsDao.findAll());
        return "posts/index";
    }


    @GetMapping("/posts/{id}")
    public String singlePost(@PathVariable long id, Model model) {
        model.addAttribute("post", postsDao.findById(id).get());
        return "posts/show";
    }


    @GetMapping("/posts/create")
    public String showPostForm(Model model) {
        // show categories in form
        model.addAttribute("categories", catDao.findAll());
        //// Send a new Post object to the form, so we can find the inputs to the fields
        model.addAttribute("post", new Post());
        return "/posts/create";
    }



    //EDIT HINT FOR EXERCISE
//    @GetMapping("/parks/{id}/edit")
//    public String showEditParkForm(@PathVariable Long id, Model model) {}
    //1. Find the park ussing the PathVariable
    //2. Use the model to send the park object to the form
    //3. return the template for the edit.html
    @GetMapping("/posts/{id}/edit")
    public String showEditPostForm(@PathVariable long id, Model model) {
        Post post = postsDao.findById(id).get();
        model.addAttribute("post", post);
        return "/posts/edit";
    }
    @PostMapping("/posts/{id}/edit")
    public String updatePost(@ModelAttribute Post post, @PathVariable long id) {
        post.setId(id);
        postsDao.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/create")
//    @RequestMapping(path = "/posts/create", method = RequestMethod.POST)
    public String submitNewPost(@ModelAttribute Post post) {
//        Post post = new Post(title, body);
//
//        List<PostCategories> categories = new ArrayList<>();
////
//        for(long categoryId : categoryIds) {
//            categories.add(catDao.findById(categoryId).get());
//        }

        User user = userDao.findById(1L).get();
        post.setUser(user);
//        post.setCategories(categories);
        postsDao.save(post);
        return "redirect:/posts";
    }
}