package com.glintdg.minas.interfaz.swing;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.glintdg.minas.common.Controlador;
import com.glintdg.minas.common.casillas.Casilla;
import com.glintdg.minas.common.casillas.Mina;
import com.glintdg.minas.common.excepciones.MinaExplosionadaException;

/**
 * Representa una casilla en la interfaz de juego
 * como si fuese un boton
 * 
 * @author Almamu
 */
public class CasillaSwing extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Lista de iconos posibles para los botones
	 */
	private ImageIcon[] mIcons = null;
	
	/**
	 * Indice del icono "unknown" para las casillas
	 */
	private static final int ICON_UNKNOWN = 0;
	
	/**
	 * Indice del icono "empty" para las casillas
	 */
	private static final int ICON_EMPTY = 1;
	
	/**
	 * Indice del icono "marked" para las casillas
	 */
	private static final int ICON_MARKED = 2;
	
	/**
	 * Indice del icono "mine_revealed" para las casillas
	 */
	private static final int ICON_MINE = 3;
	
	/**
	 * Cantidad maxima de iconos posibles
	 */
	private static final int ICON_MAX = 4;

	/**
	 * Casilla del tablero a la que este boton apunta
	 */
	private Casilla mCasilla = null;
	
	/**
	 * Controlador del juego
	 */
	private Controlador mControlador = null;
	
	/**
	 * Constructor
	 * 
	 * @param casilla
	 */
	public CasillaSwing(Controlador controlador, Casilla casilla)
	{
		this.setCasilla(casilla);
		this.setControlador(controlador);
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setup();
	}
	
	/**
	 * Configura el boton para que tenga todas las opciones necesarias
	 * para que el juego se lleve a cabo
	 */
	private void setup()
	{
		// evento de raton asignado al boton que controlamos
		// de esta forma es posible recibir las pulsaciones y
		// pasarlas al controlador de juego para que se encargue de actualizarlo
		// boton derecho -> Marca casilla como sospechosa
		// boton izquierdo -> Descubre la casilla
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent event)
			{
				if(event.getButton() == MouseEvent.BUTTON3)
				{
					getControlador().marcar(getCasilla().getFila(), getCasilla().getColumna());
				}
				else if(event.getButton() == MouseEvent.BUTTON1)
				{
					try
					{
						getControlador().descubrir(getCasilla().getFila(), getCasilla().getColumna());
					}
					catch (MinaExplosionadaException e)
					{
					}
				}
				
				actualizar();
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				setIcon(mIcons[ICON_EMPTY]);
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				actualizar();
			}
		});
		
		this.loadIcons();
		this.setIcon(this.mIcons[ICON_UNKNOWN]);
		this.setHorizontalTextPosition(CENTER);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setContentAreaFilled(false);
		this.actualizar();
	}
	
	/**
	 * Actualiza el texto de este boton y su estado de acuerdo al estado de la casilla
	 * a la que ha sido asignado
	 */
	public void actualizar()
	{
		if(this.getCasilla().isDescubierta() == false)
		{
			if(this.getCasilla().isMarcada() == true)
			{
				this.setIcon(this.mIcons[ICON_MARKED]);
			}
			else
			{
				this.setIcon(this.mIcons[ICON_UNKNOWN]);
			}
			
			return;
		}
		
		if(this.getCasilla() instanceof Mina)
		{
			this.setIcon(this.mIcons[ICON_MINE]);
			return;
		}
		else if(this.getCasilla().getMinasCercanas() > 0)
		{
			this.setText(this.getCasilla().getMinasCercanas() + "");
		}
		else
		{
			this.setText("");
		}
		
		this.setIcon(this.mIcons[ICON_EMPTY]);
	}
	
	/**
	 * Actualiza la casilla que este boton controla
	 * 
	 * @param casilla
	 */
	private void setCasilla(Casilla casilla)
	{
		this.mCasilla = casilla;
	}
	
	/**
	 * @return Indica la casilla que este boton controla
	 */
	private Casilla getCasilla()
	{
		return this.mCasilla;
	}
	
	/**
	 * @return Indica el controlador asignado a este boton
	 */
	private Controlador getControlador()
	{
		return this.mControlador;
	}
	
	/**
	 * Actualiza el controlador asignado a este boton
	 * 
	 * @param controlador
	 */
	private void setControlador(Controlador controlador)
	{
		this.mControlador = controlador;
	}
	
	/**
	 * @return Lista de iconos disponibles para el boton
	 */
	private void loadIcons()
	{
		this.mIcons = new ImageIcon[ICON_MAX];
		
		this.mIcons[ICON_UNKNOWN] = new ImageIcon(this.getClass().getResource("/resources/unknown.png"));
		this.mIcons[ICON_EMPTY] = new ImageIcon(this.getClass().getResource("/resources/empty.png"));
		this.mIcons[ICON_MARKED] = new ImageIcon(this.getClass().getResource("/resources/marked.png"));
		this.mIcons[ICON_MINE] = new ImageIcon(this.getClass().getResource("/resources/mine_revealed.png"));
	}
	
	/**
	 * Modifica el comportamiento del dibujado del boton
	 * para poder acoplar el icono a nuestras necesidades
	 */
	@Override
    protected void paintComponent(Graphics g)
	{
        super.paintComponent(g);

        if (this.getIcon() != null)
        {
        	// lo primero es necesario obtener los datos del boton
        	// (rectangulo que dibuja) para poder dibujar el icono
        	// a su tamaño correcto
            Rectangle innerArea = new Rectangle();
            SwingUtilities.calculateInnerArea(this, innerArea);

            // hora de dibujar el icono
            g.drawImage(((ImageIcon)this.getIcon()).getImage(),
                innerArea.x, innerArea.y, innerArea.width, innerArea.height,
                this);
            
            // informacion de la fuente que nos permite calcular el centro 
            // donde dibujar el texto
            FontMetrics metrics = g.getFontMetrics(this.getFont());
            
            // es necesario calcular a mano el x y el y del texto
            int x = (innerArea.width - metrics.stringWidth(this.getText())) / 2;
            int y = ((innerArea.height - metrics.getHeight()) / 2) + metrics.getAscent();
            
            // ultimo paso, dibujar el texto
            g.drawString(this.getText(), x, y);
        }
    }
}
