package com.cts.digimagazine.service;

import java.util.Scanner;

import com.cts.digimagazine.dao.MagazineDAO;
import com.cts.digimagazine.dao.impl.MagazineDAOImpl;
import com.cts.digimagazine.model.Magazine;

public class MagazineService {
	 private MagazineDAO magazineDAO;

	    public MagazineService() {
	        this.magazineDAO = new MagazineDAOImpl(); 
	    }

	    public void manageMagazines(Scanner scanner) {
	        while (true) {
	            System.out.println("=== Magazine Management ===");
	            System.out.println("1. Add a new magazine");
	            System.out.println("2. View magazine details");
	            System.out.println("3. Update magazine information");
	            System.out.println("4. Delete a magazine");
	            System.out.println("5. Go back to the main menu");
	            System.out.print("Choose an option: ");

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

	    private void addMagazine(Scanner scanner) {
	        System.out.print("Enter Magazine ID: ");
	        int id = scanner.nextInt();
	        scanner.nextLine(); // Consume newline
	        System.out.print("Enter Magazine Title: ");
	        String title = scanner.nextLine();
	        System.out.print("Enter Genre: ");
	        String genre = scanner.nextLine();
	        System.out.print("Enter Publication Frequency: ");
	        String frequency = scanner.nextLine();
	        System.out.print("Enter Publisher: ");
	        String publisher = scanner.nextLine();

	        Magazine magazine = new Magazine(id, title, genre, frequency, publisher);
	        magazineDAO.addMagazine(magazine);
	    }

	    private void viewMagazines() {
	        magazineDAO.viewMagazine(null); // Updated viewMagazine does not require parameters; use DAO's implementation.
	    }

	    private void updateMagazine(Scanner scanner) {
	        System.out.print("Enter Magazine ID to update: ");
	        int id = scanner.nextInt();
	        scanner.nextLine();
	        Magazine existingMagazine = magazineDAO.findMagazineById(id);
	        if (existingMagazine == null) {
	            System.out.println("Magazine with ID " + id + " not found.");
	            return;
	        }
	        
	        Magazine updatedMagazine = new Magazine(id, "", "", "", ""); 
	        System.out.print("Enter new Title: ");
	        updatedMagazine.setTitle(scanner.nextLine());
	        System.out.print("Enter new Genre: ");
	        updatedMagazine.setGenre(scanner.nextLine());
	        System.out.print("Enter new Publication Frequency: ");
	        updatedMagazine.setPublicationFrequency(scanner.nextLine());
	        System.out.print("Enter new Publisher: ");
	        updatedMagazine.setPublisher(scanner.nextLine());

	        magazineDAO.updateMagazine(updatedMagazine);
	    }

	    private void deleteMagazine(Scanner scanner) {
	        System.out.print("Enter Magazine ID to delete: ");
	        int id = scanner.nextInt();
	        scanner.nextLine(); // Consume newline

	        Magazine magazine = new Magazine(id, "", "", "", ""); // Initialize with default empty values
	        magazineDAO.deleteMagazine(magazine);
	    }
}
