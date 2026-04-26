package com.fot.eventsystem.controller;


import com.fot.eventsystem.model.News;
import com.fot.eventsystem.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {

    @Autowired
    private NewsRepository newsRepository;

    // ✅ SHOW PAGE + LOAD DATA
    @GetMapping
    public String showNewsPage(Model model) {
        model.addAttribute("newsList", newsRepository.findAll());
        return "admin/manage-news";
    }

    // ✅ SAVE NEWS
    @PostMapping("/save")
    public String saveNews(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam("imageFile") MultipartFile file
    ) throws IOException {


        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        file.transferTo(new File(uploadDir + fileName));

        News news = new News();
        news.setTitle(title);
        news.setDescription(description);
        news.setImage(fileName);

        newsRepository.save(news);

        return "redirect:/admin/news";
    }
    @GetMapping("/edit/{id}")
    public String editNews(@PathVariable int id, Model model) {
        News news = newsRepository.findById(id).orElse(null);
        model.addAttribute("news", news);
        model.addAttribute("newsList", newsRepository.findAll());
        return "admin/manage-news";
    }

    @PostMapping("/update")
    public String updateNews(
            @RequestParam int id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam("imageFile") MultipartFile file
    ) throws IOException {

        News news = newsRepository.findById(id).orElse(null);

        if (news != null) {

            news.setTitle(title);
            news.setDescription(description);

            // if new image uploaded
            if (!file.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + "/uploads/";

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                file.transferTo(new File(uploadDir + fileName));

                news.setImage(fileName);
            }

            newsRepository.save(news);
        }

        return "redirect:/admin/news";
    }

    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable int id) {
        newsRepository.deleteById(id);
        return "redirect:/admin/news";
    }
}