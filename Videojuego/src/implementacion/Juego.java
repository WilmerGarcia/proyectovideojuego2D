package implementacion;

import java.util.ArrayList;
import java.util.HashMap;

import clases.Fondo;
import clases.Item;
import clases.Itemanimado;
import clases.Jugador;
import clases.Jugadoranimado;
import clases.Tile;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Juego extends Application{
	private GraphicsContext graficos;
	private Group root;
	private Scene escena;
	private Canvas lienzo;
	//private Jugador jugador;
	private Jugadoranimado jugadoranimado;
	private Fondo fondo;
	public static boolean arriba;
	public static boolean abajo;
	public static boolean derecha;
	public static boolean izquierda;
	public static HashMap<String,Image>imagenes;
	private int randomx,randomy,randomyy;
	private ArrayList<Itemanimado> coins;
	public static int puntuacion = 0;
	
	private ArrayList<Item> vidas;
	private ArrayList<Tile> tiles;
	private Item V1,V2,V3;
	private int tilemap[][] = {
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,7,0,0,0,0,8,0,0,0,0,0,7,0},
			{5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,7,0,0,0,0,0,8,0,0,0,0,0,7},
			{5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,7,0,0,0,0,0,8,0,0,0,0,0,0,0,7,0,0},
			{5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
	};
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage ventana) throws Exception {
		inicializarcomponentes();
		gestioneventos();
		ventana.setScene(escena);
		ventana.setTitle("Jungle Survival");
		ventana.show();
		ciclojuego();
	}
	
		public void ciclojuego() {
			long tiempoInicial=System.nanoTime();
			AnimationTimer animationtimer= new AnimationTimer() {

				@Override
				public void handle(long tiempoActual) {
					double t=(tiempoActual-tiempoInicial)/1000000000.0;
					//System.out.println(t);
					actualizarestado(t);
					pintar();
				}
				
			};
			animationtimer.start();//empieza el ciclo de juego
		}
		
		
		public void actualizarestado(double t) {
			//jugador.mover();
			
			for(int z=0;z<coins.size();z++) {
				coins.get(z).mover();
				coins.get(z).calcularframe(t);
			}
			
			for (int i=0;i<coins.size();i++)
				jugadoranimado.verificarColisiones(coins.get(i));
			
			
			
			for (int z=0;z<3;z++) 
				vidas.get(z).mover();
			for (int i=0;i<vidas.size();i++)
				jugadoranimado.verificarColisiones2(vidas.get(i));
				
			jugadoranimado.calcularframe(t);
			jugadoranimado.mover();
			fondo.mover();
		}
		
		
		public void inicializarcomponentes() {
			imagenes=new HashMap<String,Image>();
			cargarimagenes();
			//jugador=new Jugador(20,40,"vaquero",3,0);
			jugadoranimado=new Jugadoranimado(20,530,"personaje",3,"descanso");
		
			fondo=new Fondo(0,0,"fondo","fondo2", 5);
			
			V1=new Item(1200,5,0,0,"vida",0);
			V2=new Item(1240,5,0,0,"vida",0);
			V3=new Item(1280,5,0,0,"vida",0);
			
			coins = new ArrayList<Itemanimado>();
			for (int z=0;z<40;z++) {
				randomyy=(int)(Math.random()*3+1);
				randomx=(int)(Math.random()*15000+1000);
				if (randomyy==1)
					randomy=100;
				if(randomyy==2)
					randomy=310;
				if(randomyy==3)
					randomy=500;
				coins.add(new Itemanimado(randomx,randomy,0,0,"monedas",1,0,"mover"));
				
			}
			
			
			
			vidas=new ArrayList<Item>();
			for (int z=0;z<3;z++) {
				randomyy=(int)(Math.random()*3+1);
				randomx=(int)(Math.random()*8000+3000);
				if (randomyy==1)
					randomy=70;
				if (randomyy==2)
					randomy=280;
				if (randomyy==3)
					randomy=490;
				vidas.add(new Item(randomx,randomy,0,0,"vida",4));
			}
			
			

			inicializartiles();
			//tile=new Tile(0,0,"tilemap",0,45,46,24,24);
			root= new Group();
			escena=new Scene(root,1400,700);
			lienzo=new Canvas(1400,700);
			root.getChildren().add(lienzo);
			graficos=lienzo.getGraphicsContext2D();
		}
		
		public void inicializartiles() {
			tiles=new ArrayList<Tile>();
			for(int i=0;i<tilemap.length;i++) {
				for(int j=0;j<tilemap[i].length;j++) {
					if(tilemap[i][j]!=0)
						this.tiles.add(new Tile(tilemap[i][j],j*70,i*70,"tilemap",0,70,68));
				}
			}
		}
		
		public void cargarimagenes() {
			imagenes.put("vaquero",new Image("vaquero.png" ));
			imagenes.put("goku-furioso",new Image("goku-furioso.png"));
			imagenes.put("fondo",new Image("fondo.jpg"));
			imagenes.put("fondo2",new Image("fondo2.jpg"));
			imagenes.put("tilemap",new Image("tilemap.png"));
			imagenes.put("personaje", new Image("personaje.png"));
			imagenes.put("monedas",new Image("monedas.png"));
			imagenes.put("vida", new Image("vida.png"));
		};

		public void pintar() {
		fondo.pintar(graficos);
		
		for (int i=0;i<coins.size();i++)
			coins.get(i).pintar(graficos);
			
		for (int i=0;i<tiles.size();i++)
			tiles.get(i).pintar(graficos);
		
		//tile.pintar(graficos);
		
		for(int i=0;i<tiles.size();i++)
			tiles.get(i).pintar(graficos);
		
		//jugador.pintar(graficos);
		jugadoranimado.pintar(graficos);
		
		if (jugadoranimado.getLifes()==2)
			V1.pintar(graficos);
		
		if (jugadoranimado.getLifes()==3) {
			V1.pintar(graficos);
			V2.pintar(graficos);
		}
		
		if (jugadoranimado.getLifes()==4) {
			V1.pintar(graficos);
			V2.pintar(graficos);
			V3.pintar(graficos);
		}
			for (int i=0;i<vidas.size();i++)
				vidas.get(i).pintar(graficos);
			
	}
		
		public void gestioneventos() {
		escena.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent evento) {
				switch(evento.getCode().toString()) {
				case "RIGHT":
					derecha=true;
					jugadoranimado.setDireccion(1);
					jugadoranimado.setAnimacionactual("correr");
					break;
				case "LEFT":
					izquierda=true;
					jugadoranimado.setDireccion(-1);
					jugadoranimado.setAnimacionactual("correr");
					break;
				case "UP":
					jugadoranimado.setAnimacionactual("saltar");
					arriba=true;
					break;
				case "DOWN":
					jugadoranimado.setAnimacionactual("saltar");
					abajo=true;
					break;
				case "SPACE":
					//jugador.setVelocidad(10);
					jugadoranimado.setVelocidad(15);
					break;
				}
			}
			
		});	
		escena.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent evento) {
				switch(evento.getCode().toString()) {
				case "RIGHT":
					jugadoranimado.setAnimacionactual("descanso");
					derecha=false;
					break;
				case "LEFT":
					jugadoranimado.setAnimacionactual("descanso");
					izquierda=false;
					break;
				case "UP":
					jugadoranimado.setAnimacionactual("correr");
					arriba=false;
					break;
				case "DOWN":
					jugadoranimado.setAnimacionactual("correr");
					abajo=false;
					break;
				case "SPACE":
					//jugador.setVelocidad(5);
					jugadoranimado.setVelocidad(5);
					break;
			}
			
		}
		
		});
		
		}
}
