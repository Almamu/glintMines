package com.glintdg.minas.interfaz.swing;

import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.glintdg.minas.common.Partida;
import com.glintdg.minas.common.Ranking;

public class RankingSwing extends JDialog
{
	private static final long serialVersionUID = 1L;
	private String[] mTableColumns = new String[] {"Nombre", "Puntos", "Filas", "Columnas", "Minas", "Dificultad"};
	private DefaultTableModel mTableModel = null;
	private JTable mTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			RankingSwing dialog = new RankingSwing();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RankingSwing()
	{
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		this.mTableModel = new DefaultTableModel();
		
		this.mTable = new JTable(this.mTableModel);
		getContentPane().add(this.mTable);
		this.llenarTabla();
	}
	
	/**
	 * Rellena la tabla con los datos del ranking
	 */
	private void llenarTabla()
	{
		ArrayList<Partida> partidas = Ranking.get();
		Object[][] data = new Object[Ranking.get().size()][this.mTableColumns.length];
		
		for(int i = 0; i < partidas.size(); i ++)
		{
			Partida partida = partidas.get(i);
			
			data[i][0] = partida.getNombre();
			data[i][1] = partida.getPuntos();
			data[i][2] = partida.getTablero().getFilas();
			data[i][3] = partida.getTablero().getColumnas();
			data[i][4] = partida.getTablero().getMinas();
			data[i][5] = partida.getTablero().getDificultad();
		}
		
		this.mTableModel.setDataVector(data, this.mTableColumns);
	}
}
