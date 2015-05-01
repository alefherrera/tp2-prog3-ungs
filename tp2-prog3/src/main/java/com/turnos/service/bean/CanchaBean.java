package com.turnos.service.bean;

import java.util.ArrayList;

import com.turnos.models.domain.Cancha;

public class CanchaBean {
	private Cancha cancha;
	private Boolean[] horarios;
	
	public CanchaBean(Cancha _cancha,Boolean[] _horarios)
	{
		cancha = _cancha;
		horarios = _horarios;
	}
	
	public Boolean[] getHorarios() {
		return horarios;
	}

	public Cancha getCancha() {
		return cancha;
	}
	
	
	
	
}
