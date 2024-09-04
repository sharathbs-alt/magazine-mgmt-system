package com.cts.digimagazine.service;

import java.util.Scanner;
import com.cts.digimagazine.dao.ArticleDAO;
import com.cts.digimagazine.dao.impl.ArticleDAOImpl;
import com.cts.digimagazine.model.Article;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ArticleService {
    private ArticleDAO articleDAO;
    private SimpleDateFormat dateFormat;
    
    public ArticleService() {
        this.articleDAO = new ArticleDAOImpl(); 
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    }

    public void manageArticles(Scanner scanner) {
        while (true) {
            System.out.println("=== Article Management ===");
            System.out.println("1. Add a new article");
            System.out.println("2. View article details");
            System.out.println("3. Update article information");
            System.out.println("4. Delete an article");
            System.out.println("5. Go back to the main menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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

    private void viewArticles() {
    	try {
            articleDAO.viewArticle(null); 
        } catch (SQLException e) {
            throw new RuntimeException("Error viewing articles: " + e.getMessage());
        }

    }

    private void updateArticle(Scanner scanner) {
        System.out.print("Enter Article ID to update: ");
        int articleId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        Article existingArticle = articleDAO.findArticleById(articleId);
        if (existingArticle == null) {
            System.out.println("Article with ID " + articleId + " not found.");
            return;
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
        try {
            articleDAO.updateArticle(updatedArticle);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating article: " + e.getMessage());
        }
    }

    private void deleteArticle(Scanner scanner) {
        System.out.print("Enter Article ID to delete: ");
        int articleId = scanner.nextInt();
        scanner.nextLine(); 

        Article article = new Article(articleId, 0, "", "", "", new Date(0));
        try {
            articleDAO.deleteArticle(article);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting article: " + e.getMessage());
        }
    }
    
    private Date parseDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null; // Return null if the date format is invalid
        }
    }
}
