package com.turnos.service.bean;

import java.util.Arrays;

import com.turnos.models.domain.Cancha;
import com.turnos.models.domain.Cliente;

public class ClienteBean {
	private Cliente cliente;
	private int ausente;

	public ClienteBean(Cliente _cliente, int _ausente) {
		cliente = _cliente;
		ausente = _ausente;
	}

	
	public Cliente getCliente() {
		return cliente;
	}



	public int getAusente() {
		return ausente;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(cliente.getNombre() + " ");
		sb.append(ausente);	
		return  sb.toString();
	}

}
