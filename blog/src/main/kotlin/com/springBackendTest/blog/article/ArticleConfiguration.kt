package com.springBackendTest.blog.article

import com.springBackendTest.blog.account.Account
import com.springBackendTest.blog.account.AccountRole
import com.springBackendTest.blog.account.AccountRepository
import com.springBackendTest.blog.article.Article
import com.springBackendTest.blog.article.ArticleRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@Configuration
class ArticleConfiguration {

    @Bean
    fun databaseInitializer(accountRepository: AccountRepository,
                            articleRepository: ArticleRepository) = ApplicationRunner {

        val smaldini = accountRepository.findByAccount("smaldini")

        smaldini?.let { account ->
            Article(
                    title = "Reactor Bismuth is out",
                    headline = "Lorem ipsum",
                    content = "dolor sit amet",
                    author = account
            )
        }?.let { article -> articleRepository.save(article) }

        smaldini?.let { account ->
            Article(
                    title = "게시물의 예시 #1",
                    headline = "Lorem ipsum",
                    content = "dolor sit amet",
                    author = account
            )
        }?.let { article -> articleRepository.save(article) }

        smaldini?.let { account ->
            Article(
                    title = "게시물 #2",
                    headline = "Lorem ipsum",
                    content = "dolor sit amet",
                    author = account
            )
        }?.let { article -> articleRepository.save(article) }

    }
}