package com.cts.digimagazine.service;

import java.util.Scanner;
import com.cts.digimagazine.dao.ArticleDAO;
import com.cts.digimagazine.dao.impl.ArticleDAOImpl;
import com.cts.digimagazine.exceptions.ArticleNotFoundException;
import com.cts.digimagazine.exceptions.MagazineNotFoundException;
import com.cts.digimagazine.model.Article;
import com.cts.digimagazine.util.DatabaseUtil;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ArticleService {
    private ArticleDAO articleDAO;
    private SimpleDateFormat dateFormat;
    
    public ArticleService() {
        this.articleDAO = new ArticleDAOImpl(); 
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.dateFormat.setLenient(false);
    }

    /**
     * Manages the article-related operations by providing a menu interface to the user.
     * @param scanner Scanner object for user input.
     */
    public void manageArticles(Scanner scanner) {
        while (true) {
        	System.out.println("");
            System.out.println("=== Article Management ===");
            System.out.println("1. Add a new article");
            System.out.println("2. View article details");
            System.out.println("3. Update article information");
            System.out.println("4. Delete an article");
            System.out.println("5. Go back to the main menu");
            System.out.println("");
            System.out.print("Choose an option: ");
            System.out.println("");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addArticle(scanner);
                    break;
                case 2:
                    viewArticles();
                    break;
                case 3:
                    updateArticle(scanner);
                    break;
                case 4:
                    deleteArticle(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    /**
     * Adds a new article to the system by taking input from the user.
     * @param scanner Scanner object for user input.
     */
    private void addArticle(Scanner scanner) {
        System.out.print("Enter Magazine ID: ");
        int magazineId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter Article Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine();
        System.out.print("Enter Article Content: ");
        String content = scanner.nextLine();
        System.out.print("Enter Publish Date (DD-MM-YYYY): ");
        String publishDateString = scanner.nextLine();


        Date publishDate = parseDate(publishDateString);
        if (publishDate == null) {
            System.out.println("Invalid date format. Please use DD-MM-YYYY.");
            return;
        }
        
        Article article = new Article(0, magazineId, title, author, content, publishDate);
        try {
            articleDAO.addArticle(article);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding article: " + e.getMessage());
        }
    }

    /**
     * Displays all articles in the system.
     */
    private void viewArticles() {
    	try {
            articleDAO.viewArticle(null); 
        } catch (SQLException e) {
            throw new RuntimeException("Error viewing articles: " + e.getMessage());
        }

    }

    /**
     * Updates an existing article's information based on user input.
     * @param scanner Scanner object for user input.
     */
    private void updateArticle(Scanner scanner) {
        System.out.print("Enter Article ID to update: ");
        int articleId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        try {
        Article existingArticle = articleDAO.findArticleById(articleId);
        if (existingArticle == null) {
//            System.out.println("Article with ID " + articleId + " not found.");
//            return;
        	throw new ArticleNotFoundException("Article with ID " + articleId + " not found.");
        	}

        System.out.print("Enter new Magazine ID: ");
        int magazineId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter new Content: ");
        String content = scanner.nextLine();
        System.out.print("Enter new Publish Date (DD-MM-YYYY): ");
        String publishDateString = scanner.nextLine();


        Date publishDate = parseDate(publishDateString);
        if (publishDate == null) {
            System.out.println("Invalid date format. Please use DD-MM-YYYY.");
            return;
        }

        Article updatedArticle = new Article(articleId, magazineId, title, author, content, publishDate);
            articleDAO.updateArticle(updatedArticle);
        }catch(ArticleNotFoundException e) {
        	System.err.println(e.getMessage());
        }
        catch (SQLException e) {
            throw new RuntimeException("Error updating article: " + e.getMessage());
        }
    }

    /**
     * Deletes an article from the system based on the provided ID.
     * @param scanner Scanner object for user input.
     */
    private void deleteArticle(Scanner scanner) {
        System.out.print("Enter Article ID to delete: ");
        int articleId = scanner.nextInt();
        scanner.nextLine(); 

        try {
            Article existingArticle = articleDAO.findArticleById(articleId);
            if (existingArticle == null) {
//                System.out.println("Article with ID " + articleId + " not found.");
//                return;
            	throw new ArticleNotFoundException("Article with ID " + articleId + " not found.");
            	}
            Article article = new Article(articleId, 0, "", "", "", new Date(0));
            articleDAO.deleteArticle(article);
        } catch(ArticleNotFoundException e) {
        	System.err.println(e.getMessage());
        }catch (SQLException e) {
            throw new RuntimeException("Error deleting article: " + e.getMessage());
        }
    }
    
    /**
     * Parses a date string in the format DD-MM-YYYY.
     * @param dateString The date string to parse.
     * @return The parsed Date object or null if the format is invalid.
     */
    private Date parseDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null; // Return null if the date format is invalid
        }
    }
}
