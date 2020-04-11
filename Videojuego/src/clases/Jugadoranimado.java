package clases;

import java.util.ArrayList;
import java.util.HashMap;
import implementacion.Juego;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;

public class Jugadoranimado extends Objetojuego {
	private HashMap<String, Animacion>animaciones;
	private int ximagen;
	private int yimagen;
	private int anchoimagen;
	private int altoimagen;
	private String animacionactual;
	public   int vidas=0;
	private int puntuacion = 0;
	private static int lifes=1;
	private int direccion=1;
	
	public int getDireccion() {
		return direccion;
	}


	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}
	
	
	public Jugadoranimado(int x, int y, String nombreImagen, int velocidad, String animacionactual) {
		super(x, y, nombreImagen, velocidad);
		this.x = x;
		this.y = y;
		this.animacionactual=animacionactual;
		animaciones=new HashMap<String, Animacion>();
		inicializaranimaciones();
		this.velocidad = velocidad;
		
	}

	public void inicializaranimaciones() {
		Rectangle coordenadascorrer[]= {
				new Rectangle(18,155,106,65),
				new Rectangle(82,155,106,65),
				new Rectangle(149,155,106,65),
				new Rectangle(221,155,106,65),
				new Rectangle(300,155,106,65),
				new Rectangle(370,155,106,65)
			
	};
		Animacion animacioncorrer=new Animacion(.05,coordenadascorrer);
		animaciones.put("correr", animacioncorrer);
		
		Rectangle coordenadasdescanso[]= {
				new Rectangle(38,25,106,74),
				new Rectangle(106,25,106,74),
				new Rectangle(177,25,106,74)
				
		};
		Animacion animaciondescanso=new Animacion(0.1,coordenadasdescanso);
		animaciones.put("descanso", animaciondescanso);
		
		
		Rectangle coordenadassaltar[]= {
				new Rectangle(22,269,106,80),
				new Rectangle(103,270,106,80),
				new Rectangle(177,277,106,80)
				
		};
		Animacion animacionsaltar=new Animacion(1.2,coordenadassaltar);
		animaciones.put("saltar", animacionsaltar);
		
}
	
	public void calcularframe(double t) {
		Rectangle coordenadas=animaciones.get(animacionactual).calcularframeactual(t);
		this.ximagen=(int)coordenadas.getX();
		this.yimagen=(int)coordenadas.getY();
		this.altoimagen=(int)coordenadas.getWidth();
		this.anchoimagen=(int)coordenadas.getHeight();
	}
	
	public Rectangle obtenerrectangulo() {
		
		return new Rectangle(x,y,(direccion*anchoimagen)-10,altoimagen);
	}
	
	
	@Override	
	public void pintar(GraphicsContext graficos) {
		graficos.setFont(new Font("Comic Sans MS",22));
		graficos.strokeText("JUNGLE SURVIVAL", 600, 20);
		graficos.drawImage(Juego.imagenes.get(nombreImagen),ximagen,yimagen,anchoimagen,altoimagen,x+(direccion==-1?anchoimagen:0),y,direccion*anchoimagen,altoimagen);//ximagen,yimagen,anchofragmento,altofragmento,xpintar,ypintar,anchopintar,altopintar
		//graficos.setStroke(Color.RED);
		//graficos.strokeRect(x,y,anchoimagen-10,altoimagen);
		graficos.setStroke(Color.BLACK);
		graficos.setFont(new Font("Comic Sans MS",16));
		graficos.strokeText("Puntuacion: " + puntuacion, 20, 30);
	}
	
	@Override	
	public void mover() {
		if(x>1400)
			x=-80;
		if(Juego.derecha)
			x+=velocidad;
		if(Juego.izquierda)
			x-=velocidad;
		if(Juego.arriba)
			y-=velocidad;
		if(Juego.abajo)
			y+=velocidad;
	}



	public String getAnimacionactual() {
		return animacionactual;
	}



	public void setAnimacionactual(String animacionactual) {
		this.animacionactual = animacionactual;
	}


	
	public void verificarColisiones(Itemanimado itemAnimado) {
		if (this.obtenerrectangulo().intersects(itemAnimado.obtenerRectangulo().getBoundsInLocal())) {
				if (!itemAnimado.isCapturado())
					this.puntuacion+=5;
					Juego.puntuacion=this.puntuacion;
				itemAnimado.setCapturado(true);		
	
		}	
	}
	
	public void verificarColisiones2(Item item) {
		if (this.obtenerrectangulo().intersects(item.obtenerRectangulo().getBoundsInLocal())) {
				if (!item.isCapturado())
					this.setLifes(this.getLifes() + 1);
				item.setCapturado(true);				
		}
	}


	public static int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}
	
	
	
}


	
