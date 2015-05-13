package com.turnos.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import com.turnos.models.domain.Cliente;
import com.turnos.persistencia.Persistencia;

public class ClientesPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JLabel lblValue;
	private JLabel lblPrecio;
	private JTextField txtTelefono;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JPanel panel_1;
	private JLabel lblStatusMamis;
	private JButton btnSubirImagen;

	final JFileChooser fc = new JFileChooser();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientesPage frame = new ClientesPage();
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
	public ClientesPage() {
		setTitle("Cliente");
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
				changeTitle(String.format("Cliente %s", txtNombre.getText()));
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
		lblNombre.setBounds(35, 71, 46, 20);
		contentPane.add(lblNombre);

		lblPrecio = new JLabel("Telefono");
		lblPrecio.setBounds(35, 103, 46, 20);
		contentPane.add(lblPrecio);

		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(102, 102, 160, 20);
		txtTelefono.setTransferHandler(null);
		txtTelefono.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});

		contentPane.add(txtTelefono);

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!validData()) {
					setStatus("Falta completar algun campo");
					return;
				}

				Cliente nuevoCliente = new Cliente();
				nuevoCliente.setNombre(txtNombre.getText());
				nuevoCliente.setTelefono(Integer.valueOf(txtTelefono.getText()));

				try {
					Persistencia.getInstance().insert(nuevoCliente);
					cleanForm();
					setStatus("Cliente guardado correctamente");
				} catch (SQLException e1) {
					// Error cuando se guarda
					setStatus("Se produjo un error intentando guardar el cliente.");
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
			panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
					null, null));
			panel_1.setBounds(0, 212, 463, 31);
			contentPane.add(panel_1);
			panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

			lblStatusMamis = new JLabel("");
			panel_1.add(lblStatusMamis);

			btnSubirImagen = new JButton("Subir Imagen");
			btnSubirImagen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

					fc.addChoosableFileFilter(new FileFilter() {

						private String getExtension(File f) {
							String ext = null;
							String s = f.getName();
							int i = s.lastIndexOf('.');

							if (i > 0 && i < s.length() - 1) {
								ext = s.substring(i + 1).toLowerCase();
							}
							return ext;
						}

						@Override
						public String getDescription() {
							return "Imagenes";
						}

						@Override
						public boolean accept(File f) {
							/*
							 * if (f.isDirectory()) { return false; }
							 */

							String extension = getExtension(f);
							if (extension != null) {
								if (extension.equals("jpeg")
										|| extension.equals("jpg")) {
									return true;
								} else {
									return false;
								}
							}

							return false;
						}
					});
					int returnVal = fc.showOpenDialog(ClientesPage.this);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						// TODO: guardar el archivo y guardar string en bd
						File file = fc.getSelectedFile();
						// This is where a real application would open the file.
						// log.append("Opening: " + file.getName() + "." +
						// newline);
					} else {
						// log.append("Open command cancelled by user." +
						// newline);
					}
				}
			});
			btnSubirImagen.setBounds(35, 145, 95, 23);
			contentPane.add(btnSubirImagen);
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
		txtTelefono.setText("");
		this.setTitle("Cliente");
	}

	protected boolean validData() {
		boolean resul = true;

		if (txtNombre.getText().isEmpty())
			resul = false;

		if (txtTelefono.getText().isEmpty())
			resul = false;

		return resul;
	}

	protected void closeFrame() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	protected void changeTitle(String title) {
		this.setTitle(title);
	}

}
