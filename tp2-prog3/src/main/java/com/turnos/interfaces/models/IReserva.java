package com.turnos.interfaces.models;

import java.util.Date;

import com.turnos.enums.ReservaEstado;

public interface IReserva {

	public abstract Date getFecha();

	public abstract void setFecha(Date fecha);

	public abstract double getPago();

	public abstract void setPago(double pago);

	public abstract int getCantHoras();

	public abstract void setCantHoras(int cantHoras);

	public abstract int getIdCliente();

	public abstract void setIdCliente(int idCliente);

	public abstract ReservaEstado getEstado();

	public abstract void setEstado(ReservaEstado estado);

}