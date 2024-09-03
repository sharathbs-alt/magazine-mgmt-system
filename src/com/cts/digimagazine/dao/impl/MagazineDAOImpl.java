package com.cts.digimagazine.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.cts.digimagazine.model.Magazine;
import com.cts.digimagazine.dao.MagazineDAO;

public class MagazineDAOImpl implements MagazineDAO {

	private List<Magazine> magazines = new ArrayList<>();

		@Override
	    public void addMagazine(Magazine magazine) {
	        magazines.add(magazine);
	        System.out.println("Magazine added successfully!");
	    }
		
		@Override
	    public void viewMagazine(Magazine magazine) {
	        if (magazines.isEmpty()) {
	            System.out.println("No magazines found.");
	            return;
	        }
	        for (Magazine m : magazines) {
	            System.out.println(m);
	        }
	    }
		
		@Override
	    public void updateMagazine(Magazine magazine) {
	        for (Magazine m : magazines) {
	            if (m.getMagazineId() == magazine.getMagazineId()) {
	                m.setTitle(magazine.getTitle());
	                m.setGenre(magazine.getGenre());
	                m.setPublicationFrequency(magazine.getPublicationFrequency());
	                m.setPublisher(magazine.getPublisher());
	                System.out.println("Magazine updated successfully!");
	                return;
	            }else {
	            	System.out.println("Magazine with ID " + magazine.getMagazineId() + " not found.");
	            }
	        }        
	    }
		
		@Override
	    public void deleteMagazine(Magazine magazine) {
	        magazines.removeIf(m -> m.getMagazineId() == magazine.getMagazineId());
	        System.out.println("Magazine deleted successfully!");
	    }
		
		//For pre-checks in Services
		@Override
		public Magazine findMagazineById(int id) {
	        for (Magazine m : magazines) {
	            if (m.getMagazineId() == id) {
	                return m; 
	            }
	        }
	        return null;
	    }
}
