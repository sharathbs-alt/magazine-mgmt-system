package com.cts.digimagazine.dao;

import java.sql.SQLException;

import com.cts.digimagazine.model.Article;

public interface ArticleDAO {
	
	public abstract void addArticle(Article article) throws SQLException;
	
	public abstract void viewArticle(Article article) throws SQLException;
	
	public abstract void updateArticle(Article article) throws SQLException;
	
	public abstract void deleteArticle(Article article) throws SQLException;
	
	public abstract Article findArticleById(int id) ;
}
