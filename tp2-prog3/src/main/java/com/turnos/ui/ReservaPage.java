package com.turnos.ui;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import com.turnos.models.domain.Cliente;
import com.turnos.persistencia.Persistencia;
import javax.swing.SpringLayout;

public class ReservaPage extends JFrame {
	
	class ClienteModel implements ComboBoxModel<Cliente>
	{
		private List<Cliente> mList;
		private ListDataListener listener;
		private Cliente selectedItem;
		
		public ClienteModel(List<Cliente> lista) {
			mList = lista;
		}
		
		@Override
		public void addListDataListener(ListDataListener l) {
			listener = l;
		}

		@Override
		public Cliente getElementAt(int index) {
			return mList.get(index);
		}

		@Override
		public int getSize() {
			return mList.size();
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			
		}

		@Override
		public Object getSelectedItem() {
			return selectedItem;
		}

		@Override
		public void setSelectedItem(Object anItem) {
			selectedItem = (Cliente) anItem;
		}
		
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private UtilDateModel model;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private JLabel lblCliente;
	private ComboBoxModel<Cliente> cmbModelCliente;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservaPage frame = new ReservaPage();
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
	public ReservaPage() {
		setResizable(false);
		setTitle("Reservas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		SpringLayout springLayout = (SpringLayout) datePicker.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH, datePicker.getJFormattedTextField(), 0, SpringLayout.SOUTH, datePicker);
		 
		datePicker.setBounds(134,27,202,29);
		contentPane.add(datePicker);
		
		
		JLabel lblFechaDeLa = new JLabel("Fecha de la reserva");
		lblFechaDeLa.setLabelFor(lblFechaDeLa);
		lblFechaDeLa.setBounds(16, 27, 113, 23);
		contentPane.add(lblFechaDeLa);
		
		lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(16, 63, 46, 23);
		contentPane.add(lblCliente);
		
		ArrayList<Cliente> clientes = null;
		try {
			clientes = (ArrayList<Cliente>) Persistencia.getInstance().getAll(Cliente.class);
			cmbModelCliente = new ClienteModel(clientes);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JComboBox<Cliente> comboBox = new JComboBox<Cliente>(cmbModelCliente);
		comboBox.setBounds(134, 67, 202, 23);
		contentPane.add(comboBox);
	}
}
