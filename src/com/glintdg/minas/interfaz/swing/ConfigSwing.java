package com.glintdg.minas.interfaz.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.glintdg.minas.common.idiomas.TraduccionesSwing;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Clase usada para la configuración inicial de la partida
 * por el jugador
 * 
 * @author Almamu
 */
public class ConfigSwing extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Panel con el contenido principal
	 */
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Cuadro de texto para las filas
	 */
	private JTextField filasText;
	
	/**
	 * Cuadro de texto para las columnas
	 */
	private JTextField columnasText;
	
	/**
	 * Cuadro de texto para las minas
	 */
	private JTextField minasText;
	
	/**
	 * Etiqueta para las filas
	 */
	private JLabel filasLabel;
	
	/**
	 * Etiqueta para las columnas
	 */
	private JLabel columnasLabel;
	
	/**
	 * Etiqueta para las minas
	 */
	private JLabel minasLabel;
	
	/**
	 * Panel para el mensaje de bienvenida
	 */
	private JPanel panel;
	
	/**
	 * Panel con las opciones del jugador
	 */
	private JPanel panel_1;

	/**
	 * Filas introducidas por el usuario
	 */
	private int mFilas = 0;
	
	/**
	 * Columnas introducidas por el usuario
	 */
	private int mColumnas = 0;
	
	/**
	 * Minas introducidas por el usuario
	 */
	private int mMinas = 0;
	
	/**
	 * Controla si el boton "Salir/Cancelar" fue pulsado
	 */
	private boolean mCancel = false;
	
	/**
	 * Lanza el dialogo como si fuese una aplicacion
	 * independiente del resto (usado para debug)
	 */
	public static void main(String[] args)
	{
		try
		{
			ConfigSwing dialog = new ConfigSwing(TraduccionesSwing.ConfigWindow.WELCOME_MESSAGE);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return Numero de filas que ha elegido el usuario
	 */
	public int getFilas()
	{
		return this.mFilas;
	}
	
	/**
	 * @return Numero de columnas que ha elegido el usuario
	 */
	public int getColumnas()
	{
		return this.mColumnas;
	}
	
	/**
	 * @return Numero de minas que ha elegido el usuario
	 */
	public int getMinas()
	{
		return this.mMinas;
	}
	
	/**
	 * @return Indica si se pulso sobre "cancelar"
	 */
	public boolean wasCanceled()
	{
		return this.mCancel;
	}
	
	/**
	 * Crea el dialogo (codigo autogenerado por el diseñador de eclipse)
	 */
	public ConfigSwing(String dialogo)
	{
		// configuracion del dialogo modal
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setTitle(TraduccionesSwing.ConfigWindow.TITLE);
		setBounds(100, 100, 655, 195);
		
		// layout del contenedor principal
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// panel con el mensaje de bienvenida
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			panel = new JPanel();
			contentPanel.add(panel);
			{
				JLabel explicacionLabel = new JLabel("<html>" + dialogo + "</html>");
				panel.add(explicacionLabel);
			}
		}
		
		// panel con todas las opciones que el jugador puede personalizar
		{
			panel_1 = new JPanel();
			contentPanel.add(panel_1);
			{
				filasLabel = new JLabel("Filas:");
				panel_1.add(filasLabel);
			}
			filasLabel.setLabelFor(filasText);
			{
				filasText = new JTextField();
				filasText.setText("15");
				panel_1.add(filasText);
				filasText.setColumns(10);
			}
			{
				columnasLabel = new JLabel("Columnas:");
				panel_1.add(columnasLabel);
			}
			columnasLabel.setLabelFor(columnasText);
			{
				columnasText = new JTextField();
				columnasText.setText("15");
				panel_1.add(columnasText);
				columnasText.setColumns(10);
			}
			{
				minasLabel = new JLabel("Numero de minas:");
				panel_1.add(minasLabel);
			}
			minasLabel.setLabelFor(minasText);
			{
				minasText = new JTextField();
				minasText.setText("15");
				panel_1.add(minasText);
				minasText.setColumns(10);
			}
		}
		
		// panel con los botones del dialogo
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(TraduccionesSwing.ConfigWindow.OK_BUTTON);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0)
					{
						try
						{
							mFilas = Integer.parseInt(filasText.getText());
							
							if(mFilas <= 0) throw new Exception();
						}
						catch(Exception e2)
						{
							JOptionPane.showMessageDialog(null, "Las filas han de ser numeros mayores que 0", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						try
						{
							mColumnas = Integer.parseInt(columnasText.getText());
							
							if(mColumnas <= 0) throw new Exception();
						}
						catch(Exception e2)
						{
							JOptionPane.showMessageDialog(null, "Las columnas han de ser numeros mayores que 0", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						try
						{
							mMinas = Integer.parseInt(minasText.getText());
							
							if(mMinas <= 0) throw new Exception();
						}
						catch(Exception e2)
						{
							JOptionPane.showMessageDialog(null, "Las minas han de ser numeros mayores que 0", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						mCancel = false;
						setVisible(false);
					}
				});
				okButton.setActionCommand(TraduccionesSwing.ConfigWindow.OK_BUTTON);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(TraduccionesSwing.ConfigWindow.CANCEL_BUTTON);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						mCancel = true;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand(TraduccionesSwing.ConfigWindow.CANCEL_BUTTON);
				buttonPane.add(cancelButton);
			}
		}
	}

}
