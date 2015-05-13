package com.turnos.ui;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPage {

	private JFrame frmWaltercancha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage window = new MainPage();
					window.frmWaltercancha.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWaltercancha = new JFrame();
		frmWaltercancha.setIconImage(Toolkit.getDefaultToolkit().getImage(MainPage.class.getResource("/com/turnos/resources/icono.JPG")));
		frmWaltercancha.setTitle("WalterCancha");
		frmWaltercancha.setResizable(false);
		frmWaltercancha.setBounds(100, 100, 550, 399);
		frmWaltercancha.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWaltercancha.getContentPane().setLayout(null);
		
		
		JButton btnAbmCanchas = new JButton("ABM Canchas");
		btnAbmCanchas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CanchasPage().setVisible(true);
			}
		});
		btnAbmCanchas.setBounds(26, 51, 154, 101);
		frmWaltercancha.getContentPane().add(btnAbmCanchas);
		
		JButton btnReservar = new JButton("Reservar");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ReservaPage().setVisible(true);
			}
		});
		btnReservar.setBounds(190, 52, 154, 98);
		frmWaltercancha.getContentPane().add(btnReservar);
		
		JButton btnAbmClientes = new JButton("ABM Clientes");
		btnAbmClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ClientesPage().setVisible(true);
			}
		});
		btnAbmClientes.setBounds(360, 54, 154, 94);
		frmWaltercancha.getContentPane().add(btnAbmClientes);
		

		JPanel panel;
		try {
			
			JButton btnListadoDeReservas = new JButton("Listado de Reservas");
			btnListadoDeReservas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new ListadoReservasPage().setVisible(true);
				}
			});
			btnListadoDeReservas.setBounds(190, 160, 154, 98);
			frmWaltercancha.getContentPane().add(btnListadoDeReservas);
			panel = new JImagePanel("resources/fondo.jpg");
			//panel.setBackground(new Color(176, 196, 222));
			panel.setBounds(0, 0, 544, 370);
			frmWaltercancha.getContentPane().add(panel);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
