package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.News;
import com.fot.eventsystem.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {

    @Autowired
    private NewsRepository newsRepository;

    // show page
    @GetMapping
    public String showNewsPage() {
        return "admin/manage-news";
    }

    // save news
    @PostMapping("/save")
    public String saveNews(@RequestParam String title,
                           @RequestParam String description,
                           @RequestParam("imageFile") MultipartFile file)
            throws IOException {

        News news = new News();

        String fileName = file.getOriginalFilename();
        String uploadDir = "src/main/resources/static/images/";
        file.transferTo(new File(uploadDir + fileName));

        news.setTitle(title);
        news.setDescription(description);
        news.setImage(fileName);

        newsRepository.save(news);

        return "redirect:/admin/news";
    }
}