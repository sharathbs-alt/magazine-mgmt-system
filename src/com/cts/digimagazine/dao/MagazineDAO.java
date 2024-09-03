package com.cts.digimagazine.dao;

import com.cts.digimagazine.model.Magazine;

public interface MagazineDAO {
	
	public abstract void addMagazine(Magazine magazine);
	
	public abstract void viewMagazine(Magazine magazine);
	
	public abstract void updateMagazine(Magazine magazine);
	
	public abstract void deleteMagazine(Magazine magazine);
	
	public abstract Magazine findMagazineById(int id);
}
