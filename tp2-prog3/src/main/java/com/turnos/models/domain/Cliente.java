package com.turnos.models.domain;

public class Cliente  {
	private int id;
	private String nombre;
	private int telefono;
	private String[] imagenes;
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
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String[] getImagenes() {
		return imagenes;
	}
	public void setImagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
}
