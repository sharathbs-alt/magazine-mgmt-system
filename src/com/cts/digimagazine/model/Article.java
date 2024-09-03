package com.cts.digimagazine.model;

import java.util.Date;

public class Article {
    private int articleId;
    private int magazineId;
    private String title;
    private String author;
    private String content;
    private Date publishDate;

    public Article(int articleId, int magazineId, String title, String author, String content, Date publishDate) {
        this.articleId = articleId;
        this.magazineId = magazineId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.publishDate = publishDate;
    }

    // Getters and Setters
    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(int magazineId) {
        this.magazineId = magazineId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "Article [ID=" + articleId + ", Magazine ID=" + magazineId + ", Title=" + title + ", Author=" + author + "]";
    }
}