package com.turnos.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.turnos.models.domain.Cancha;
import com.turnos.models.domain.Reserva;
import com.turnos.service.bean.CanchaBean;

public class ReservaService {
	
	private ReservaService _instance = null;
	private List<Cancha> canchas; 
	private List<Reserva> reservas;
	
	private void CanchaService(){
		
	}
	
	public ReservaService getInstance(){
		if(_instance == null)
			_instance = new ReservaService();
		return _instance;
	}
	
	public List<CanchaBean> ConsultarDisponibles(Date d){
		List<Reserva> reservadas = new ArrayList<Reserva>();
		reservadas  = reservas.stream().filter(x-> x.getFecha() == d).collect(Collectors.toList());
		
		List<CanchaBean> dis = new ArrayList<CanchaBean>();
		
		for (Cancha cancha : canchas) {
			Boolean[] horarios = new Boolean[23];
			List<Reserva> reservaCancha = new ArrayList<Reserva>();
			reservaCancha = reservadas.stream().filter(x-> x.getCancha() == cancha).collect(Collectors.toList());
			
			for (Reserva reserva : reservaCancha) {
				horarios[reserva.getFecha().getHours()] = true;
			}
					
			dis.add(new CanchaBean(cancha, horarios));
			
		}
		
		return dis;
	}
	
	public void reservar(Date d){
		
	}

}
