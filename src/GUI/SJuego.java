package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;

import Logica.Celda;
import Logica.Juego;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.border.CompoundBorder;
import javax.swing.Timer;
import java.awt.GridBagLayout;

public class SJuego extends JFrame {

	private Juego juego;
	private JPanel contentPane;
	private Timer t;
	private int h, m, s, cs;
	private Clip audio;
	private JLabel [][] celdas;// guardo los labels que contienen a las celdas para despues modificarlos
	private String archivo;

	/**
	 * Create the frame.
	 */
	public SJuego(String archivo, Juego j) {
		
		setIconImage(new ImageIcon(getClass().getResource("/img/9.png")).getImage());
		
		this.archivo = archivo;
		
		this.celdas = new JLabel[9][9];
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 694, 466);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel1 = new JPanel();
		panel1.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 0), 2, true), new LineBorder(new Color(255, 0, 255), 3, true)));
		panel1.setBackground(Color.BLACK);
		panel1.setBounds(12, 13, 399, 405);
		contentPane.add(panel1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.BLUE, 3));
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(431, 13, 245, 405);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/img/derecha.png")));
		lblNewLabel.setBounds(12, 274, 229, 131);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("    00:00:00:00");
		lblNewLabel_1.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 26));
		lblNewLabel_1.setForeground(Color.YELLOW);
		lblNewLabel_1.setBounds(58, 68, 175, 51);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_5 = new JLabel(" SOLUCION INVALIDA");
		lblNewLabel_5.setForeground(Color.YELLOW);
		lblNewLabel_5.setFont(new Font("Britannic Bold", Font.PLAIN, 19));
		lblNewLabel_5.setBounds(34, 255, 207, 16);
		
		JButton btnNewButton = new JButton("Verificar");
		btnNewButton.setBorder(new LineBorder(new Color(255, 255, 0), 2));
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setForeground(Color.YELLOW);
		btnNewButton.setFont(new Font("Britannic Bold", Font.PLAIN, 16));
		btnNewButton.setSelectedIcon(new ImageIcon(getClass().getResource("/img/9.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean verifica = true;
				
				for(int i = 0; i < 9 && verifica; i++) {
					for(int j = 0; j < 9 && verifica; j++) {
						verifica = ( !juego.getCelda(i, j).getError() && ( juego.getCelda(i, j).getValor() != null ) );//si la celda no esta en estado de error y no es nula entonces, es valida
					}
				}
				
				if(!verifica) {
					
					sonidoFail();
				
					panel_1.add(lblNewLabel_5);
					panel_1.repaint();
					
					
				}else {
					
					t.stop();
					
					panel_1.remove(lblNewLabel_5);
					
					panel1.removeAll();
					
					JLabel lblNewLabel_3 = new JLabel(" VICTORIA");
					lblNewLabel_3.setBounds(74, 83, 325, 101);
					lblNewLabel_3.setFont(new Font("Britannic Bold", Font.ITALIC, 52));
					lblNewLabel_3.setForeground(Color.YELLOW);
					panel1.add(lblNewLabel_3);
					
					JLabel lblNewLabel_4 = new JLabel("");
					lblNewLabel_4.setIcon(new ImageIcon(getClass().getResource("/img/pacman grande2.png")));
					lblNewLabel_4.setBounds(151, 168, 152, 179);
					panel1.add(lblNewLabel_4);

					repaint();
					
				}
			}
		});
		
		btnNewButton.setBounds(75, 144, 97, 25);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Reiniciar");
		btnNewButton_1.setBackground(Color.BLACK);
		btnNewButton_1.setBorder(new LineBorder(new Color(255, 255, 0), 2));
		btnNewButton_1.setForeground(Color.YELLOW);
		btnNewButton_1.setFont(new Font("Britannic Bold", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				sonidoReiniciar();
				
				if(t.isRunning()) {
					t.stop();
				}
				
				h=0; m=0; s=0; cs=0;
				actualizarLabel(lblNewLabel_1);
				t.start();
				
				panel_1.remove(lblNewLabel_5);
				
				panel1.removeAll();
				
				inicializar(panel1,new Juego(archivo));
				repaint();
				
			}
		});
		
		btnNewButton_1.setBounds(75, 207, 97, 25);
		panel_1.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(getClass().getResource("/img/frutas.png")));
		lblNewLabel_2.setBounds(4, 0, 229, 69);
		panel_1.add(lblNewLabel_2);
		
		
		ActionListener acciones = new ActionListener() {
			
			public void actionPerformed(ActionEvent ae) {
				
				cs++;
				if(cs==100) {
					cs = 0;
					++s;
				}
				if(s==60) {
					s = 0;
					++m;
				}
				if(m==60) {
					m = 0;
					++h;
				}
			    actualizarLabel(lblNewLabel_1);
				
			}
			
		};
		
		t = new Timer(10 , acciones);
		t.start();
		
		inicializar(panel1,j);
		
		panel1.setLayout(new GridLayout(juego.getCantFilas(),1,1,1));
		
		
	}
	
	private void actualizarLabel(JLabel l) {
		String tiempo = ( h<=9 ? "0" : "")+h+":"+( m<=9 ? "0" : "")+m+":"+( s<=9 ? "0" : "")+s+":"+( cs<=9 ? "0" : "")+cs;
		l.setText(tiempo);
		
	}
	
	/**
	 * Inicializa la matriz de juego Sudoku
	 * @param panel panel de la matriz de juego
	 * @param game tablero de juego
	 */
	private void inicializar(JPanel panel, Juego game) {
		
		juego = game ; 
		
		for(int i = 0 ; i< 9 ; i++) {
			for(int j = 0 ; j < 9 ; j++) {

				
				Celda c=juego.getCelda(i, j);
			
				ImageIcon grafico = c.getEntidadG().getGrafico();
				
				JLabel label = new JLabel();
				
				
				panel.add(label);
				this.celdas[i][j]=label;
				
				pintar(c);
				
				label.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						reDimensionar(label, grafico);
						label.setIcon(grafico); 
					}
				});
				
				if(c.modificable()) {// si la celda actual no es modificable
					label.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							
							juego.accionar(c);
							reDimensionar(label,grafico);
							
							for(int i=0; i<9;i++) {//reinicio el estado del tablero y de las celdas
								for(int j=0; j<9; j++) {
									pintar(juego.getCelda(i, j));
									juego.getCelda(i, j).setError(false);
								}
							}
							
							
							juego.verificar();//verifico si se cumplen las condiciones
							
							for(int i=0; i<9; i++) {//cambio las que estan repetidas por el borde amarillo
								for(int j=0; j<9; j++) {
									if(juego.getCelda(i, j).getError())
										celdas[i][j].setBorder(new LineBorder(Color.YELLOW, 2, true));
								}
							}
							
							celdas[c.getFila()][c.getCol()].repaint();
							
						}
					});
				}
			}//end f2
		}//end f1
		
	}

	/**
	 * Pinta la celda c en base a si es modificable y a qué cuadrante pertenece
	 * @param c
	 */
	private void pintar( Celda c) {
		
		int i =  c.getFila(), j = c.getCol(); 
		
		if( i == 0 || i == 1 || i == 2 || i == 6 || i == 7 || i == 8 ) {
			if( j == 0 || j == 1 || j == 2 || j == 6 || j == 7 || j == 8 ) {
				if(c.modificable())
					celdas[i][j].setBorder(new LineBorder(Color.MAGENTA, 2, true));
				else 
					celdas[i][j].setBorder(new CompoundBorder(new LineBorder(Color.MAGENTA, 1, true), new LineBorder(Color.CYAN, 1, true)));
			}else
				if(c.modificable())
					celdas[i][j].setBorder(new LineBorder(Color.BLUE, 2, true));
				else
					celdas[i][j].setBorder(new CompoundBorder(new LineBorder(Color.BLUE, 1, true), new LineBorder(Color.CYAN, 1, true)));
		}else {
			if( j == 3 || j == 4 || j == 5 )
				if(c.modificable())
					celdas[i][j].setBorder(new LineBorder(Color.MAGENTA, 2, true));
				else
					celdas[i][j].setBorder(new CompoundBorder(new LineBorder(Color.MAGENTA, 1, true), new LineBorder(Color.CYAN, 1, true)));
			else
				if(c.modificable())
					celdas[i][j].setBorder(new LineBorder(Color.BLUE, 2, true));
				else
					celdas[i][j].setBorder(new CompoundBorder(new LineBorder(Color.BLUE, 1, true), new LineBorder(Color.CYAN, 1, true)));
		}
	}
	
	/**
	 * Activa el sonido que suena al reiniciar el juego
	 */
	private void sonidoReiniciar() {
		try {
			audio = AudioSystem.getClip();
			audio.open(AudioSystem.getAudioInputStream(getClass().getResource("/sonidos/waka waka.wav")));
			audio.start();
		}catch(LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.getMessage();
			System.out.println("error audio");
		}
	}
	
	/**
	 * Activa el sonido cuando la solución del juego no es válida
	 */
	private void sonidoFail() {
		try {
			audio = AudioSystem.getClip();
			audio.open(AudioSystem.getAudioInputStream(getClass().getResource("/sonidos/pm.wav")));
			audio.start();
			
		}catch(LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.getMessage();
			System.out.println("error audio");
		}
		
	}
	
	/**
	 * Redimensiona la imagen del label
	 * @param label 
	 * @param grafico
	 */
	private void reDimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if( image != null) {
			Image newing = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newing);
			label.repaint();
		}
	}
}
