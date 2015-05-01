package com.turnos.models.domain;

import java.util.Date;

import com.turnos.enums.ReservaEstado;
import com.turnos.models.interfaces.IReserva;

public class Reserva implements IReserva {
	private Date fecha;
	private double pago;
	private int cantHoras;
	private int idCliente;
	private ReservaEstado estado;
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.IReserva#getFecha()
	 */
	public Date getFecha() {
		return fecha;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.IReserva#setFecha(java.util.Date)
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.IReserva#getPago()
	 */
	public double getPago() {
		return pago;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.IReserva#setPago(double)
	 */
	public void setPago(double pago) {
		this.pago = pago;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.IReserva#getCantHoras()
	 */
	public int getCantHoras() {
		return cantHoras;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.IReserva#setCantHoras(int)
	 */
	public void setCantHoras(int cantHoras) {
		this.cantHoras = cantHoras;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.IReserva#getIdCliente()
	 */
	public int getIdCliente() {
		return idCliente;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.IReserva#setIdCliente(int)
	 */
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.IReserva#getEstado()
	 */
	public ReservaEstado getEstado() {
		return estado;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.IReserva#setEstado(com.turnos.enums.ReservaEstado)
	 */
	public void setEstado(ReservaEstado estado) {
		this.estado = estado;
	}
	
}
