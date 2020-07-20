package com.springBackendTest.blog.account

class AccountDto {
    private var name: String? = null

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
}