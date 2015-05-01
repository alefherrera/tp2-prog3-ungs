package com.turnos.models.domain;

import com.turnos.interfaces.models.ICliente;

public class Cliente implements ICliente {
	private int id;
	private String nombre;
	private int telefono;
	private String[] imagenes;
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICliente#getId()
	 */
	public int getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICliente#setId(int)
	 */
	public void setId(int id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICliente#getNombre()
	 */
	public String getNombre() {
		return nombre;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICliente#setNombre(java.lang.String)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICliente#getTelefono()
	 */
	public int getTelefono() {
		return telefono;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICliente#setTelefono(int)
	 */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICliente#getImagenes()
	 */
	public String[] getImagenes() {
		return imagenes;
	}
	/* (non-Javadoc)
	 * @see com.turnos.models.domain.ICliente#setImagenes(java.lang.String[])
	 */
	public void setImagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
	
}
