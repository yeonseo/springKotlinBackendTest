package com.springBackendTest.blog.config

import com.springBackendTest.blog.account.Account
import com.springBackendTest.blog.account.AccountRole
import com.springBackendTest.blog.account.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class BeanConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun applicationRunner(): ApplicationRunner {
        return object : ApplicationRunner {

            @Autowired
            lateinit var accountService: AccountService

            @Throws(Exception::class)
            override fun run(args: ApplicationArguments) {
                val admin = Account(
                        "smaldini",
                        "Stéphane",
                        "Maldini",
                        null,
                        null,
                        "admin@test.com",
                        "password",
                        mutableSetOf(AccountRole.ADMIN, AccountRole.USER))
                accountService.saveAccount(admin)
                val testUser = Account(
                        "yan",
                        "ys",
                        "Nam",
                        null,
                        null,
                        "ysnam@test.com",
                        "1234",
                        mutableSetOf(AccountRole.USER))
                accountService.saveAccount(testUser)
            }
        }
    }
}