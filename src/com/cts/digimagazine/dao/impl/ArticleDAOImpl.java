package com.cts.digimagazine.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.cts.digimagazine.model.Article;
import com.cts.digimagazine.dao.ArticleDAO;

public class ArticleDAOImpl implements ArticleDAO {

	private List<Article> articles = new ArrayList<>();

		@Override
	    public void addArticle(Article article) {
	        articles.add(article);
	        System.out.println("Article added successfully!");
	    }
	
	    @Override
	    public void viewArticle(Article article) {
	        if (articles.isEmpty()) {
	            System.out.println("No articles found.");
	            return;
	        }
	        for (Article a : articles) {
	            System.out.println(a);
	        }
	    }
		
	    @Override
	    public void updateArticle(Article article) {
	        for (Article a : articles) {
	            if (a.getArticleId() == article.getArticleId()) {
	                a.setMagazineId(article.getMagazineId());
	                a.setTitle(article.getTitle());
	                a.setAuthor(article.getAuthor());
	                a.setContent(article.getContent());
	                a.setPublishDate(article.getPublishDate());
	                System.out.println("Article updated successfully!");
	                return;
	            }
	        }
	        System.out.println("Article with ID " + article.getArticleId() + " not found.");
	    }

	    @Override
	    public void deleteArticle(Article article) {
	        boolean removed = articles.removeIf(a -> a.getArticleId() == article.getArticleId());
	        if (removed) {
	            System.out.println("Article deleted successfully!");
	        } else {
	            System.out.println("Article with ID " + article.getArticleId() + " not found.");
	        }
	    }

	    @Override
	    public Article findArticleById(int id) {
	        for (Article a : articles) {
	            if (a.getArticleId() == id) {
	                return a;
	            }
	        }
	        return null;
	    }
}
