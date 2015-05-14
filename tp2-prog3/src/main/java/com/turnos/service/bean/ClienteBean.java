package com.turnos.service.bean;

import com.turnos.models.domain.Cliente;
import com.turnos.service.StringUtilService;

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
		sb.append(StringUtilService.padRight(cliente.getNombre(), 20));
		sb.append(StringUtilService.padRight("Ausencias -> " + ausente, 20));
		return  sb.toString();
	}

}
