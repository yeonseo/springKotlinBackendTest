package com.springBackendTest.blog.account

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/view")
class AccountController {
    @RequestMapping("/success")
    fun success(request: HttpServletRequest, model: Model): String {
        return "success"
    }
}