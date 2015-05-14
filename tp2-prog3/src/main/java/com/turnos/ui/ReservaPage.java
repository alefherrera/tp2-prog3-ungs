package com.turnos.ui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import com.turnos.exceptions.ReservaException;
import com.turnos.models.domain.Cancha;
import com.turnos.models.domain.Cliente;
import com.turnos.models.domain.Reserva;
import com.turnos.persistencia.Persistencia;
import com.turnos.service.ReservaService;
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
	private UtilDateModel datePickerModel;
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
	private JTextField txtNombre;
	private JTextField txtNumeroTel;
	private JPanel pnlNuevoCliente;
	private JImagePanel imagen;

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
		setBounds(100, 100, 368, 488);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cmbClientes = new JComboBox<Cliente>();
		cmbCanchas = new JComboBox<Cancha>();
		cmbHorarios = new JComboBox<String>();

		datePickerModel = new UtilDateModel();
		datePickerModel.setDate(Calendar.getInstance().get(Calendar.YEAR),
				Calendar.getInstance().get(Calendar.MONTH), Calendar
						.getInstance().get(Calendar.DAY_OF_MONTH));
		datePickerModel.setSelected(true);
		datePanel = new JDatePanelImpl(datePickerModel);
		datePicker = new JDatePickerImpl(datePanel);
		SpringLayout springLayout = (SpringLayout) datePicker.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH,
				datePicker.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				datePicker);
		datePicker.setBounds(134, 27, 202, 29);
		contentPane.add(datePicker);

		ArrayList<Cliente> clientes = null;
		try {
			clientes = (ArrayList<Cliente>) Persistencia.getInstance().getAll(
					Cliente.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmbModelCliente = new ClienteModel(clientes);

		cmbClientes.setModel(cmbModelCliente);
		cmbClientes.setSelectedIndex(0);
		cmbClientes.setBounds(134, 136, 202, 23);
		cmbClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cmbClientes.getSelectedIndex() == 0)
					pnlNuevoCliente.setVisible(true);
				else {
					try {
						imagen.changeImage(((Cliente) cmbClientes.getModel()
								.getSelectedItem()).getImagen());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pnlNuevoCliente.setVisible(false);
				}
			}
		});
		contentPane.add(cmbClientes);

		ArrayList<Cancha> canchas = null;
		try {
			canchas = (ArrayList<Cancha>) Persistencia.getInstance().getAll(
					Cancha.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmbCanchaModel = new GenericModel<Cancha>(canchas);
		cmbCanchas.setModel(cmbCanchaModel);
		cmbCanchas.setSelectedIndex(0);
		cmbCanchas.setBounds(134, 68, 202, 23);
		contentPane.add(cmbCanchas);

		cmbHorarios.setModel(new DefaultComboBoxModel<String>(new String[] {
				"0hs", "1hs", "2hs", "3hs", "4hs", "5hs", "6hs", "7hs", "8hs",
				"9hs", "10hs", "11hs", "12hs", "13hs", "14hs", "15hs", "16hs",
				"17hs", "18hs", "19hs", "20hs", "21hs", "22hs", "23hs" }));
		cmbHorarios.setSelectedIndex(0);
		cmbHorarios.setBounds(134, 102, 100, 23);
		contentPane.add(cmbHorarios);

		lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(16, 136, 108, 23);
		contentPane.add(lblCliente);

		JLabel lblFechaDeLa = new JLabel("Fecha de la reserva");
		lblFechaDeLa.setLabelFor(lblFechaDeLa);
		lblFechaDeLa.setBounds(16, 27, 108, 23);
		contentPane.add(lblFechaDeLa);

		JLabel lblCanchas = new JLabel("Canchas");
		lblCanchas.setBounds(16, 68, 108, 23);
		contentPane.add(lblCanchas);

		JLabel lblHorarios = new JLabel("Horarios");
		lblHorarios.setBounds(16, 102, 108, 23);
		contentPane.add(lblHorarios);

		btnReservar = new JButton("Reservar!");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (cmbClientes.getSelectedIndex() == 0) {
					// Validacion para el cliente nuevo
					if (txtNombre.getText().isEmpty()) {
						updateMessage("Ingrese al menos un nombre al cliente...");
						return;
					}

					Cliente nuevoCliente = new Cliente();
					nuevoCliente.setNombre(txtNombre.getText());
					nuevoCliente.setTelefono(Integer.valueOf(txtNumeroTel
							.getText()));

					try {
						Persistencia.getInstance().insert(nuevoCliente);

						txtNombre.setText("");
						txtNumeroTel.setText("");

						updateMessage("Cliente guardado correctamente");

						cmbModelCliente = new ClienteModel(Persistencia
								.getInstance().getAll(Cliente.class));
						cmbClientes.setModel(cmbModelCliente);
						cmbClientes.setSelectedIndex(cmbClientes.getItemCount() - 1);

					} catch (SQLException e1) {
						// Error cuando se guarda
						updateMessage("Se produjo un error intentando guardar el cliente.");
						e1.printStackTrace();
						return;
					}
				}

				if (!validForm()) {
					updateMessage("Falta ingresar algun campo");
					return;
				}

				Calendar cal = Calendar.getInstance();
				Reserva nuevaReserva = new Reserva();
				cal.setTime((Date) datePicker.getModel().getValue());
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.HOUR, cmbHorarios.getSelectedIndex());
				nuevaReserva.setFecha(cal.getTime());

				nuevaReserva.setIdCancha(((Cancha) cmbCanchas.getModel()
						.getSelectedItem()).getId());
				nuevaReserva.setIdCliente(((Cliente) cmbClientes.getModel()
						.getSelectedItem()).getId());
				nuevaReserva.setCantHoras(sliderHoras.getValue());

				Double pagoEfectuado = Double.valueOf(txtSena.getText());
				nuevaReserva.setPago(pagoEfectuado);

				if (pagoEfectuado.equals(Double.valueOf(0)))
					nuevaReserva.setEstado(ReservaEstado.RESERVADO);
				else
					nuevaReserva.setEstado(ReservaEstado.SEÑADO);

				try {
					ReservaService.getInstance().reservar(nuevaReserva);
					updateMessage("Reserva efectuada correctamente.");
					cleanForm();
				} catch (SQLException e1) {
					updateMessage("Ups! No se pudo guardar la reserva...");
					e1.printStackTrace();
				} catch (ReservaException e2) {
					ArrayList<HorarioAlternativo> horariosAlt = null;
					try {
						horariosAlt = (ArrayList<HorarioAlternativo>) ReservaService
								.getInstance().horariosAlternativos(
										nuevaReserva.getFecha(),
										nuevaReserva.getIdCancha());
					} catch (SQLException e1) {
						horariosAlt = new ArrayList<HorarioAlternativo>();
						e1.printStackTrace();
					}
					
					
					
					String listadoHorariosDisponibles = String.join(System.lineSeparator(), horariosAlt.stream().map(x-> x.toString()).collect(Collectors.toList()));
					
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"No se puede reservar a esta hora/cancha. Te propongo un listado de horarios disponibles:" + System.lineSeparator() + listadoHorariosDisponibles ,
							"No se pudo reservar!", JOptionPane.DEFAULT_OPTION);
					
					updateMessage("No se puede guardar en este horario");
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
		txtSena.setBounds(134, 170, 86, 23);
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
		lblSea.setBounds(16, 170, 108, 23);
		contentPane.add(lblSea);

		JLabel lblValue = new JLabel("1");
		lblValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblValue.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValue.setBounds(306, 204, 30, 26);
		contentPane.add(lblValue);

		sliderHoras = new JSlider();
		sliderHoras.setValue(1);
		sliderHoras.setMinimum(1);
		sliderHoras.setMaximum(12);
		sliderHoras.setBounds(134, 204, 162, 26);
		sliderHoras.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblValue.setText(String.valueOf(sliderHoras.getValue()));
			}
		});
		contentPane.add(sliderHoras);

		lblCantidadHoras = new JLabel("Cantidad de horas");
		lblCantidadHoras.setBounds(16, 204, 108, 26);
		contentPane.add(lblCantidadHoras);

		pnlNuevoCliente = new JPanel();
		pnlNuevoCliente.setBounds(16, 241, 320, 73);
		contentPane.add(pnlNuevoCliente);
		pnlNuevoCliente.setLayout(null);

		JLabel label = new JLabel("Nombre");
		label.setBounds(10, 11, 46, 20);
		pnlNuevoCliente.add(label);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(77, 11, 233, 20);
		pnlNuevoCliente.add(txtNombre);

		txtNumeroTel = new JTextField();
		txtNumeroTel.setColumns(10);
		txtNumeroTel.setBounds(77, 42, 160, 20);
		txtNumeroTel.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		pnlNuevoCliente.add(txtNumeroTel);

		JLabel label_1 = new JLabel("Telefono");
		label_1.setBounds(10, 43, 46, 20);
		pnlNuevoCliente.add(label_1);

		try {
			lblStatus = new JLabel("");
			
					JPanel panel = new JPanel();
					panel.setBounds(0, 422, 362, 37);
					contentPane.add(panel);
					panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
							null));
					panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
					
							panel.add(lblStatus);
			imagen = new JImagePanel("");
			imagen.setLocation(16, 327);
			imagen.setSize(138, 73);
			contentPane.add(imagen);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// panel.setBackground(new Color(176, 196, 222));

	}

	private void updateMessage(String msg) {
		lblStatus.setText(msg);
	}

	protected void closeFrame() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	private boolean validForm() {
		boolean resul = true;

		resul &= datePicker.getModel().getValue() != null;
		resul &= !txtSena.getText().isEmpty();

		return resul;
	}

	private void cleanForm() {
		datePickerModel = new UtilDateModel();
		datePickerModel.setDate(Calendar.getInstance().get(Calendar.YEAR),
				Calendar.getInstance().get(Calendar.MONTH), Calendar
						.getInstance().get(Calendar.DAY_OF_MONTH));
		datePickerModel.setSelected(true);

		cmbCanchas.setSelectedIndex(0);
		cmbClientes.setSelectedIndex(0);
		cmbHorarios.setSelectedIndex(0);
		txtSena.setText("0");
		sliderHoras.setValue(1);
		txtNombre.setText("");
		txtNumeroTel.setText("");
		
		pnlNuevoCliente.setVisible(true);
		
	}
	
}
