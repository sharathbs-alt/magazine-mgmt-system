package com.cts.digimagazine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;

import com.cts.digimagazine.model.Article;
import com.cts.digimagazine.util.DatabaseUtil;
import com.cts.digimagazine.dao.ArticleDAO;
import com.cts.digimagazine.exceptions.InvalidSubscriptionStatusException;
import com.cts.digimagazine.exceptions.MagazineNotFoundException;
import com.cts.digimagazine.exceptions.UserNotFoundException;

public class ArticleDAOImpl implements ArticleDAO {

	private DatabaseUtil dbUtil = new DatabaseUtil();
	//private List<Article> articles = new ArrayList<>();

	//Helper for checking if the corresponding magazine exist or not
		public boolean isMagazineIdValid(int magazineId) throws SQLException {
		    String query = "SELECT COUNT(*) FROM Magazine WHERE magazine_id = ?";
		    try (Connection connection = dbUtil.getConnection();
		         PreparedStatement statement = connection.prepareStatement(query)) {
	
		        statement.setInt(1, magazineId);
		        ResultSet resultSet = statement.executeQuery();
	
		        if (resultSet.next()) {
		            int count = resultSet.getInt(1);
		            return count > 0;
		        }
		    }
		    return false;
		}
		@Override
	    public void addArticle(Article article) throws SQLException {
			String query = "INSERT INTO Article (magazine_id, title, author, content, publish_date) VALUES (?, ?, ?, ?, ?)";
	        try (Connection connection = dbUtil.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	        	
	        	if (!isMagazineIdValid(article.getMagazineId())) {
	        		throw new MagazineNotFoundException("Invalid magazine_id: " + article.getMagazineId());
	        	}
	        	
	        	statement.setInt(1, article.getMagazineId());
	            statement.setString(2, article.getTitle());
	            statement.setString(3, article.getAuthor());
	            statement.setString(4, article.getContent());
	            statement.setDate(5, new java.sql.Date(article.getPublishDate().getTime()));

	            int rowsInserted = statement.executeUpdate();
	            if (rowsInserted > 0) {
	                System.out.println("Article added successfully!");
	            }
	        }
	        catch (MagazineNotFoundException e) {
	            System.err.println(e.getMessage());
	        }catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error adding article: " + e.getMessage());
	        }
	    }
	
	    @Override
	    public void viewArticle(Article article) throws SQLException {
	    	String query = "SELECT * FROM Article";
	        try (Connection connection = dbUtil.getConnection();
	             Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {

	        	if(!resultSet.isBeforeFirst()) {
	        		System.out.println("No articles Found");
	        		return;
	        	}
	        	System.out.println("");
	            while (resultSet.next()) {
	                Article a = new Article(
	                        resultSet.getInt("article_id"),
	                        resultSet.getInt("magazine_id"),
	                        resultSet.getString("title"),
	                        resultSet.getString("author"),
	                        resultSet.getString("content"),
	                        resultSet.getDate("publish_date")
	                );
	                
	                System.out.println(a);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error viewing articles: " + e.getMessage());
	        }
	    }
		
	    @Override
	    public void updateArticle(Article article) throws SQLException{
	    	String query = "UPDATE Article SET magazine_id = ?, title = ?, author = ?, content = ?, publish_date = ? WHERE article_id = ?";
	        try (Connection connection = dbUtil.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	        	if (!isMagazineIdValid(article.getMagazineId())) {
	        		throw new MagazineNotFoundException("Invalid magazine_id: " + article.getMagazineId());
	        	}
	        	statement.setInt(1, article.getMagazineId());
	            statement.setString(2, article.getTitle());
	            statement.setString(3, article.getAuthor());
	            statement.setString(4, article.getContent());
	            statement.setDate(5, new java.sql.Date(article.getPublishDate().getTime()));
	            statement.setInt(6, article.getArticleId());

	            int rowsUpdated = statement.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("Article updated successfully!");
	            } else {
	                System.out.println("Article with ID " + article.getArticleId() + " not found.");
	            }
	        } 
	        catch (MagazineNotFoundException e) {
	            System.err.println(e.getMessage());
	        }catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error updating article: " + e.getMessage());
	        }
	    }

	    @Override
	    public void deleteArticle(Article article) throws SQLException{
	    	 String query = "DELETE FROM Article WHERE article_id = ?";
	         try (Connection connection = dbUtil.getConnection();
	              PreparedStatement statement = connection.prepareStatement(query)) {

	             statement.setInt(1, article.getArticleId());
	             
	             int rowsDeleted = statement.executeUpdate();
	             if (rowsDeleted > 0) {
	                 System.out.println("Article deleted successfully!");
	             } else {
	                 System.out.println("No article found with ID " + article.getArticleId());
	             }
	         } catch (SQLException e) {
	             e.printStackTrace();
	             throw new RuntimeException("Error deleting article: " + e.getMessage());
	         }
	    }

	    @Override
	    public Article findArticleById(int id) {
	    	 String query = "SELECT * FROM Article WHERE article_id = ?";
	         try (Connection connection = dbUtil.getConnection();
	              PreparedStatement statement = connection.prepareStatement(query)) {

	             statement.setInt(1, id);
	             try (ResultSet resultSet = statement.executeQuery()) {
	                 if (resultSet.next()) {
	                     return new Article(
	                             resultSet.getInt("article_id"),
	                             resultSet.getInt("magazine_id"),
	                             resultSet.getString("title"),
	                             resultSet.getString("author"),
	                             resultSet.getString("content"),
	                             resultSet.getDate("publish_date")
	                     );
	                 }
	             }
	         } catch (SQLException e) {
	             throw new RuntimeException("Error finding article by ID: " + e.getMessage());
	         }
	         return null;    
	    }
}
