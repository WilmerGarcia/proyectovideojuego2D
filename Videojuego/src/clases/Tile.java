package clases;

import implementacion.Juego;
import javafx.scene.canvas.GraphicsContext;

public class Tile extends Objetojuego{
	private int xImagen;
	private int yImagen;
	private int tipotile;

	public Tile(int tipotile,int x, int y, String nombreImagen, int velocidad,int ancho, int alto) {
	super(x, y, nombreImagen, velocidad);
	this.alto=alto;
	this.ancho=ancho;
	
	
	switch(tipotile) {
	case 1:
		this.xImagen=0;
		this.yImagen=0;
		break;
	case 2:
		this.xImagen=0;
		this.yImagen=70;
		break;
	case 3:
		this.xImagen=0;
		this.yImagen=140;
		break;
	case 4:
		this.xImagen=0;
		this.yImagen=210;
		break;
		
	case 5:
		this.xImagen=490;
		this.yImagen=560;
		break;
		
	case 6:
		this.xImagen=420;
		this.yImagen=600;
		break;
		
	case 7:
		this.xImagen=0;
		this.yImagen=420;
		break;
		
	case 8:
		this.xImagen=560;
		this.yImagen=280;
		break;
		
	case 10:
		this.xImagen=561;
		this.yImagen=558;
		break;
		
	case 20:
		this.xImagen=560;
		this.yImagen=840;
		break;


	}
	
}

	public int getxImagen() {
		return xImagen;
	}

	public void setxImagen(int xImagen) {
		this.xImagen = xImagen;
	}

	public int getyImagen() {
		return yImagen;
	}

	public void setyImagen(int yImagen) {
		this.yImagen = yImagen;
	}

	@Override
	public void pintar(GraphicsContext graficos) {
		graficos.drawImage(Juego.imagenes.get(nombreImagen), xImagen, yImagen,ancho,alto,x,y,ancho,alto);
		
	}

	@Override
	public void mover() {
		
	}

}
