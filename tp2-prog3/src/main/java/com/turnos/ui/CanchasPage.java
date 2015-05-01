package com.turnos.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.turnos.models.domain.Cancha;
import com.turnos.persistencia.Persistencia;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;

public class CanchasPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JSlider sliderJugadores;
	private JLabel lblValue;
	private JLabel lblPrecio;
	private JTextField txtPrecio;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JPanel panel_1;
	private JLabel lblStatusMamis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CanchasPage frame = new CanchasPage();
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
	public CanchasPage() {
		setTitle("Cancha");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 469, 272);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				changeTitle(String.format("Cancha %s", txtNombre.getText()));
			}
		});
		txtNombre.setBounds(102, 71, 306, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);

		lblValue = new JLabel("");
		lblValue.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblValue.setBounds(416, 97, 30, 26);
		contentPane.add(lblValue);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(35, 74, 46, 14);
		contentPane.add(lblNombre);

		JLabel lblCantidadMaximaDe = new JLabel("Cantidad maxima de jugadores");
		lblCantidadMaximaDe.setBounds(35, 103, 176, 20);
		contentPane.add(lblCantidadMaximaDe);

		sliderJugadores = new JSlider();
		sliderJugadores.setMinimum(2);
		sliderJugadores.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblValue.setText(String.valueOf(sliderJugadores.getValue()));
			}
		});
		sliderJugadores.setValue(10);
		sliderJugadores.setMaximum(22);
		lblCantidadMaximaDe.setLabelFor(sliderJugadores);
		sliderJugadores.setBounds(223, 102, 185, 26);
		contentPane.add(sliderJugadores);

		lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(35, 135, 46, 14);
		contentPane.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(102, 134, 86, 20);
		txtPrecio.setTransferHandler(null);
		txtPrecio.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});

		contentPane.add(txtPrecio);

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!validData()){
					setStatus("Falta completar algun campo");
					return;
				}
				
				//creamos el nuevo objeto cancha y lo guardamos aca
				Cancha nuevaCancha = new Cancha();
				nuevaCancha.setNombre(txtNombre.getText());
				nuevaCancha.setCantMaxima(sliderJugadores.getValue());
				nuevaCancha.setPrecio(Double.valueOf(txtPrecio.getText()));
				
				try {
					Persistencia.getInstance().insert(nuevaCancha);
					cleanForm();
					setStatus("Cancha guardada correctamente");
				} catch (SQLException e1) {
					//Error cuando se guarda
					setStatus("Se produjo un error intentando guardar la cancha.");
					e1.printStackTrace();
				}
				
			}
		});
		btnGuardar.setBounds(357, 181, 89, 23);
		contentPane.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFrame();
			}
		});
		btnCancelar.setBounds(258, 181, 89, 23);
		contentPane.add(btnCancelar);

		JPanel panel;
		try {
			panel = new JImagePanel("resources/pitch.jpg");
			panel.setBackground(new Color(176, 196, 222));
			panel.setBounds(0, 0, 463, 53);
			contentPane.add(panel);
			
			panel_1 = new JPanel();
			panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
							null));
			panel_1.setBounds(0, 212, 463, 31);
			contentPane.add(panel_1);
			panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			
			lblStatusMamis = new JLabel("");
			panel_1.add(lblStatusMamis);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	protected void setStatus(String msg) {
		lblStatusMamis.setText(msg);
	}

	protected void cleanForm() {
		txtNombre.setText("");
		txtPrecio.setText("");
		sliderJugadores.setValue(10);
	}

	protected boolean validData() {
		boolean resul = true;
		
		if (txtNombre.getText().isEmpty())
			resul = false;
		
		if (txtPrecio.getText().isEmpty())
			resul = false;
		
		return resul;
	}

	protected void closeFrame() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	protected void changeTitle(String title){
		this.setTitle(title);
	}
	
	
	
}
