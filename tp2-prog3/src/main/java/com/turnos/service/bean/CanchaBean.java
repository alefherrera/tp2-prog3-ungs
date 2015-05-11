package com.turnos.service.bean;

import java.util.Arrays;

import com.turnos.models.domain.Cancha;

public class CanchaBean {
	private Cancha cancha;
	private boolean[] horarios;

	public CanchaBean(Cancha _cancha, boolean[] _horarios) {
		cancha = _cancha;
		horarios = _horarios;
	}

	public boolean[] getHorarios() {
		return horarios;
	}

	public Cancha getCancha() {
		return cancha;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(cancha.getNombre() + " -> ");
		for (int i = 0; i < horarios.length; i++) {
			sb.append(horarios[i] ? "" : " " + i);
		}		
		return  sb.toString();
	}

}
