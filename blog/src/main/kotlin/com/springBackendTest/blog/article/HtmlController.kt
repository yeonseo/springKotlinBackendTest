package com.springBackendTest.blog.article

import com.springBackendTest.blog.ArticleRepository
import com.springBackendTest.blog.BlogProperties
import com.springBackendTest.blog.UserRepository
import com.springBackendTest.blog.account.Account
import com.springBackendTest.blog.account.AccountRole
import com.springBackendTest.blog.config.format
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class HtmlController(private val repository: ArticleRepository, private val repositoryUser: UserRepository,
                     private val properties: BlogProperties) {

  @GetMapping("/")
  fun blog(model: Model): String {
    model["title"] = "Blog"
    model["banner"] = properties.banner
    model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
    return "blog"
  }

  @GetMapping("/article/{slug}")
  fun article(@PathVariable slug: String, model: Model): String {
    val article = repository
            .findBySlug(slug)
            ?.render()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
    model["title"] = article.title
    model["article"] = article
    return "article"
  }

  @GetMapping("/article/create")
  fun articleCreate(model: Model): String {
    val user = repositoryUser.save(Account(
            "yan",
            "ys",
            "Nam",
            null,
            null,
            "ys@test.com",
            "1234",
            mutableSetOf(AccountRole.USER)))
    val article = Article("yyyy", "yyyyy", "yyyyy", user)
    model["user"] = user
    model["article"] = article
    model["title"] = "create Article"
    // repository.save(article)
    return "articleCreate"
  }

  @PostMapping("/article/create")
  fun create(model: Model, requestArticle : Article, result: BindingResult, request: HttpServletRequest
             , redirect: RedirectAttributes, response: HttpServletResponse) : String {
    model["title"] = "Blog"
    model["banner"] = properties.banner
    model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }

    val article = Article(requestArticle.title, requestArticle.headline
            , requestArticle.content, requestArticle.author)

    var returnUrl = "blog"

    if (result.hasErrors()) {
      returnUrl = "redirect:/article/create"
    } else {
      returnUrl = "redirect:/article/${article.slug}"
      repository.save(article)
    }
    return returnUrl
  }

  // 참고
  //https://medium.com/hacktive-devs/build-restful-web-services-with-spring-boot-and-kotlin-8190bd156722
  //https://blog.mestwin.net/rest-api-with-spring-boot-and-mongodb-using-kotlin/
  @PutMapping("/{slug}/edit")
  fun update(@PathVariable("slug") slug : String, requestArticle : Article): ResponseEntity<Article> {
    val articleID = repository.findBySlug(slug)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
    val updateArticle = repository.save(Article(
            id = articleID.id,
            title = requestArticle.title,
            headline = requestArticle.headline,
            content = requestArticle.content,
            author = requestArticle.author
    ))
    return ResponseEntity.ok(updateArticle)
  }

//  @RequestMapping("/success")
//  fun success(request: HttpServletRequest): String {
//    return "success"
//  }

  fun Article.render() = RenderedArticle(
          slug,
          title,
          headline,
          content,
          author,
          addedAt.format()
  )

  data class RenderedArticle(
          val slug: String,
          val title: String,
          val headline: String,
          val content: String,
          val author: Account,
          val addedAt: String)
}