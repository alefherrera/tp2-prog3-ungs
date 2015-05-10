package com.turnos.models.domain;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.turnos.enums.ReservaEstado;

@DatabaseTable(tableName = "reserva")
public class Reserva {
	@DatabaseField
	private Date fecha;
	@DatabaseField
	private double pago;
	@DatabaseField
	private int cantHoras;
	@DatabaseField
	private int idCliente;

	@DatabaseField
	private int idCancha;
	@DatabaseField
	private ReservaEstado estado;

	public Reserva() {

	}

	public Reserva(Date fecha) {
		super();
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getPago() {
		return pago;
	}

	public void setPago(double pago) {
		this.pago = pago;
	}

	public int getCantHoras() {
		return cantHoras;
	}

	public void setCantHoras(int cantHoras) {
		this.cantHoras = cantHoras;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdCancha() {
		return idCancha;
	}

	public void setIdCancha(int idCancha) {
		this.idCancha = idCancha;
	}

	public ReservaEstado getEstado() {
		return estado;
	}

	public void setEstado(ReservaEstado estado) {
		this.estado = estado;
	}
}
