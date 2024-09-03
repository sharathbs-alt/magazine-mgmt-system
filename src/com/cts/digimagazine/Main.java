package com.cts.digimagazine;

import java.util.Scanner;

import com.cts.digimagazine.service.MagazineService;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		MagazineService magazineService = new MagazineService();
		
		while(true) {
			System.out.println("=== Digital Magazine Management System ===");
			System.out.println("1. Magazine Management");
			System.out.println("4. Exit");
			System.out.print("Choose an option : ");
			
			int choice = scanner.nextInt();
			switch(choice) {
			case 1: 
				magazineService.manageMagazines(scanner);
				break;
			case 4: 
				System.out.println("Exiting the application . . .");
				scanner.close();
				return;
			default : 
				System.out.println("Invalid Choice. . .");
				break;
			}
		}
	}

}
