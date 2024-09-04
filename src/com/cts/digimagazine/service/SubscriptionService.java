package com.cts.digimagazine.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cts.digimagazine.dao.SubscriptionDAO;
import com.cts.digimagazine.dao.impl.SubscriptionDAOImpl;
import com.cts.digimagazine.model.Subscription;

public class SubscriptionService {
    private SubscriptionDAO subscriptionDAO;
    private SimpleDateFormat dateFormat;

    public SubscriptionService() {
        this.subscriptionDAO = new SubscriptionDAOImpl();
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    }

    public void manageSubscriptions(Scanner scanner) {
        while (true) {
            System.out.println("=== Subscription Management ===");
            System.out.println("1. Add a new subscription");
            System.out.println("2. View subscription details");
            System.out.println("3. Update subscription information");
            System.out.println("4. Delete a subscription");
            System.out.println("5. Go back to the main menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addSubscription(scanner);
                    break;
                case 2:
                    viewSubscriptions();
                    break;
                case 3:
                    updateSubscription(scanner);
                    break;
                case 4:
                    deleteSubscription(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addSubscription(Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter Magazine ID: ");
        int magazineId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Subscription Date (DD-MM-YYYY): ");
        String subscriptionDateString = scanner.nextLine();
        System.out.print("Enter Expiry Date (DD-MM-YYYY): ");
        String expiryDateString = scanner.nextLine();
        System.out.print("Enter Status: ");
        String status = scanner.nextLine();
        
        // Parse dates using SimpleDateFormat
        Date subscriptionDate = parseDate(subscriptionDateString);
        Date expiryDate = parseDate(expiryDateString);
        if (subscriptionDate == null || expiryDate == null) {
            System.out.println("Invalid date format. Please use DD-MM-YYYY.");
            return;
        }

        Subscription subscription = new Subscription(0, userId, magazineId, subscriptionDate, expiryDate, status);
        try {
            subscriptionDAO.addSubscription(subscription);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding subscription: " + e.getMessage());
        }
    }

    private void viewSubscriptions() {
    	 try {
             subscriptionDAO.viewSubscription(null);
         } catch (SQLException e) {
             throw new RuntimeException("Error viewing subscriptions: " + e.getMessage());
         }
    }

    private void updateSubscription(Scanner scanner) {
        System.out.print("Enter Subscription ID to update: ");
        int subscriptionId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        Subscription existingSubscription = null;
        try {
            existingSubscription = subscriptionDAO.findSubscriptionById(subscriptionId);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding subscription by ID: " + e.getMessage());
        }
        if (existingSubscription == null) {
            System.out.println("Subscription with ID " + subscriptionId + " not found.");
            return;
        }

        System.out.print("Enter new User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter new Magazine ID: ");
        int magazineId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter new Subscription Date (DD-MM-YYYY): ");
        String subscriptionDateString = scanner.nextLine();
        System.out.print("Enter new Expiry Date (DD-MM-YYYY): ");
        String expiryDateString = scanner.nextLine();
        System.out.print("Enter new Status: ");
        String status = scanner.nextLine();
        
        // Parse dates using SimpleDateFormat
        Date subscriptionDate = parseDate(subscriptionDateString);
        Date expiryDate = parseDate(expiryDateString);
        if (subscriptionDate == null || expiryDate == null) {
            System.out.println("Invalid date format. Please use DD-MM-YYYY.");
            return;
        }

        Subscription updatedSubscription = new Subscription(subscriptionId, userId, magazineId, subscriptionDate, expiryDate, status);
        try {
            subscriptionDAO.updateSubscription(updatedSubscription);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating subscription: " + e.getMessage());
        }
    }

    private void deleteSubscription(Scanner scanner) {
        System.out.print("Enter Subscription ID to delete: ");
        int subscriptionId = scanner.nextInt();
        scanner.nextLine(); 

        Subscription subscription = new Subscription(subscriptionId, 0, 0, new Date(), new Date(), ""); // Initialize with default empty values
        try {
            subscriptionDAO.deleteSubscription(subscription);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting subscription: " + e.getMessage());
        }
    }

    // Helper method to parse a date string
    private Date parseDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null; // Return null if the date format is invalid
        }
    }
    
 // Method to schedule periodic updates
    public void scheduleStatusUpdate() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            subscriptionDAO.updateSubscriptionStatus();
        }, 0, 1, TimeUnit.DAYS); // Runs every day
    }
}