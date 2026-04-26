package com.fot.eventsystem.service;

import com.fot.eventsystem.model.News;
import com.fot.eventsystem.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    // GET ALL NEWS
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    // SAVE NEWS
    public void saveNews(News news) {
        newsRepository.save(news);
    }

    // FIND BY ID
    public News getNewsById(int id) {
        return newsRepository.findById(id).orElse(null);
    }

    // DELETE
    public void deleteNews(int id) {
        newsRepository.deleteById(id);
    }
}