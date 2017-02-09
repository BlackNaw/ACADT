//////////////////////////
class TER3 {
	private final Tablero3 tablero = new Tablero3();
	private final Jugador3 jugadores[] = new Jugador3[Jugador3.NUMERO];
	private final Turno3 turno = new Turno3();

	public TER3() {
		for (int i = 0; i < Jugador3.NUMERO; i++) {
			this.jugadores[i] = new Jugador3(i);
		}
	}

	public void jugar() {
		do {
			tablero.mostrar();
			jugadores[turno.toca()].turno(tablero);
			turno.cambiar();
		} while (!tablero.hayTER(jugadores[turno.noToca()]));
		tablero.mostrar();
		jugadores[turno.noToca()].victoria();
	}

	public static void main(String arg[]) {
		TER3 partida = new TER3();
		partida.jugar();
	}
}

// ////////////////////////
class Jugador3 {
	private int color;
	private int puestas = 0;
	public static final int NUMERO = 2;

	public int getColor() {
		return this.color;
	}

	public int getPuestas() {
		return this.puestas;
	}
	
	private void setPuestas(int puestas){
		this.puestas = puestas;
	}
	
	protected void incPuestas() {
		if (this.getPuestas()<3){
			this.setPuestas(this.getPuestas()+1);
		}
	}
	
	public Jugador3(int color) {
		this.color = color;
	}

	public void turno(Tablero3 tablero) {
		if (this.puestas < Tablero.TAMAÑO) {
			this.poner(tablero);
		} else {
			this.mover(tablero);
		}
	}

	private void poner(Tablero3 tablero) {
		Coordenada3TER destino = new Coordenada3TER();
		do {
			destino.recoger("Coordenada destino de puesta");
			if (tablero.ocupado(destino)) {
				new GestorIO().out("ERROR: casilla ocupada\n");
			}
		} while (tablero.ocupado(destino));
		tablero.poner(destino, color);
		this.incPuestas();
	}

	private void mover(Tablero3 tablero) {
		Coordenada3TER origen = new Coordenada3TER();
		do {
			do {
				origen.recoger("Coordenada origen de movimiento");
				if (!tablero.ocupado(origen, color)) {
					new GestorIO().out("ERROR: no es su ficha\n");
				}
			} while (!tablero.ocupado(origen, color));
			tablero.sacar(origen, color);
			this.poner(tablero);
			// evitar que mueva "sin mover" en la misma posición de partida
			if (tablero.ocupado(origen, color)) {
				new GestorIO().out("ERROR: no ha realizado movimiento\n");
			}
		} while (tablero.ocupado(origen, color));
	}

	public void victoria() {
		new GestorIO().out("las " + Tablero3.COLOR[color] + " han gandado!!!");
	}

}

// ////////////////////////
class Tablero3 {
	private static final Coordenada3TER fichas[][] = new Coordenada3TER[Jugador3.NUMERO][Tablero3.TAMAÑO];
	public static final int TAMAÑO = 3;
	public static final char[] COLOR = new char[] { 'x', 'o', '-' };

	public void mostrar() {
		GestorIO gestorIO = new GestorIO();

		for (int i = 1; i <= Tablero3.TAMAÑO; i++) {
			for (int j = 1; j <= Tablero3.TAMAÑO; j++) {
				final Coordenada3TER coordenada = new Coordenada3TER(i, j);
				int color = Jugador3.NUMERO;
				for (int k = 0; k < Jugador3.NUMERO; k++) {
					if (this.ocupado(coordenada, k)) {
						color = k;
					}
				}
				gestorIO.out(COLOR[color]);
				gestorIO.out(' ');
			}
			gestorIO.out('\n');
		}
		gestorIO.out('\n');
	}

	public boolean hayTER(Jugador3 jugador) {
		if (jugador.getPuestas() < Tablero.TAMAÑO) {
			return false;
		} else {
			final int color = jugador.getColor();
			final int direccion = fichas[color][0].direccion(fichas[color][1]);
			for (int i = 1; i < Tablero3.TAMAÑO - 1; i++) {
				if (direccion != fichas[color][i]
						.direccion(fichas[color][i + 1])) {
					return false;
				}
			}
			return true;
		}
	}

