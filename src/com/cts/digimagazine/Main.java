package com.cts.digimagazine;

import java.util.Scanner;

import com.cts.digimagazine.service.ArticleService;
import com.cts.digimagazine.service.MagazineService;
import com.cts.digimagazine.service.SubscriptionService;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		MagazineService magazineService = new MagazineService();
		ArticleService articleService = new ArticleService();
		SubscriptionService subscriptionService = new SubscriptionService();
		
		while(true) {
			System.out.println("=== Digital Magazine Management System ===");
			System.out.println("1. Magazine Management");
			System.out.println("2. Article Management");
			System.out.println("3. Subscription Management");
			System.out.println("4. Exit");
			System.out.print("Choose an option : ");
			
			int choice = scanner.nextInt();
			switch(choice) {
			case 1: 
				magazineService.manageMagazines(scanner);
				break;
			case 2: 
				articleService.manageArticles(scanner);
				break;
			case 3: 
				subscriptionService.manageSubscriptions(scanner);
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
