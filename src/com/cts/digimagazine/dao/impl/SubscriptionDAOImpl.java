package com.cts.digimagazine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.cts.digimagazine.dao.SubscriptionDAO;
import com.cts.digimagazine.exceptions.InvalidSubscriptionStatusException;
import com.cts.digimagazine.exceptions.MagazineNotFoundException;
import com.cts.digimagazine.exceptions.UserNotFoundException;
import com.cts.digimagazine.model.Subscription;
import com.cts.digimagazine.util.DatabaseUtil;

public class SubscriptionDAOImpl implements SubscriptionDAO {

	private DatabaseUtil dbUtil = new DatabaseUtil();
    private List<Subscription> subscriptions = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    //Checking for user id
    public boolean isUserIdValid(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM User WHERE user_id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }
    //Check for Magazine id
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
    private boolean isValidStatus(String status) {
        return "active".equalsIgnoreCase(status) || "inactive".equalsIgnoreCase(status);
    }
    
    @Override
    public void addSubscription(Subscription subscription) throws SQLException {
    	if (!isUserIdValid(subscription.getUserId())) {
            throw new UserNotFoundException("User with given Id "+ subscription.getUserId()+" doesnt exist " );
        }

        // Check if magazine_id is valid
        if (!isMagazineIdValid(subscription.getMagazineId())) {
            throw new MagazineNotFoundException("Magazine with given Id "+ + subscription.getMagazineId()+" doesnt exist: ");
        }
        
        if (!isValidStatus(subscription.getStatus())) {
            throw new InvalidSubscriptionStatusException("Invalid subscription status provided: " + subscription.getStatus());
        }
        
    	String query = "INSERT INTO Subscription (user_id, magazine_id, subscription_date, expiry_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, subscription.getUserId());
            statement.setInt(2, subscription.getMagazineId());
            statement.setDate(3, new java.sql.Date(subscription.getSubscriptionDate().getTime()));
            statement.setDate(4, new java.sql.Date(subscription.getExpiryDate().getTime()));
            statement.setString(5, subscription.getStatus());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new subscription was created successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding subscription: " + e.getMessage());
        }
    }

    @Override
    public void viewSubscription(Subscription subscription) {
    	String query = "SELECT * FROM Subscription";
        try (Connection connection = dbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
             if(!resultSet.isBeforeFirst()) {
            	 System.out.println("No Subscriptions Found");
            	 return;
             }
             System.out.println("");
            while (resultSet.next()) {
                Subscription sub = new Subscription(
                        resultSet.getInt("subscription_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("magazine_id"),
                        resultSet.getDate("subscription_date"),
                        resultSet.getDate("expiry_date"),
                        resultSet.getString("status")
                );
                System.out.println(sub);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error viewing subscriptions: " + e.getMessage());
        }
    }

    @Override
    public void updateSubscription(Subscription subscription) throws SQLException {
    	if (!isUserIdValid(subscription.getUserId())) {
            throw new RuntimeException("Invalid user_id: " + subscription.getUserId());
        }

        // Check if magazine_id is valid
        if (!isMagazineIdValid(subscription.getMagazineId())) {
            throw new RuntimeException("Invalid magazine_id: " + subscription.getMagazineId());
        }
        if (!isValidStatus(subscription.getStatus())) {
            throw new InvalidSubscriptionStatusException("Invalid subscription status provided: " + subscription.getStatus());
        }
    	String query = "UPDATE Subscription SET user_id = ?, magazine_id = ?, subscription_date = ?, expiry_date = ?, status = ? WHERE subscription_id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, subscription.getUserId());
            statement.setInt(2, subscription.getMagazineId());
            statement.setDate(3, new java.sql.Date(subscription.getSubscriptionDate().getTime()));
            statement.setDate(4, new java.sql.Date(subscription.getExpiryDate().getTime()));
            statement.setString(5, subscription.getStatus());
            statement.setInt(6, subscription.getSubscriptionId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Subscription updated successfully!");
            } else {
                System.out.println("Subscription with ID " + subscription.getSubscriptionId() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating subscription: " + e.getMessage());
        }
    }


    @Override
    public void deleteSubscription(Subscription subscription) throws SQLException {
    	String query = "DELETE FROM Subscription WHERE subscription_id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, subscription.getSubscriptionId());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Subscription deleted successfully!");
            } else {
                System.out.println("No subscription found with ID " + subscription.getSubscriptionId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting subscription: " + e.getMessage());
        }
    }

    @Override
    public Subscription findSubscriptionById(int id) throws SQLException{
    	 String query = "SELECT * FROM Subscription WHERE subscription_id = ?";
         try (Connection connection = dbUtil.getConnection();
              PreparedStatement statement = connection.prepareStatement(query)) {
              
             statement.setInt(1, id);
             try (ResultSet resultSet = statement.executeQuery()) {
                 if (resultSet.next()) {
                     return new Subscription(
                             resultSet.getInt("subscription_id"),
                             resultSet.getInt("user_id"),
                             resultSet.getInt("magazine_id"),
                             resultSet.getDate("subscription_date"),
                             resultSet.getDate("expiry_date"),
                             resultSet.getString("status")
                     );
                 } else {
                     return null;
                 }
             }
         } catch (SQLException e) {
             e.printStackTrace();
             throw new RuntimeException("Error finding subscription by ID: " + e.getMessage());
         }
     	}
    
    public void updateSubscriptionStatus() {
        String query = "UPDATE Subscription " +
                       "SET status = CASE " +
                       "WHEN CURDATE() < subscription_date THEN 'inactive' " +
                       "WHEN CURDATE() BETWEEN subscription_date AND expiry_date THEN 'active' " +
                       "ELSE 'inactive' " +
                       "END";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            int rowsUpdated = statement.executeUpdate();
            System.out.println(rowsUpdated + " subscription statuses updated.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating subscription statuses: " + e.getMessage());
        }
    }
 	}