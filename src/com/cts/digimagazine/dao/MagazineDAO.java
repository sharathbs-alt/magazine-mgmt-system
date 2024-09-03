package com.cts.digimagazine.dao;

import java.sql.SQLException;

import com.cts.digimagazine.model.Magazine;

public interface MagazineDAO {
	
	public abstract void addMagazine(Magazine magazine) throws SQLException;
	
	public abstract void viewMagazine(Magazine magazine) throws SQLException;
	
	public abstract void updateMagazine(Magazine magazine) throws SQLException;
	
	public abstract void deleteMagazine(Magazine magazine) throws SQLException;
	
	public abstract Magazine findMagazineById(int id);
}
