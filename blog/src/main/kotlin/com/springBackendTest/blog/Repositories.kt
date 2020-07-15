package com.springBackendTest.blog

import com.springBackendTest.blog.account.Account
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
    fun findBySlug(slug: String): Article?
    fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}

//interface UserRepository : CrudRepository<User, Long> {
//    fun findByLogin(login: String): User?
//}

interface UserRepository : CrudRepository<Account, Long> {
    fun findByLogin(login: String): Account?
}