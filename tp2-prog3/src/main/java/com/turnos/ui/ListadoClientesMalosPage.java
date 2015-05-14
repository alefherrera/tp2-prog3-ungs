package com.turnos.ui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import com.turnos.enums.ReservaEstado;
import com.turnos.models.domain.Reserva;
import com.turnos.service.DateUtilService;
import com.turnos.service.ReservaService;

public class ListadoClientesMalosPage extends JFrame {

	class GenericListModel<T> implements ListModel<T> {

		private List<T> mList;
		private ListDataListener listener;
		private T selectedItem;

		public GenericListModel(List<T> lista) {
			mList = lista;
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			listener = l;
		}

		@Override
		public T getElementAt(int index) {
			return mList.get(index);
		}

		@Override
		public int getSize() {
			return mList.size();
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			listener = null;
		}

	}

	private JPanel contentPane;
	private static final long serialVersionUID = 1L;
	private UtilDateModel modelDesde;
	private JDatePanelImpl datePanelDesde;
	private JDatePickerImpl dpDesde;
	private UtilDateModel modelHasta;
	private JDatePanelImpl datePanelHasta;
	private JDatePickerImpl dpHasta;

	private JLabel lblDesde;
	private JList<Reserva> lstReservas;
	private JPanel panel;
	private JLabel lblStatus;
	private JLabel lblHasta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListadoClientesMalosPage frame = new ListadoClientesMalosPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ListadoClientesMalosPage() {
		setResizable(false);
		setTitle("Listado Reservas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		modelDesde = new UtilDateModel();
		modelDesde.setDate(Calendar.getInstance().get(Calendar.YEAR), Calendar
				.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		modelDesde.setSelected(true);
		datePanelDesde = new JDatePanelImpl(modelDesde);
		dpDesde = new JDatePickerImpl(datePanelDesde);
		dpDesde.setBounds(122, 11, 134, 29);
		SpringLayout springLayout = (SpringLayout) dpDesde.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH,
				dpDesde.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				dpDesde);
		dpDesde.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateListaReservas();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(dpDesde);

		lblDesde = new JLabel("Desde");
		lblDesde.setBounds(10, 11, 102, 23);
		contentPane.add(lblDesde);

		modelHasta = new UtilDateModel();
		modelHasta.setDate(Calendar.getInstance().get(Calendar.YEAR), Calendar
				.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		modelHasta.setSelected(true);
		datePanelHasta = new JDatePanelImpl(modelHasta);
		dpHasta = new JDatePickerImpl(datePanelHasta);
		dpHasta.setBounds(384, 11, 150, 29);
		SpringLayout springLayout2 = (SpringLayout) dpHasta.getLayout();
		springLayout2.putConstraint(SpringLayout.SOUTH,
				dpHasta.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				dpHasta);
		dpHasta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateListaReservas();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(dpHasta);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 524, 354);
		contentPane.add(scrollPane);

		lstReservas = new JList<Reserva>();
		try {
			lstReservas.setModel(new GenericListModel<Reserva>(ReservaService
					.getInstance().getReservas()));
		} catch (SQLException e1) {
			lstReservas.setModel(new GenericListModel<Reserva>(
					new ArrayList<Reserva>()));
			e1.printStackTrace();
		}
		scrollPane.setViewportView(lstReservas);

		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
				null));
		panel.setBounds(0, 416, this.getWidth() - 5, 31);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		lblStatus = new JLabel("");
		panel.add(lblStatus);

		lblHasta = new JLabel("Hasta");
		lblHasta.setBounds(272, 11, 102, 23);
		contentPane.add(lblHasta);

	}

	private void updateMessage(String msg) {
		lblStatus.setText(msg);
	}

	private void updateReservas(ReservaEstado nuevoEstado) {
		List<Reserva> listaReservas = lstReservas.getSelectedValuesList();

		for (Reserva reserva : listaReservas) {
			reserva.setEstado(nuevoEstado);
			try {
				ReservaService.getInstance().updateReserva(reserva);
			} catch (SQLException e) {
				System.out
						.println("La siguiente reserva no se pudo actualizar: "
								+ reserva.toString());
				e.printStackTrace();
			}
		}
	}

	private void updateListaReservas() {
		ArrayList<Reserva> reservasFiltradas = null;
		DateUtilService serv = DateUtilService.getInstance();
		try {
			Date fechaDesde = serv.getZeroTimeDate((Date) dpDesde.getModel()
					.getValue());
			Date fechaHasta = serv.getZeroTimeDate((Date) dpHasta.getModel()
					.getValue());

			reservasFiltradas = (ArrayList<Reserva>) ReservaService
					.getInstance()
					.getReservasBy(
							reservita -> fechaDesde.compareTo(serv
									.getZeroTimeDate(reservita.getFecha()))
									* serv.getZeroTimeDate(reservita.getFecha())
											.compareTo(fechaHasta) > 0);
		} catch (SQLException e1) {
			reservasFiltradas = new ArrayList<Reserva>();
			e1.printStackTrace();
		} catch (Exception ex) {
			reservasFiltradas = new ArrayList<Reserva>();
			ex.printStackTrace();
		}

		lstReservas.setModel(new GenericListModel<Reserva>(reservasFiltradas));

	}
}
