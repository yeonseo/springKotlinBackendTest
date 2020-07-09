package com.springBackendTest.blog

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import sun.rmi.runtime.Log
import javax.servlet.http.HttpServletRequest

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
    val user = repositoryUser.save(User("ysys", "ys", "nam"))
//    val article = Article("yyyy", "yyyyy", "yyyyy", user)
    val article = Article("111", "222", "333", user)
    model["user"] = user
    model["article"] = article
    model["title"] = "create Article"
//    repository.save(article)
    return "articleCreate"
  }

  @PostMapping("/article/create")
  fun create(requestArticle : Article, result: BindingResult) : String {
    val user = repositoryUser.save(User("ysys", "ys", "nam"))
    val article = Article(requestArticle.title, requestArticle.headline
            , requestArticle.content, user)
    article.author = user
    return if (result.hasErrors()) {
      "/article/create"
    } else {
      repository.save(article)
      return "/article/" + article.slug
    }
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
          val author: User,
          val addedAt: String)
}