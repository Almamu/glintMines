package com.glintdg.minas.common;

import com.glintdg.minas.common.excepciones.MinaExplosionadaException;

/**
 * Controlador del juego usado por la interfaz grafica
 * para realizar acciones como descubrir elementos y ser notificada
 * en caso de explosiones, etc
 * 
 * @author Almamu
 */
public interface Controlador
{
	public void onMinaExplosionada(int fila, int columna);
	public void descubrir(int fila, int columna) throws MinaExplosionadaException;
	public void marcar(int fila, int columna);
	
	public int getCasillasDescubiertas();
	public int getMinas();
	public int getMinasMarcadas();
}
