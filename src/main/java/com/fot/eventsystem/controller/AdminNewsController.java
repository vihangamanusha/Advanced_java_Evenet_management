package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.News;
import com.fot.eventsystem.service.NewsService;
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
    private NewsService newsService;

    // SHOW PAGE
    @GetMapping
    public String showNewsPage(Model model) {

        try {
            model.addAttribute("newsList", newsService.getAllNews());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to load news");
        }

        return "admin/manage-news";
    }

    // SAVE NEWS
    @PostMapping("/save")
    public String saveNews(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam("imageFile") MultipartFile file
    ) {

        try {
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

            newsService.saveNews(news);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/news";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String editNews(@PathVariable int id, Model model) {

        try {
            model.addAttribute("news", newsService.getNewsById(id));
            model.addAttribute("newsList", newsService.getAllNews());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to load news for edit");
        }

        return "admin/manage-news";
    }

    // UPDATE
    @PostMapping("/update")
    public String updateNews(
            @RequestParam int id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam("imageFile") MultipartFile file
    ) {

        try {
            News news = newsService.getNewsById(id);

            if (news != null) {

                news.setTitle(title);
                news.setDescription(description);

                if (!file.isEmpty()) {
                    String uploadDir = System.getProperty("user.dir") + "/uploads/";
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadDir + fileName));
                    news.setImage(fileName);
                }

                newsService.saveNews(news);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/news";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable int id) {

        try {
            newsService.deleteNews(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/news";
    }
}