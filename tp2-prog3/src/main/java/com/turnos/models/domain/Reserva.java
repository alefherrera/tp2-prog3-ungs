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
	private Cliente cliente;
	@DatabaseField
	private Cancha cancha;
	@DatabaseField
	private ReservaEstado estado;
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
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Cancha getCancha() {
		return cancha;
	}
	public void setCancha(Cancha cancha) {
		this.cancha = cancha;
	}
	public ReservaEstado getEstado() {
		return estado;
	}
	public void setEstado(ReservaEstado estado) {
		this.estado = estado;
	}
}
