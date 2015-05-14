package com.turnos.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.turnos.enums.ReservaEstado;
import com.turnos.exceptions.ReservaException;
import com.turnos.models.domain.Cancha;
import com.turnos.models.domain.Cliente;
import com.turnos.models.domain.Reserva;
import com.turnos.persistencia.Persistencia;
import com.turnos.service.bean.CanchaBean;
import com.turnos.service.bean.ClienteBean;
import com.turnos.service.bean.HorarioAlternativo;

public class ReservaService {

	private static ReservaService _instance = null;
	private List<Cancha> canchas;
	private List<Reserva> reservas;
	private List<Cliente> clientes;

	private ReservaService() throws SQLException {
		canchas = Persistencia.getInstance().getAll(Cancha.class);
		if (canchas == null)
			canchas = new ArrayList<Cancha>();
		reservas = Persistencia.getInstance().getAll(Reserva.class);
		if (reservas == null)
			reservas = new ArrayList<Reserva>();
		clientes = Persistencia.getInstance().getAll(Cliente.class);
		if (clientes == null)
			clientes = new ArrayList<Cliente>();
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
	
	public List<Reserva> getReservas(){
		return reservas;
	}
	
	public List<Reserva> getReservasBy(Predicate<Reserva> condicion){
		return reservas.stream().filter(condicion).collect(Collectors.toList());
	}

	public List<HorarioAlternativo> horariosAlternativos(Date fecha,
			int idCancha) throws SQLException {
		// Persistencia serv = Persistencia.getInstance();
		DateUtilService dateService = DateUtilService.getInstance();
		List<HorarioAlternativo> ret = new ArrayList<HorarioAlternativo>();
		// verifico si puedo realizar la reserva
		if (getOcupados(fecha, idCancha).isEmpty()) {
			ret.add(new HorarioAlternativo(idCancha, fecha));
			return ret;
		}
		// busco hasta tener al menos 10 horarios alternativos
		int i = 0;
		while (ret.size() < 10) {
			for (Cancha cancha : canchas) {
				// verifico si puede relizar la reserva
				ret = IntentarAgregar(dateService.changeHour(fecha, i),
						cancha.getId(), ret);
				// intento una hora despues de la incial
				Date aux = dateService.changeHour(fecha, i * -1);
				// dejo de buscar para atras cuando el horario es menor al
				// actual
				if (i != 0 && aux.compareTo(dateService.now()) != -1)
					// verifico si puede relizar la reserva
					ret = IntentarAgregar(aux, cancha.getId(), ret);
			}
			i++;
		}
		return ret;
	}

	private List<HorarioAlternativo> IntentarAgregar(Date fecha, int idCancha,
			List<HorarioAlternativo> ret) throws SQLException {
		if (getOcupados(fecha, idCancha).isEmpty()) {
			ret.add(new HorarioAlternativo(idCancha, fecha));
		}
		return ret;
	}

	private List<Reserva> getOcupados(Date fecha, int idCancha)
			throws SQLException {

		List<Reserva> ret = new ArrayList<Reserva>();
		for (Reserva reserva : reservas) {
			if (reserva.getIdCancha() == idCancha
					&& fecha.compareTo(reserva.getFecha()) > -1
					&& fecha.compareTo(reserva.getFechaFin()) < 0)
				ret.add(reserva);
		}

		return ret;
	}



	public void reservar(Date dia, Cliente cliente, Cancha cancha,
			int cantidadHoras, ReservaEstado estado, double pago)
			throws SQLException, ReservaException {
		Reserva reserva = new Reserva();
		reserva.setFecha(dia);
		reserva.setIdCliente(cliente.getId());
		reserva.setCantHoras(cantidadHoras);
		reserva.setIdCancha(cancha.getId());
		reserva.setEstado(estado);
		reserva.setPago(pago);
		reservar(reserva);
	}

	public void reservar(Reserva reserva) throws SQLException, ReservaException {
		if (getOcupados(reserva.getFecha(), reserva.getIdCancha()).size() != 0)
			throw new ReservaException();		
		Persistencia.getInstance().insert(reserva);
		reservas.add(reserva);
	}
	
	public void updateReserva(Reserva reserva) throws SQLException{
		Persistencia.getInstance().update(reserva);
		reservas.set(reservas.indexOf(reserva), reserva); 
	}

	public List<ClienteBean> clientesNoCumplidores() {
		List<Reserva> reservasAusentes = new ArrayList<Reserva>();
		reservasAusentes = reservas.stream()
				.filter(x -> x.getEstado() == ReservaEstado.AUSENTE)
				.collect(Collectors.toList());

		List<ClienteBean> aus = new ArrayList<ClienteBean>();

		for (Cliente cliente : clientes) {

			int cantAus = reservasAusentes.stream()
					.filter(x -> x.getIdCliente() == cliente.getId())
					.collect(Collectors.toList()).size();

			aus.add(new ClienteBean(cliente, cantAus));
		}

		Comparator<ClienteBean> CantAusente = (e1, e2) -> Integer.compare(
				e1.getAusente(), e2.getAusente());

		aus = aus.stream().sorted(CantAusente).collect(Collectors.toList());

		return aus;
	}

}
