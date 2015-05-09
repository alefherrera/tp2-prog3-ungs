package com.turnos.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPage {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage window = new MainPage();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 550, 399);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnAbmCanchas = new JButton("ABM Canchas");
		btnAbmCanchas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CanchasPage().setVisible(true);
			}
		});
		btnAbmCanchas.setBounds(10, 118, 121, 41);
		frame.getContentPane().add(btnAbmCanchas);
		
		JButton btnReservar = new JButton("Reservar");
		btnReservar.setBounds(141, 118, 121, 41);
		frame.getContentPane().add(btnReservar);
		
		JButton btnAbmClientes = new JButton("ABM Clientes");
		btnAbmClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ClientesPage().setVisible(true);
			}
		});
		btnAbmClientes.setBounds(273, 118, 121, 41);
		frame.getContentPane().add(btnAbmClientes);
	}
}
