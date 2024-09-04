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
import com.cts.digimagazine.exceptions.SubscriptionNotFoundException;
import com.cts.digimagazine.model.Subscription;

public class SubscriptionService {
    private SubscriptionDAO subscriptionDAO;
    private SimpleDateFormat dateFormat;

    public SubscriptionService() {
        this.subscriptionDAO = new SubscriptionDAOImpl();
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.dateFormat.setLenient(false);
    }

    /**
     * Manages subscription-related operations by providing a menu interface to the user.
     * @param scanner Scanner object for user input.
     */
    public void manageSubscriptions(Scanner scanner) {
        while (true) {
        	System.out.println("");
            System.out.println("=== Subscription Management ===");
            System.out.println("1. Add a new subscription");
            System.out.println("2. View subscription details");
            System.out.println("3. Update subscription information");
            System.out.println("4. Delete a subscription");
            System.out.println("5. Go back to the main menu");
            System.out.println("");
            System.out.print("Choose an option: ");
            System.out.println("");

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

    /**
     * Adds a new subscription to the system based on user input.
     * @param scanner Scanner object for user input.
     */
    
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
        System.out.print("Enter Status (Active/Inactive): ");
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

    /**
     * Displays all subscriptions in the system.
     */
    private void viewSubscriptions() {
    	 try {
             subscriptionDAO.viewSubscription(null);
         } catch (SQLException e) {
             throw new RuntimeException("Error viewing subscriptions: " + e.getMessage());
         }
    }

    /**
     * Updates an existing subscription's information based on user input.
     * @param scanner Scanner object for user input.
     */
    private void updateSubscription(Scanner scanner) {
        System.out.print("Enter Subscription ID to update: ");
        int subscriptionId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        Subscription existingSubscription = null;
        try {
            existingSubscription = subscriptionDAO.findSubscriptionById(subscriptionId);
        
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
       
            subscriptionDAO.updateSubscription(updatedSubscription);
        } catch (SubscriptionNotFoundException e) {
            throw new SubscriptionNotFoundException("Error finding subscription by ID: " + e.getMessage());
        }
         catch (SQLException e) {
            throw new RuntimeException("Error updating subscription: " + e.getMessage());
        }
    }

    /**
     * Deletes a subscription from the system based on the provided ID.
     * @param scanner Scanner object for user input.
     */
    private void deleteSubscription(Scanner scanner) {
        System.out.print("Enter Subscription ID to delete: ");
        int subscriptionId = scanner.nextInt();
        scanner.nextLine(); 
        try {
        	
        Subscription subscription = new Subscription(subscriptionId, 0, 0, new Date(), new Date(), ""); // Initialize with default empty values
            subscriptionDAO.deleteSubscription(subscription);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting subscription: " + e.getMessage());
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
    
    /**
     * Schedules periodic updates for subscription statuses using a fixed-rate scheduler.
     */
    public void scheduleStatusUpdate() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            subscriptionDAO.updateSubscriptionStatus();
        }, 0, 1, TimeUnit.DAYS);
    }
}