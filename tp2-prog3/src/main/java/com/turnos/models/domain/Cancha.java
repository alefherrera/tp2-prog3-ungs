package com.turnos.models.domain;

import com.turnos.models.interfaces.ICancha;

public class Cancha implements ICancha {
	private int id;
	private String nombre;
	private double precio;
	private int cantMaxima;
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICancha#getId()
	 */
	public int getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICancha#setId(int)
	 */
	public void setId(int id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICancha#getNombre()
	 */
	public String getNombre() {
		return nombre;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICancha#setNombre(java.lang.String)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICancha#getPrecio()
	 */
	public double getPrecio() {
		return precio;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICancha#setPrecio(double)
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICancha#getCantMaxima()
	 */
	public int getCantMaxima() {
		return cantMaxima;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICancha#setCantMaxima(int)
	 */
	public void setCantMaxima(int cantMaxima) {
		this.cantMaxima = cantMaxima;
	}
	
}
