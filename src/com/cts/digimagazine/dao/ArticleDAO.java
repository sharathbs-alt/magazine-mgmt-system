package com.cts.digimagazine.dao;

import com.cts.digimagazine.model.Article;

public interface ArticleDAO {
	
	public abstract void addArticle(Article article);
	
	public abstract void viewArticle(Article article);
	
	public abstract void updateArticle(Article article);
	
	public abstract void deleteArticle(Article article);
	
	public abstract Article findArticleById(int id);
}
