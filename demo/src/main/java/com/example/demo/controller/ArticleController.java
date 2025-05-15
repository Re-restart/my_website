package com.example.demo.controller;

import com.example.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import com.example.demo.entity.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// 修正2：添加缺失的导入
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/articles") // 修改其他控制器的路径
public class ArticleController {
    private final ResourcePatternResolver resourceResolver;
    private final ArticleRepository articleRepo;

    @Autowired
    public ArticleController(ResourcePatternResolver resourceResolver, ArticleRepository articleRepo) {
        this.resourceResolver = resourceResolver;
        this.articleRepo = articleRepo;
    }

    @GetMapping({"","/"})
    //文章列表页
    public String articles(Model model) {
        try {
            Resource[] resources = resourceResolver.getResources("classpath:markdown/*.md");
            List<Article> articles = new ArrayList<>();
            long idCounter = 1;
            for (Resource res : resources) {
                String filename = res.getFilename(); // xxx.md
                String[] parts = null;
                if (filename != null) {
                    parts = filename.split("_", 2);
                }
                String category = null;
                if (parts != null) {
                    category = parts[0];
                }
                String title = null;
                if (parts != null) {
                    title = parts[1].replace(".md", "");
                }
                Article article = new Article();
                article.setId(idCounter++); // 用假的 ID，实际可用 hash 或 UUID
                article.setTitle(title);
                article.setCategory(category);
                article.setContent(""); // 不在列表中显示内容
                articles.add(article);
            }
            // 保存到数据库
            model.addAttribute("articles", articles);
        } catch (IOException e) {
            model.addAttribute("error", "加载文章列表失败");
        }
        return "articles";
    }


    // 文章详情页（新增@PathVariable）
    @GetMapping("/{category}/{title}")
    public String articleDetails(@PathVariable String category, @PathVariable String title, Model model) throws IOException {
        String fullFilename = category + "_" + title + ".md";
        Resource resource = resourceResolver.getResource("classpath:markdown/" + fullFilename);
        if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "文章不存在");
        }

        String content = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String htmlContent = renderer.render(Parser.builder().build().parse(content));

        Article article = new Article();
        article.setTitle(title);
        article.setCategory(category);
        article.setContent(htmlContent);
        model.addAttribute("article", article);
        return "article-details"; // 文章详情模板
    }


    //文章分类页
    @GetMapping("/category/{category}")
    public String articlesByCategory(@PathVariable String category, Model model) {
        try {
            Resource[] resources = resourceResolver.getResources("classpath:markdown/*.md");
            List<Article> articles = new ArrayList<>();
            long idCounter = 1;
            for (Resource res : resources) {
                String filename = res.getFilename(); // 例如：随笔_生活记录.md
                if (filename != null && filename.contains("_")) {
                    String[] parts = filename.split("_", 2);
                    String fileCategory = parts[0];
                    String title = parts[1].replace(".md", "");

                    // 只保留匹配当前分类的文章
                    if (fileCategory.equalsIgnoreCase(category)) {
                        Article article = new Article();
                        article.setId(idCounter++);
                        article.setTitle(title);
                        article.setCategory(fileCategory);
                        article.setContent(""); // 列表页不显示内容
                        articles.add(article);
                    }
                }
            }

            model.addAttribute("articles", articles);
            model.addAttribute("category", category);
        } catch (IOException e) {
            model.addAttribute("error", "加载文章分类失败");
        }
        return "category-articles"; // templates/category-articles.html
    }


}
