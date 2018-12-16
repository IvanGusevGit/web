package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.ArticleRepository;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class ArticleService {

    private ArticleRepository articleRepository = new ArticleRepositoryImpl();
    private UserRepository userRepository = new UserRepositoryImpl();


    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<ExtendedArticle> findAllExtended() {
        List<Article> articles = findAll();
        List<ExtendedArticle> extendedArticles = new ArrayList<>();
        for (Article article : articles) {
            if (!article.isHidden()) {
                extendedArticles.add(new ExtendedArticle(article));
            }
        }
        return extendedArticles;
    }

    public List<Article> findByUserId(long userId) {
        return articleRepository.findByUserId(userId);
    }


    public void setHidden(long userId, boolean value) {
        articleRepository.setHidden(userId, value);
    }


    public void validate(Article article) throws ValidationException {
        if (article.getTitle().isEmpty()) {
            throw new ValidationException("Title is required to be not empty");
        } else if (article.getTitle().length() > 255) {
            throw new ValidationException("Title is required to be of 255 symbols or less");
        } else if (article.getText().isEmpty()) {
            throw new ValidationException("Content is required to be not empty");
        }
    }

    public void save(Article article) {
        articleRepository.save(article);
    }


    public class ExtendedArticle extends Article {

        private String author;

        public ExtendedArticle(Article article) {
            setId(article.getId());
            setUserId(article.getUserId());
            setTitle(article.getTitle());
            setText(article.getText());
            setCreationTime(article.getCreationTime());
            setHidden(article.isHidden());
            setAuthor(userRepository.find(article.getUserId()).getLogin());
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String userName) {
            this.author = userName;
        }

    }

}
