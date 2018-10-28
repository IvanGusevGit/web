package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.News;
import ru.itmo.webmail.model.repository.AbstractRepository;
import ru.itmo.webmail.model.repository.NewsRepository;

import java.util.List;

public class NewsRepositoryImpl extends AbstractRepository<News> implements NewsRepository {

    @Override
    public void save(News news) {
        super.save(news);
    }

    @Override
    public long newsCount() {
        return countItems();
    }

}
