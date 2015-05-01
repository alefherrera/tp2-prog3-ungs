package com.turnos.interfaces;

public interface ICliente {

	public abstract int getId();

	public abstract void setId(int id);

	public abstract String getNombre();

	public abstract void setNombre(String nombre);

	public abstract int getTelefono();

	public abstract void setTelefono(int telefono);

	public abstract String[] getImagenes();

	public abstract void setImagenes(String[] imagenes);

}