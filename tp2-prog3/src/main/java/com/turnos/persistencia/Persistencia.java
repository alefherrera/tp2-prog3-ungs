package com.turnos.persistencia;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Persistencia {

	private String databaseUrl = "jdbc:sqlite:turnos.db";
	private ConnectionSource connectionSource;
	private static Persistencia instance;
	
	private Persistencia(){
		
	}
	
	public static Persistencia getInstance(){
		if (instance == null)
			instance = new Persistencia();
		return instance;
	}
	
	public <T> void createTable(Class<T> c) throws SQLException {
		OpenDb();
		TableUtils.createTable(connectionSource, c);		
		closeDb();
	}
	
	public <T> void dropTable(Class<T> c) throws SQLException {
		OpenDb();
		TableUtils.dropTable(connectionSource, c, true);		
		closeDb();
	}
	
	public <T> void insert(T obj) throws SQLException {
		OpenDb();

		Dao<T, String> genericDao = extracted(obj);

		genericDao.create(obj);
		
		closeDb();
	}

	public <T> void update(T obj) throws SQLException {
		OpenDb();

		Dao<T, String> genericDao = extracted(obj);

		genericDao.update(obj);
		
		closeDb();
	}
	
	public <T> void delete(T obj) throws SQLException {
		OpenDb();

		Dao<T, String> genericDao = extracted(obj);

		genericDao.delete(obj);
		
		closeDb();
	}
	
	public <T> List<T> getAll(Class<T> c) throws SQLException {
		OpenDb();
		
		List<T> ret = null;
		Dao<T, String> genericDao = DaoManager.createDao(
				connectionSource,c);
		
		ret = genericDao.queryForAll();
		
		closeDb();

		return ret;
	}
	
	public <T> List<T>getWhere(T obj) throws SQLException{
		OpenDb();
		
		List<T> ret = null;
		Dao<T, String> genericDao = extracted(obj);
		
		ret = genericDao.queryForMatching(obj);
		
		closeDb();

		return ret;
	}
	
	private void closeDb() throws SQLException {
		connectionSource.close();
	}

	private void OpenDb() throws SQLException {
		connectionSource = new JdbcConnectionSource(databaseUrl);
	}

	@SuppressWarnings("unchecked")
	private <T> Dao<T, String> extracted(T obj) throws SQLException {
		return (Dao<T, String>) DaoManager.createDao(
				connectionSource, obj.getClass());
	}
}
