package com.example.repository;

import com.example.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {
    List<Article> findByTitle(String title);

    List<Article> findByTitleContains(String title);

    List<Article> findByTitleAndContentContains(String title, String content);

    List<Article> findByIdBetween(Integer startId, Integer endId);

    Page<Article> findByIdBetween(Integer startId, Integer endId, Pageable pageable);

    @Query("from Article ")
    List<Article> selectAll();

    @Query("from Article where id > ?1 and title like %?2%")
    List<Article> selectByCondition(Integer id, String title);

    @Query("from Article where id > :id and title like %:title%")
    List<Article> selectByCondition2(@Param("id") Integer id, @Param("title") String title);

    @Query("from Article where id > ?#{[0].id} and title like %?#{[0].title}%")
    List<Article> selectByEntity(Article article);

    @Query("from Article where id > :#{#article.id} and title like %:#{#article.title}%")
    List<Article> selectByEntity2(Article article);

    @Query(value = "select * from article where id :#{#article.id}", nativeQuery = true)
    List<Article> selectByNativeSql(Article article);

}
