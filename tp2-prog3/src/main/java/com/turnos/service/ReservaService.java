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
import com.turnos.service.bean.HorarioAlternativo;

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
					horarios[reserva.getFecha().getHours() + i] = true;
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

	public List<HorarioAlternativo> horariosAlternativos(Date fecha,
			int idCancha) throws SQLException {
		Persistencia serv = Persistencia.getInstance();
		DateUtilService dateService = DateUtilService.getInstance();
		Reserva filtro = new Reserva();
		filtro.setIdCancha(idCancha);
		filtro.setFecha(fecha);
		List<HorarioAlternativo> ret = new ArrayList<HorarioAlternativo>();
		// verifico si puedo realizar la reserva
		if (getOcupados(filtro).isEmpty()) {
			ret.add(horarioDesdeFiltro(filtro));
			return ret;
		} else {
			// pruebo con cada cancha en el mismo horario
			for (Cancha cancha : canchas) {
				filtro.setIdCancha(cancha.getId());
				ret = llenarResultado(filtro, ret);
			}
			if (ret.size() > 0)
				return ret;
		}
		// busco hasta tener al menos 10 horarios alternativos
		int i = 1;
		while (ret.size() < 10) {
			for (Cancha cancha : canchas) {
				filtro.setIdCancha(cancha.getId());
				filtro.setFecha(dateService.changetHour(fecha, i * -1));
				// verifico si puede relizar la reserva
				ret = llenarResultado(filtro, ret);
				// intento una hora despues de la incial
				filtro.setFecha(dateService.changetHour(fecha, i));
				// verifico si puede relizar la reserva
				ret = llenarResultado(filtro, ret);
			}
			i++;
		}
		return ret;
	}

	private List<HorarioAlternativo> llenarResultado(Reserva filtro,
			List<HorarioAlternativo> ret) throws SQLException {
		if (getOcupados(filtro).isEmpty()) {
			ret.add(horarioDesdeFiltro(filtro));
		}
		return ret;
	}

	private HorarioAlternativo horarioDesdeFiltro(Reserva filtro) {
		return new HorarioAlternativo(filtro.getIdCancha(), filtro.getFecha());
	}

	// TODO: falta contemplar si existe alguna reserva anterior donde se
	// reservaron horas consecutivas
	private List<Reserva> getOcupados(Reserva filtro) throws SQLException {
		return Persistencia.getInstance().getWhere(filtro);
	}

	/*
	 * // Recorre todas las canchas y por cada una de estas saca a las ocupadas
	 * private List<HorarioAlternativo> obtenerAlternativos(Reserva filtro,
	 * List<Reserva> aux) { List<Integer> ids =
	 * aux.stream().map(Reserva::getIdCancha) .collect(Collectors.toList());
	 * return canchas.stream().filter(x -> !ids.contains(x.getId())) .map(y ->
	 * new HorarioAlternativo(y.getId(), filtro.getFecha()))
	 * .collect(Collectors.toList()); }
	 */

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