	public boolean ocupado(Coordenada3TER coordenada, int color) {
		for (int j = 0; j < Tablero.TAMAÑO; j++) {
			if (fichas[color][j] != null && fichas[color][j].igual(coordenada)) {
				return true;
			}
		}
		return false;
	}

	public boolean ocupado(Coordenada3TER coordenada) {
		return this.ocupado(coordenada, 0) || this.ocupado(coordenada, 1);
	}

	public void poner(Coordenada3TER coordenada, int color) {
		int j = 0;
		while (fichas[color][j] != null) {
			j++;
		}
		fichas[color][j] = coordenada;
	}

	public void sacar(Coordenada3TER coordenada, int color) {
		for (int j = 0; j < Tablero.TAMAÑO; j++) {
			if (fichas[color][j] != null && fichas[color][j].igual(coordenada)) {
				fichas[color][j] = null;
			}
		}
	}

}

// ////////////////////////
class Coordenada3 {
	private int fila;
	private int columna;

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}
	
	protected void setFila(int fila) {
		this.fila = fila;
	}

	protected void setColumna(int columna) {
		this.columna = columna;
	}
	
	public void recoger(String titulo) {
			GestorIO gestorIO = new GestorIO();
			gestorIO.out(titulo + "\n");
			gestorIO.out("Dame fila: ");
			this.setFila(gestorIO.inInt());
			gestorIO.out("Dame columna: ");
			this.setColumna(gestorIO.inInt());
	}

	protected boolean enFila(Coordenada3 coordenada) {
		return this.getFila() == coordenada.getFila();
	}

	protected boolean enColumna(Coordenada3 coordenada) {
		return this.getColumna() == coordenada.getColumna();
	}
	
	public boolean igual(Coordenada3 coordenada) {
		return this.enFila(coordenada) 
				&& this.enColumna(coordenada);
	}

	protected boolean enDiagonal() {
		return this.getFila() - this.getColumna() == 0;
	}

}

// ////////////////////////
class Coordenada3TER extends Coordenada3 {
	public static final int FILA = 1;
	public static final int COLUMNA = 2;
	public static final int DIAGONAL = 3;
	public static final int INVERSA = 4;
	public static final int NINGUNA = 5;

	protected void setFila(int fila) {
		super.setFila(fila-1);
	}

	protected void setColumna(int columna) {
		super.setColumna(columna-1);
	}
	
	public Coordenada3TER(){
		super();
	}
	
	public Coordenada3TER (int fila, int columna) {
		this.setFila(fila);
		this.setColumna(columna);
	}
	
	public void recoger(String titulo) {
		do {
			super.recoger(titulo);
			if (!this.valida()) {
				new GestorIO().out("ERROR: valores entre 1 y " + Tablero2.TAMAÑO
						+ "\n");
			}
		} while (!this.valida());
	}
	
	public boolean valida() {
		final Intervalo rango = new Intervalo(0, Tablero.TAMAÑO - 1);
		return rango.incluye(this.getFila()) && rango.incluye(this.getColumna());
	}

	protected boolean enInversa() {
		return this.getFila() + this.getColumna() == Tablero.TAMAÑO - 1;
	}

	public int direccion(Coordenada3TER coordenada) {
		if (this.enFila(coordenada)) {
			return Coordenada3TER.FILA;
		} else if (this.enColumna(coordenada)) {
			return Coordenada3TER.COLUMNA;
		} else if (this.enDiagonal() && coordenada.enDiagonal()) {
			return Coordenada3TER.DIAGONAL;
		} else if (this.enInversa() && coordenada.enInversa()) {
			return Coordenada3TER.INVERSA;
		} else {
			return Coordenada3TER.NINGUNA;
		}
	}
}

// ////////////////////////
class Turno3 {
	private int valor = 0;

	public void cambiar() {
		this.valor = this.noToca();
	}

	public int toca() {
		return valor;
	}

	public int noToca() {
		return (valor + 1) % Jugador3.NUMERO;
	}

}
