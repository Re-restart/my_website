package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Lob // 适合存储长文本
    @Column(columnDefinition = "TEXT")
    private String content;
    private String category;

    // 必须添加无参构造器
    public Article() {}

    // 需要添加setter方法
    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setCategory(String category) { this.category = category; }
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public String getCategory() { return category; }
}
