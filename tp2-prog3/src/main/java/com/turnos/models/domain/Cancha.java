package com.turnos.models.domain;

public class Cancha {
	private int id;
	private String nombre;
	private double precio;
	private int cantMaxima;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getCantMaxima() {
		return cantMaxima;
	}
	public void setCantMaxima(int cantMaxima) {
		this.cantMaxima = cantMaxima;
	}
	
}
