package com.springBackendTest.blog

import com.springBackendTest.blog.account.Account
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Article(
    var title: String,
    var headline: String,
    var content: String,
    @ManyToOne var author: Account,
    var slug: String = title.toSlug(),
    var addedAt: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null)

//@Entity
//class User(
//    var login: String,
//    var firstname: String,
//    var lastname: String,
//    var description: String? = null,
//    @Id @GeneratedValue var id: Long? = null)