package com.turnos.ui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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

public class ListadoReservasPage extends JFrame {

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
	private UtilDateModel model;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private JLabel label;
	private JList<Reserva> lstReservas;
	private JPanel panel;
	private JLabel lblStatus;
	private JButton btnPagada;
	private JButton btnAusente;
	private JLabel lblEstado;
	private JComboBox<ReservaEstado> cmbEstadoReserva;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListadoReservasPage frame = new ListadoReservasPage();
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
	public ListadoReservasPage() {
		setResizable(false);
		setTitle("Listado Reservas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		model = new UtilDateModel();
		model.setDate(Calendar.getInstance().get(Calendar.YEAR), Calendar
				.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(144, 11, 134, 29);
		SpringLayout springLayout = (SpringLayout) datePicker.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH,
				datePicker.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				datePicker);
		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateListaReservas();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(datePicker);

		label = new JLabel("Fecha de la reserva");
		label.setBounds(10, 11, 124, 23);
		contentPane.add(label);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 524, 288);
		contentPane.add(scrollPane);

		cmbEstadoReserva = new JComboBox<ReservaEstado>();
		cmbEstadoReserva.setModel(new DefaultComboBoxModel<ReservaEstado>(
				ReservaEstado.values()));
		cmbEstadoReserva.setSelectedIndex(0);
		cmbEstadoReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateListaReservas();
			}
		});

		cmbEstadoReserva.setBounds(398, 11, 136, 23);
		contentPane.add(cmbEstadoReserva);

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

		btnPagada = new JButton("Pagada");
		btnPagada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lstReservas.isSelectionEmpty())
					return;
				
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Esta seguro que desea marcarlas como pagadas?",
						"Confirmacion", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					updateReservas(ReservaEstado.EFECTIVO);
					updateMessage("Reservas modificadas correctamente");
				}
			}
		});
		btnPagada.setBounds(10, 350, 81, 55);
		contentPane.add(btnPagada);

		btnAusente = new JButton("Ausente");
		btnAusente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lstReservas.isSelectionEmpty())
					return;
				
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Esta seguro que desea marcarlas como ausentes?",
						"Confirmacion", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					updateReservas(ReservaEstado.AUSENTE);
					updateMessage("Reservas modificadas correctamente");
				}
			}
		});
		btnAusente.setBounds(101, 350, 81, 55);
		contentPane.add(btnAusente);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeFrame();
			}
		});
		btnCerrar.setBounds(445, 382, 89, 23);
		contentPane.add(btnCerrar);

		lblEstado = new JLabel("Estado");
		lblEstado.setBounds(288, 11, 100, 23);
		contentPane.add(lblEstado);

	}

	private void updateMessage(String msg) {
		lblStatus.setText(msg);
	}

	protected void closeFrame() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
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
			Date fechaParametro = (Date) datePicker.getModel().getValue();
			ReservaEstado estadoParametro = (ReservaEstado) cmbEstadoReserva
					.getSelectedItem();

			reservasFiltradas = (ArrayList<Reserva>) ReservaService
					.getInstance().getReservasBy(
							reservita -> serv.compareOnlyDate(
									reservita.getFecha(), fechaParametro)
									&& reservita.getEstado().equals(
											estadoParametro));
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
