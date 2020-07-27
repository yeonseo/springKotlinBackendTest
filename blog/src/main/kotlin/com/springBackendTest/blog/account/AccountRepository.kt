package com.springBackendTest.blog.account

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AccountRepository: JpaRepository<Account, Long> {
    fun findByEmail(email: String): Account?
    fun findByLogin(login: String): Account?

    @Query("SELECT f FROM Account f WHERE LOWER(f.id) = LOWER(:id)")
    fun findByAccount(@Param("id") login: String?): Account?
}