package com.turnos.interfaces;

public interface ICancha {

	public abstract int getId();

	public abstract void setId(int id);

	public abstract String getNombre();

	public abstract void setNombre(String nombre);

	public abstract double getPrecio();

	public abstract void setPrecio(double precio);

	public abstract int getCantMaxima();

	public abstract void setCantMaxima(int cantMaxima);

}