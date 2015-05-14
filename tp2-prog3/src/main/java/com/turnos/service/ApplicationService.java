package com.turnos.service;

import java.sql.SQLException;

import com.turnos.models.domain.Cancha;
import com.turnos.models.domain.Cliente;
import com.turnos.models.domain.Reserva;
import com.turnos.persistencia.Persistencia;

public class ApplicationService {

	private static ApplicationService instance;
	
	public static ApplicationService getInstance(){
		if (instance == null)
			instance = new ApplicationService();
		return instance;
	}
	
	private ApplicationService(){
		
	}
	
	public void InitDb(){
		Persistencia service = Persistencia.getInstance();
		try {
			service.createTable(Cliente.class);
			service.createTable(Cancha.class);
			service.createTable(Reserva.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
