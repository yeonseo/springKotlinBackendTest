package com.springBackendTest.blog

import com.springBackendTest.blog.account.Account
import com.springBackendTest.blog.article.Article
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
    fun findBySlug(slug: String): Article?
    fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}