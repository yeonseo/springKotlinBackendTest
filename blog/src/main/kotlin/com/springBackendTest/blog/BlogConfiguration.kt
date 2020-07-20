package com.springBackendTest.blog

import com.springBackendTest.blog.account.Account
import com.springBackendTest.blog.account.AccountRole
import com.springBackendTest.blog.account.AccountRepository
import com.springBackendTest.blog.article.Article
import com.springBackendTest.blog.article.ArticleRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {

    @Bean
    fun databaseInitializer(accountRepository: AccountRepository,
                            articleRepository: ArticleRepository) = ApplicationRunner {

        val smaldini = accountRepository.save(Account(
                "smaldini",
                "Stéphane",
                "Maldini",
                null,
                null,
                "admin@test.com",
                "password",
                mutableSetOf(AccountRole.ADMIN, AccountRole.USER)))
        articleRepository.save(Article(
                title = "Reactor Bismuth is out",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = smaldini
        ))
        articleRepository.save(Article(
                title = "Reactor Aluminium has landed",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = smaldini
        ))
        articleRepository.save(Article(
                title = "게시물의 예시 #1",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = smaldini
        ))
        articleRepository.save(Article(
                title = "게시물 #2",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = smaldini
        ))
    }
}