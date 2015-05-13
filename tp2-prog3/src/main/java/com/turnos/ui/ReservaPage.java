package com.turnos.ui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataListener;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import com.turnos.enums.ReservaEstado;
import com.turnos.models.domain.Cancha;
import com.turnos.models.domain.Cliente;
import com.turnos.models.domain.Reserva;
import com.turnos.persistencia.Persistencia;
import com.turnos.service.ReservaService;
import com.turnos.service.bean.CanchaBean;
import com.turnos.service.bean.HorarioAlternativo;

public class ReservaPage extends JFrame {

	class GenericModel<T> implements ComboBoxModel<T> {
		private List<T> mList;
		private ListDataListener listener;
		private T selectedItem;

		public GenericModel(List<T> lista) {
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

		}

		@Override
		public Object getSelectedItem() {
			return selectedItem;
		}

		@Override
		public void setSelectedItem(Object anItem) {
			selectedItem = (T) anItem;
		}

	}

	class ClienteModel implements ComboBoxModel<Cliente> {
		private List<Cliente> mList;
		private ListDataListener listener;
		private Cliente selectedItem;

		public ClienteModel(List<Cliente> lista) {
			mList = lista;
			Cliente noCliente = new Cliente();
			noCliente.setNombre("Nuevo cliente...");
			noCliente.setId(-1);
			mList.add(0, noCliente);
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
	private GenericModel<Cancha> cmbCanchaModel;
	private JLabel lblStatus;
	private JComboBox<Cliente> cmbClientes;
	private JComboBox<Cancha> cmbCanchas;
	private JComboBox<String> cmbHorarios;
	private JButton btnCancelar;
	private JButton btnReservar;
	private JTextField txtSena;
	private JSlider sliderHoras;
	private JLabel lblCantidadHoras;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 368, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cmbClientes = new JComboBox<Cliente>();
		cmbCanchas = new JComboBox<Cancha>();
		cmbHorarios = new JComboBox<String>();
		lblStatus = new JLabel("");

		model = new UtilDateModel();
		model.setDate(Calendar.getInstance().get(Calendar.YEAR), Calendar
				.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		SpringLayout springLayout = (SpringLayout) datePicker.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH,
				datePicker.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				datePicker);
		datePicker.setBounds(134, 27, 202, 29);
		contentPane.add(datePicker);

		ArrayList<Cliente> clientes = null;
		try {
			clientes = (ArrayList<Cliente>) Persistencia.getInstance().getAll(Cliente.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmbModelCliente = new ClienteModel(clientes);

		cmbClientes.setModel(cmbModelCliente);
		cmbClientes.setSelectedIndex(0);
		cmbClientes.setBounds(134, 169, 202, 23);
		contentPane.add(cmbClientes);

		ArrayList<Cancha> canchas = null;
		try {
			canchas = (ArrayList<Cancha>) Persistencia.getInstance().getAll(Cancha.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmbCanchaModel = new GenericModel<Cancha>(canchas);
		cmbCanchas.setModel(cmbCanchaModel);
		cmbCanchas.setSelectedIndex(0);
		cmbCanchas.setBounds(134, 101, 202, 23);
		contentPane.add(cmbCanchas);

		cmbHorarios.setModel(new DefaultComboBoxModel<String>(new String[] {"0hs", "1hs", "2hs", "3hs", "4hs", "5hs", "6hs", "7hs", "8hs", "9hs", "10hs", "11hs", "12hs", "13hs", "14hs", "15hs", "16hs", "17hs", "18hs", "19hs", "20hs", "21hs", "22hs", "23hs"}));
		cmbHorarios.setSelectedIndex(0);
		cmbHorarios.setBounds(134, 135, 100, 23);
		contentPane.add(cmbHorarios);

		lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(16, 169, 108, 23);
		contentPane.add(lblCliente);

		JLabel lblFechaDeLa = new JLabel("Fecha de la reserva");
		lblFechaDeLa.setLabelFor(lblFechaDeLa);
		lblFechaDeLa.setBounds(16, 27, 108, 23);
		contentPane.add(lblFechaDeLa);

		JLabel lblCanchas = new JLabel("Canchas");
		lblCanchas.setBounds(16, 101, 108, 23);
		contentPane.add(lblCanchas);

		JLabel lblHorarios = new JLabel("Horarios");
		lblHorarios.setBounds(16, 135, 108, 23);
		contentPane.add(lblHorarios);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
				null));
		panel.setBounds(0, 381, 362, 31);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		panel.add(lblStatus);

		btnReservar = new JButton("Reservar!");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (cmbClientes.getSelectedIndex() == 0)
				{
					
				}
				
				if (!validForm())
					return;

				Reserva nuevaReserva = new Reserva();
				nuevaReserva.setFecha((Date) datePicker.getModel().getValue());
				nuevaReserva.setIdCancha(((CanchaBean) cmbCanchas.getModel()
						.getSelectedItem()).getCancha().getId());
				nuevaReserva.setIdCliente(((Cliente) cmbClientes.getModel()
						.getSelectedItem()).getId());
				nuevaReserva.setCantHoras(sliderHoras.getValue());

				Double pagoEfectuado = Double.valueOf(txtSena.getText());
				nuevaReserva.setPago(pagoEfectuado);

				if (pagoEfectuado.equals(0))
					nuevaReserva.setEstado(ReservaEstado.RESERVADO);
				else
					nuevaReserva.setEstado(ReservaEstado.SEÑADO);

				try {
					ReservaService.getInstance().reservar(nuevaReserva);
				} catch (SQLException e1) {
					updateMessage("Ups! No se pudo guardar la reserva...");
					e1.printStackTrace();
				}

			}
		});

		btnReservar.setBounds(263, 347, 89, 23);
		contentPane.add(btnReservar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFrame();
			}
		});
		btnCancelar.setBounds(164, 347, 89, 23);
		contentPane.add(btnCancelar);

		txtSena = new JTextField();
		txtSena.setText("0");
		txtSena.setColumns(10);
		txtSena.setBounds(134, 203, 86, 23);
		txtSena.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		contentPane.add(txtSena);

		JLabel lblSea = new JLabel("Seña");
		lblSea.setBounds(16, 203, 108, 23);
		contentPane.add(lblSea);

		JLabel lblValue = new JLabel("1");
		lblValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblValue.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValue.setBounds(306, 237, 30, 26);
		contentPane.add(lblValue);

		sliderHoras = new JSlider();
		sliderHoras.setValue(1);
		sliderHoras.setMinimum(1);
		sliderHoras.setMaximum(12);
		sliderHoras.setBounds(134, 237, 162, 26);
		sliderHoras.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblValue.setText(String.valueOf(sliderHoras.getValue()));
			}
		});
		contentPane.add(sliderHoras);

		lblCantidadHoras = new JLabel("Cantidad de horas");
		lblCantidadHoras.setBounds(16, 237, 108, 26);
		contentPane.add(lblCantidadHoras);

	}

	private void updateMessage(String msg) {
		lblStatus.setText(msg);
	}

	protected void closeFrame() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	protected boolean validForm() {

		return true;
	}
	
	//List<HorarioAlternativo> listaDeHorarios = 
	//ReservaService.getInstance().horariosAlternativos(fechaSolicitada, ((CanchaBean)cmbCanchas.getSelectedItem()).getCancha().getId());
	
}
