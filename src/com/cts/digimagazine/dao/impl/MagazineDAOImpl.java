package com.cts.digimagazine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cts.digimagazine.model.Magazine;
import com.cts.digimagazine.util.DatabaseUtil;
import com.cts.digimagazine.dao.MagazineDAO;

public class MagazineDAOImpl implements MagazineDAO {

	//private List<Magazine> magazines = new ArrayList<>();
	DatabaseUtil dbUtil = new DatabaseUtil();
		@Override
	    public void addMagazine(Magazine magazine) throws SQLException{
	        //magazines.add(magazine);
	        String query = "INSERT INTO Magazine (title, genre, publication_frequency, publisher) VALUES (?, ?, ?, ?)";
	        try (Connection connection = dbUtil.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	             
	            statement.setString(1, magazine.getTitle());
	            statement.setString(2, magazine.getGenre());
	            statement.setString(3, magazine.getPublicationFrequency());
	            statement.setString(4, magazine.getPublisher());

	            int rowsInserted = statement.executeUpdate();
	            if (rowsInserted > 0) {
	                System.out.println("A new magazine was inserted successfully!");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Magazine added successfully!");
	    }
		
		@Override
	    public void viewMagazine(Magazine magazine) throws SQLException {
//	        if (magazines.isEmpty()) {
//	            System.out.println("No magazines found.");
//	            return;
//	        }
	        String query = "SELECT * FROM Magazine";
	        try (Connection connection = dbUtil.getConnection();
		             Statement statement = connection.createStatement();
	        		ResultSet resultSet = statement.executeQuery(query) ){
	        	if(!resultSet.isBeforeFirst()) {
	        		System.out.println("No magazines Found");
	        		return;
	        	}
	        	System.out.println("");
	        	while(resultSet.next()) {
	        		Magazine mag = new Magazine(
	                        resultSet.getString("title"),
	                        resultSet.getString("genre"),
	                        resultSet.getString("publication_frequency"),
	                        resultSet.getString("publisher")
	                    );
	                    mag.setMagazineId(resultSet.getInt("magazine_id"));
	                    
	                    System.out.println(mag);
	        	}
	        }
//	        for (Magazine m : magazines) {
//	            System.out.println(m);
//	        }
	    }
		
		@Override
	    public void updateMagazine(Magazine magazine) throws SQLException {
//	        for (Magazine m : magazines) {
//	            if (m.getMagazineId() == magazine.getMagazineId()) {
//	                m.setTitle(magazine.getTitle());
//	                m.setGenre(magazine.getGenre());
//	                m.setPublicationFrequency(magazine.getPublicationFrequency());
//	                m.setPublisher(magazine.getPublisher());
//	                System.out.println("Magazine updated successfully!");
//	                return;
//	            }else {
//	            	System.out.println("Magazine with ID " + magazine.getMagazineId() + " not found.");
//	            }
//	        }
			if (hasArticles(magazine.getMagazineId())) {
		        throw new RuntimeException("Cannot update magazine with ID " + magazine.getMagazineId() + " because it has associated articles.");
		    }
			String query = "UPDATE Magazine SET title = ?, genre = ?, publication_frequency = ?, publisher = ? WHERE magazine_id = ?";
		    
		    try (Connection connection = dbUtil.getConnection();
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        
		        // Set parameters for the update query
		        statement.setString(1, magazine.getTitle());
		        statement.setString(2, magazine.getGenre());
		        statement.setString(3, magazine.getPublicationFrequency());
		        statement.setString(4, magazine.getPublisher());
		        statement.setInt(5, magazine.getMagazineId());
		        
		        // Execute the update
		        int rowsUpdated = statement.executeUpdate();
		        
		        if (rowsUpdated > 0) {
		            System.out.println("Magazine updated successfully!");
		        } else {
		            System.out.println("Magazine with ID " + magazine.getMagazineId() + " not found.");
		        }
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new RuntimeException("Error updating magazine: " + e.getMessage());
		    }
	    }
		
		//Helper for deletion
		public boolean hasArticles(int magazineId) throws SQLException {
		    String query = "SELECT COUNT(*) FROM Article WHERE magazine_id = ?";
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
	    public void deleteMagazine(Magazine magazine) throws SQLException {
//	        magazines.removeIf(m -> m.getMagazineId() == magazine.getMagazineId());
//	        System.out.println("Magazine deleted successfully!");
			if (hasArticles(magazine.getMagazineId())) {
		        throw new RuntimeException("Cannot delete magazine with ID " + magazine.getMagazineId() + " because it has associated articles.");
		    }
			 String query = "DELETE FROM Magazine WHERE magazine_id = ?";
		        try (Connection connection = dbUtil.getConnection();
		             PreparedStatement statement = connection.prepareStatement(query)) {
		            
		            statement.setInt(1, magazine.getMagazineId());
		            
		            int rowsDeleted = statement.executeUpdate();
		            if (rowsDeleted > 0) {
		                System.out.println("Magazine deleted successfully!");
		            } else {
		                System.out.println("No magazine found with ID " + magazine.getMagazineId());
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		            throw new RuntimeException("Error occurred while deleting magazine: " + e.getMessage());
		        }
	    }
		
		//For pre-checks in Services
		@Override
		public Magazine findMagazineById(int id) {
//	        for (Magazine m : magazines) {
//	            if (m.getMagazineId() == id) {
//	                return m; 
//	            }
//	        }
//	        return null;
			String query = "SELECT * FROM Magazine WHERE magazine_id = ?";
		    try (Connection connection = dbUtil.getConnection();
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        
		        statement.setInt(1, id);
		        
		        try (ResultSet resultSet = statement.executeQuery()) {
		            if (resultSet.next()) {

		                int magazineId = resultSet.getInt("magazine_id");
		                String title = resultSet.getString("title");
		                String genre = resultSet.getString("genre");
		                String publicationFrequency = resultSet.getString("publication_frequency");
		                String publisher = resultSet.getString("publisher");


		                Magazine magazine = new Magazine(title, genre, publicationFrequency, publisher);
		                magazine.setMagazineId(magazineId);
		                return magazine;
		            } else {

		                return null;
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new RuntimeException("Error finding magazine by ID: " + e.getMessage());
		    }
	    }
}
