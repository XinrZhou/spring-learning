package com.example;

import com.example.pojo.Article;
import com.example.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJpa {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void testSave() {
        Article article = new Article();
        article.setTitle("测试文章标题");
        article.setContent("测试文章内容");
        article.setCreateTime(new Date());
        articleRepository.save(article);
    }

    @Test
    public void testSaveBatch() {
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < 30; i ++) {
            Article article = new Article();
            article.setTitle("测试文章标题11");
            article.setContent("测试文章内容111");
            article.setCreateTime(new Date());
            list.add(article);
        }
        articleRepository.saveAll(list);
    }

    @Test
    public void testGet() {
        Article article = articleRepository.findById(1).get();
        System.out.println(article);
    }

    @Test
    public void testFindPage() {
        // 第一个参数接收的是页数，从0开始
        Pageable pageable = PageRequest.of(3, 10);
        Page<Article> page = articleRepository.findAll(pageable);
        System.out.println("总条数" +  page.getTotalElements());
        System.out.println("总页数" +  page.getTotalPages());
        System.out.println("数据：");
        List<Article> list = page.getContent();
        for (Article article : list) {
            System.out.println(article);
        }
    }

    @Test
    public void testFindPageSort() {
        int pageNumber = 0;
        int paseSize = 10;
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(pageNumber, paseSize, sort);
        Page<Article> page = articleRepository.findAll(pageable);
        System.out.println("总条数" +  page.getTotalElements());
        System.out.println("总页数" +  page.getTotalPages());
        System.out.println("数据：");
        List<Article> list = page.getContent();
        for (Article article : list) {
            System.out.println(article);
        }
    }

    @Test
    public void testFindByTitle() {
        List<Article> articleList = articleRepository.findByTitle("测试文章标题");
        System.out.println(articleList);
    }

    @Test
    public void testFindByTitleContains() {
        List<Article> articleList = articleRepository.findByTitleContains("测试文章标题");
        System.out.println(articleList);
    }

    @Test
    public void testFindBetween() {
        List<Article> articleList = articleRepository.findByIdBetween(10, 20);
        System.out.println(articleList);
    }

    @Test
    public void testSelectAll() {
        List<Article> articleList = articleRepository.selectAll();
        System.out.println(articleList);
    }

    @Test
    public void testSelectByCondition() {
        List<Article> articleList = articleRepository.selectByCondition(10, "测试文章标题11");
        System.out.println(articleList);
    }
}
