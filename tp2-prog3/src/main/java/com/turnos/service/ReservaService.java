package com.turnos.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.turnos.enums.ReservaEstado;
import com.turnos.models.domain.Cancha;
import com.turnos.models.domain.Cliente;
import com.turnos.models.domain.Reserva;
import com.turnos.persistencia.Persistencia;
import com.turnos.service.bean.CanchaBean;

public class ReservaService {

	private static ReservaService _instance = null;
	private List<Cancha> canchas;
	private List<Reserva> reservas;

	private ReservaService() throws SQLException {
		canchas = Persistencia.getInstance().getAll(Cancha.class);
		if (canchas == null)
			canchas = new ArrayList<Cancha>();
		reservas = Persistencia.getInstance().getAll(Reserva.class);
		if (reservas == null)
			reservas = new ArrayList<Reserva>();

	}

	public static ReservaService getInstance() throws SQLException {
		if (_instance == null)
			_instance = new ReservaService();
		return _instance;
	}

	public List<CanchaBean> filtrar(Date d, Predicate<Reserva> condicion) {
		List<Reserva> reservadas = new ArrayList<Reserva>();
		reservadas = reservas.stream().filter(condicion)
				.collect(Collectors.toList());

		List<CanchaBean> dis = new ArrayList<CanchaBean>();

		for (Cancha cancha : canchas) {
			boolean[] horarios = new boolean[23];
			List<Reserva> reservaCancha = new ArrayList<Reserva>();
			reservaCancha = reservadas.stream()
					.filter(x -> x.getIdCancha() == cancha.getId())
					.collect(Collectors.toList());

			for (Reserva reserva : reservaCancha) {
				for (int i = 0; i < reserva.getCantHoras(); i++) {
					horarios[reserva.getFecha().getHours()+i] = true;
				}				
			}

			dis.add(new CanchaBean(cancha, horarios));

		}

		return dis;
	}

	public List<CanchaBean> consultarDisponibles(Date dia) {
		DateUtilService serv = DateUtilService.getInstance();
		return filtrar(dia, x -> serv.compareOnlyDate(x.getFecha(), dia));
	}

	public List<CanchaBean> horariosAlternativos(Date dia) {
		return filtrar(dia, x -> x.getFecha() == dia
				&& x.getFecha().getHours() == dia.getHours());
	}

	public void reservar(Date dia, Cliente cliente, Cancha cancha,
			int cantidadHoras, ReservaEstado estado, double pago)
			throws SQLException {
		Reserva reserva = new Reserva();
		reserva.setFecha(dia);
		reserva.setIdCliente(cliente.getId());
		reserva.setCantHoras(cantidadHoras);
		reserva.setIdCancha(cancha.getId());
		reserva.setEstado(estado);
		reserva.setPago(pago);
		Persistencia.getInstance().insert(reserva);
		reservas.add(reserva);
	}

}
