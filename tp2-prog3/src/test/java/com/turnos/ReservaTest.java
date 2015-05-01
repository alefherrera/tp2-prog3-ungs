package com.turnos;

import java.sql.SQLException;
import java.util.Calendar;

import org.junit.Test;

import com.turnos.service.ReservaService;

public class ReservaTest {

	//• Consultar las canchas disponibles en un dıa.
	@Test
	public void ConsultarDisponiblesTest() throws SQLException{
		ReservaService.getInstance().ConsultarDisponibles(Calendar.getInstance().getTime());
	}
	
	//• Realizar reservas de las canchas. Cada reserva se puede hacer con un n´umero de cliente
	//o bien sin ser cliente del complejo (y en este ´ultimo caso se hace con nombre y tel´efono
	//		del responsable), y se debe dejar una se˜na.
	public void RealizarReservaTest(){
		
	}
	
	//• Registrar si la reserva se utiliz´o efectivamente.
	public void RegistrarEfectividad(){
		
	}
	
	//• Permitir reservas de m´as de una hora, sin tener que hacer reservas consecutivas
	public void ReservasConcecutivasTest(){
		
	}
	
	//• Sugerir horarios alternativos cuando un cliente solicite una reserva y no haya canchas
	//disponibles en ese horario.
	public void SugerirHorariosTest(){
		
	}
	
	
}
