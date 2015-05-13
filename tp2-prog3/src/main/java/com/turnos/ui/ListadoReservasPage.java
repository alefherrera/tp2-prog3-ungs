package com.turnos.ui;

import java.awt.EventQueue;
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
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import com.turnos.models.domain.Reserva;
import com.turnos.service.DateUtilService;
import com.turnos.service.ReservaService;

public class ListadoReservasPage extends JFrame {

	class GenericListModel<T> implements ListModel<T>{

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
	private JList<Reserva> lstReservas;
	private JLabel label;
	
	
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
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		
		
		try {
			lstReservas = new JList<Reserva>(new GenericListModel<Reserva>(ReservaService.getInstance().getReservas()));
		} catch (SQLException e2) {
			lstReservas = new JList<Reserva>(new GenericListModel<Reserva>(null));
			e2.printStackTrace();
		}
		
		model = new UtilDateModel();
		model.setDate(Calendar.getInstance().get(Calendar.YEAR), Calendar
				.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(144, 11, 202, 29);
		SpringLayout springLayout = (SpringLayout) datePicker.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH,
				datePicker.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				datePicker);

		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Reserva> reservasDelDia = null;
				DateUtilService serv = DateUtilService.getInstance();
				try {
					reservasDelDia = (ArrayList<Reserva>) ReservaService.getInstance().getReservasBy(res -> serv.compareOnlyDate(res.getFecha(), (Date) datePicker.getModel().getValue()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				lstReservas.setModel(new GenericListModel<Reserva>(reservasDelDia));
				
				
			}
		});
		contentPane.setLayout(null);
		contentPane.add(datePicker);
		
		label = new JLabel("Fecha de la reserva");
		label.setBounds(23, 11, 111, 23);
		contentPane.add(label);
		
		
		lstReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstReservas.setBounds(10, 256, 424, -205);
		contentPane.add(lstReservas);
		
		
		
	}
}
