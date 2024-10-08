package com.cts.digimagazine.service;

import java.util.Scanner;

import com.cts.digimagazine.dao.MagazineDAO;
import com.cts.digimagazine.dao.impl.MagazineDAOImpl;
import com.cts.digimagazine.exceptions.MagazineNotFoundException;
import com.cts.digimagazine.model.Magazine;

public class MagazineService {
	
	 private MagazineDAO magazineDAO;

	    public MagazineService() {
	        this.magazineDAO = new MagazineDAOImpl(); 
	    }

	    /**
	     * Manages magazine-related operations by providing a menu interface to the user.
	     * @param scanner Scanner object for user input.
	     */
	    public void manageMagazines(Scanner scanner) {
	        while (true) {
	        	System.out.println("");
	            System.out.println("=== Magazine Management ===");
	            System.out.println("1. Add a new magazine");
	            System.out.println("2. View magazine details");
	            System.out.println("3. Update magazine information");
	            System.out.println("4. Delete a magazine");
	            System.out.println("5. Go back to the main menu");
	            System.out.println("");
	            System.out.print("Choose an option: ");
	            System.out.println("");

	            int choice = scanner.nextInt();
	            scanner.nextLine(); // Consume newline

	            switch (choice) {
	                case 1:
	                    addMagazine(scanner);
	                    break;
	                case 2:
	                    viewMagazines();
	                    break;
	                case 3:
	                    updateMagazine(scanner);
	                    break;
	                case 4:
	                    deleteMagazine(scanner);
	                    break;
	                case 5:
	                    return;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }

	    /**
	     * Adds a new magazine to the system based on user input.
	     * @param scanner Scanner object for user input.
	     */
	    private void addMagazine(Scanner scanner){
	    	
	        System.out.print("Enter Magazine Title: ");
	        String title = scanner.nextLine();
	        System.out.print("Enter Genre: ");
	        String genre = scanner.nextLine();
	        System.out.print("Enter Publication Frequency: ");
	        String frequency = scanner.nextLine();
	        System.out.print("Enter Publisher: ");
	        String publisher = scanner.nextLine();

	        Magazine magazine = new Magazine( title, genre, frequency, publisher);
	        try {
	        	magazineDAO.addMagazine(magazine);
	        }catch(Exception e) {
	        	throw new RuntimeException("Error adding the magazine" +e);
	        }
	    }

	    /**
	     * Displays all magazines in the system.
	     */
	    private void viewMagazines() {
	    	try {
	    		
	    		magazineDAO.viewMagazine(null); // Updated viewMagazine does not require parameters; use DAO's implementation.
	    	}catch(Exception e) {
	    		throw new RuntimeException("Coudlnt fetch the data" +e);
	    	}
	    }

	    /**
	     * Updates an existing magazine's information based on user input.
	     * @param scanner Scanner object for user input.
	     */
	    private void updateMagazine(Scanner scanner) {
	    	 System.out.print("Enter Magazine ID to update: ");
	    	    int id = scanner.nextInt();
	    	    scanner.nextLine(); 
	    	    try {
		    	    Magazine existingMagazine = magazineDAO.findMagazineById(id);
		    	    if (existingMagazine == null) {
		    	        throw new MagazineNotFoundException("Magazine Not Found");
		    	    }
	
		    	    Magazine updatedMagazine = new Magazine(id,"", "", "", "");
		    	    System.out.print("Enter new Title: ");
		    	    updatedMagazine.setTitle(scanner.nextLine());
		    	    System.out.print("Enter new Genre: ");
		    	    updatedMagazine.setGenre(scanner.nextLine());
		    	    System.out.print("Enter new Publication Frequency: ");
		    	    updatedMagazine.setPublicationFrequency(scanner.nextLine());
		    	    System.out.print("Enter new Publisher: ");
		    	    updatedMagazine.setPublisher(scanner.nextLine());

	    	        magazineDAO.updateMagazine(updatedMagazine);
	    	    	}catch(MagazineNotFoundException e) {
	    	    		System.err.println(e.getMessage());
	    	    	}
	    	    	catch (Exception e) {
	    	        throw new RuntimeException("Operation couldn't be performed: " + e.getMessage());
	    	    	}
	    	}

	    /**
	     * Deletes a magazine from the system based on the provided ID.
	     * @param scanner Scanner object for user input.
	     */
	    private void deleteMagazine(Scanner scanner) {
	        System.out.print("Enter Magazine ID to delete: ");
	        int id = scanner.nextInt();
	        scanner.nextLine(); // Consume newline

	        Magazine magazine = new Magazine( id,"", "", "", ""); // Initialize with default empty values
	        try {	
			magazineDAO.deleteMagazine(magazine);
	        }catch(Exception e) {
	        	throw new RuntimeException("Operation couldnt be performed"+e);
	        }
	    }
}
