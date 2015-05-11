package com.turnos.service.bean;

import java.util.Date;

import com.turnos.models.domain.Cancha;

public class HorarioAlternativo {

	private int idCancha;
	private Date horario;

	public HorarioAlternativo(int idCancha, Date horario) {
		super();
		this.idCancha = idCancha;
		this.horario = horario;
	}

	public int getIdCancha() {
		return idCancha;
	}

	public void setIdCancha(int idCancha) {
		this.idCancha = idCancha;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("idCancha: " + idCancha);
		sb.append(" | ");
		sb.append("horario: " + horario);
		return sb.toString();
	}
	
}
