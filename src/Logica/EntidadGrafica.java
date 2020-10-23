package Logica;
import javax.swing.*;

public class EntidadGrafica {
	private ImageIcon gr�fico;
	private String[] numeros;
	private int indexActualEnS;
	
	/**
	 * Crea una entidad grafica
	 */
	public EntidadGrafica() {
		this.gr�fico=new ImageIcon();
		this.numeros = new String[]{"/img/1.png","/img/2.png","/img/3.png","/img/4.png","/img/5.png","/img/6.png","/img/7.png","/img/8.png","/img/9.png"} ;
		this.indexActualEnS = 9; //para poder comparar que celdas se actualizaron y cuales no
	}
	
	/**
	 * Actualiza el gr�fico
	 * @param index
	 */
	public void actualizar(int index) {
		if( index < numeros.length ) {
			ImageIcon ImageIcon = new ImageIcon(this.getClass().getResource(this.numeros[index]));
			this.gr�fico.setImage(ImageIcon.getImage());
			this.indexActualEnS=index;
		}
	}
	
	/**
	 * @return retorna grafico
	 */
	public ImageIcon getGrafico() {
		return this.gr�fico;
	}
	
	/**
	 * @return index actual del grafico en el arreglo 
	 * de numeros
	 */
	public int getIndexActual() {
		return this.indexActualEnS;
	}
	
	/**
	 * Settea el grafico
	 * @param grafico
	 */
	public void setGrafico(ImageIcon grafico) {
		this.gr�fico=grafico;
	}
	
	/**
	 * @return arreglo de numeros
	 */
	public String[] getNumeros() {
		return this.numeros;
	}
	
	/**
	 * Settea el arreglo de numeros
	 * @param n
	 */
	public void setNumeros(String[] n) {
		this.numeros=n;
	}
	
	/**
	 * Compara los indices del grafico en el arreglo de numeros de la entidad gr�fica e y la actual
	 * @param e
	 * @return
	 */
	public boolean equals(EntidadGrafica e) {
		return this.indexActualEnS == e.getIndexActual();
	}
}

