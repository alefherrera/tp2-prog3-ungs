package com.turnos;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.turnos.enums.ReservaEstado;
import com.turnos.models.domain.Cancha;
import com.turnos.models.domain.Cliente;
import com.turnos.models.domain.Reserva;
import com.turnos.persistencia.Persistencia;
import com.turnos.service.ReservaService;
import com.turnos.service.bean.CanchaBean;

public class ReservaTest {

	private Cancha cancha;
	private Cliente cliente;
	
	@Before
	public void GenerarContexto() throws SQLException{
		Persistencia p = Persistencia.getInstance();
		//regenero la bd limpia
		p.dropTable(Cliente.class);
		p.dropTable(Cancha.class);
		p.dropTable(Reserva.class);
		p.createTable(Cliente.class);
		p.createTable(Cancha.class);
		p.createTable(Reserva.class);
		
		Persistencia service = Persistencia.getInstance();
		
		//inserto cancha de prueba
		cancha = new Cancha();
		cancha.setCantMaxima(11);
		cancha.setNombre("canchita");
		cancha.setPrecio(100);
		service.insert(cancha);
		
		//inserto cliente de prueba
		cliente = new Cliente();
		cliente.setNombre("pepe");
		cliente.setTelefono(11112222);
		service.insert(cliente);
		
		//genero reservas para esa cancha y ese cliente
		ReservaService.getInstance().reservar(getFecha(), cliente, cancha, 2, ReservaEstado.SEÑADO, 50);
		
	}


	private Date getFecha() {
		Calendar cal = Calendar.getInstance();
		cal.set(2015, Calendar.MAY, 10, 10, 0, 0);
		return cal.getTime();
	}
	
	
	//• Consultar las canchas disponibles en un dıa.
	@Test
	public void ConsultarDisponiblesTest() throws SQLException{	
		List<CanchaBean> res = ReservaService.getInstance().consultarDisponibles(Calendar.getInstance().getTime());
		System.out.println(res);
	}
	
	//• Realizar reservas de las canchas. Cada reserva se puede hacer con un n´umero de cliente
	//o bien sin ser cliente del complejo (y en este ´ultimo caso se hace con nombre y tel´efono
	//		del responsable), y se debe dejar una se˜na.
	@Test
	public void RealizarReservaTest(){
		
	}
	
	//• Registrar si la reserva se utiliz´o efectivamente.
	@Test
	public void RegistrarEfectividad() throws SQLException{
		Persistencia service = Persistencia.getInstance();
		Reserva r = service.getAll(Reserva.class).get(0);
		r.setEstado(ReservaEstado.EFECTIVO);
		service.insert(r);
	}
	
	//• Permitir reservas de m´as de una hora, sin tener que hacer reservas consecutivas
	@Test
	public void ReservasConcecutivasTest() throws SQLException{
		ReservaService.getInstance().reservar(getFecha(), cliente, cancha, 2, ReservaEstado.SEÑADO, 50);
	}
	
	//• Sugerir horarios alternativos cuando un cliente solicite una reserva y no haya canchas
	//disponibles en ese horario.
	@Test
	public void SugerirHorariosTest(){
		
	}
	
	
}
