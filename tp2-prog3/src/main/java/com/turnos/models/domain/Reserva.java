package com.turnos.models.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.turnos.enums.ReservaEstado;
import com.turnos.service.DateUtilService;

@DatabaseTable(tableName = "reserva")
public class Reserva {
	@DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
	private int id;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Date getFechaFin() {
		return DateUtilService.getInstance().changeHour(this.fecha, cantHoras);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reserva ");
		builder.append(padRight("Fecha = "
				+ new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fecha), 30));
		builder.append(padRight("pago = " + pago, 15));
		builder.append(padRight("cantHoras = " + cantHoras, 15));
		builder.append(padRight("estado = " + estado, 15));
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + idCancha;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Reserva))
			return false;
		Reserva other = (Reserva) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (idCancha != other.idCancha)
			return false;
		return true;
	}

	private String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	private String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}

}
