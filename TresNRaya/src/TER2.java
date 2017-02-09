
// Tablero con 2x3 coordenadas de fichas de jugadores
// Pequeñas refactorizaciones de la versión anterior

//////////////////////////
class TER2 {
	private final Tablero2 tablero = new Tablero2();
	private final Jugador2 jugadores[] = new Jugador2[Jugador2.NUMERO];
	private final Turno2 turno = new Turno2();

	public TER2() {
		for (int i = 0; i < Jugador2.NUMERO; i++) {
			this.jugadores[i] = new Jugador2(i);
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
		TER2 partida = new TER2();
		partida.jugar();
	}
}

// ////////////////////////
class Jugador2 {
	private int color;
	private int puestas = 0;
	public static final int NUMERO = 2;

	public int getColor() {
		return this.color;
	}
	
	private void setColor(int color){
		this.color = color;
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

	public Jugador2(int color) {
		this.setColor(color);
	}

	public void turno(Tablero2 tablero) {
		if (this.puestas < Tablero.TAMAÑO) {
			this.poner(tablero);
		} else {
			this.mover(tablero);
		}
	}

	private void poner(Tablero2 tablero) {
		Coordenada2 destino = new Coordenada2();
		do {
			destino.recoger("Coordenada destino de puesta");
			if (tablero.ocupado(destino)) {
				new GestorIO().out("ERROR: casilla ocupada\n");
			}
		} while (tablero.ocupado(destino));
		tablero.poner(destino, this.getColor());
		this.incPuestas();
	}

	private void mover(Tablero2 tablero) {
		Coordenada2 origen = new Coordenada2();
		do {
			do {
				origen.recoger("Coordenada origen de movimiento");
				if (!tablero.ocupado(origen, this.getColor())) {
					new GestorIO().out("ERROR: no es su ficha\n");
				}
			} while (!tablero.ocupado(origen, this.getColor()));
			tablero.sacar(origen, this.getColor());
			this.poner(tablero);
			// evitar que mueva "sin mover" en la misma posición de partida
			if (tablero.ocupado(origen, this.getColor())) {
				new GestorIO().out("ERROR: no ha realizado movimiento\n");
			}
		} while (tablero.ocupado(origen, this.getColor()));
	}

	public void victoria() {
		new GestorIO().out("las " + Tablero2.COLOR[color] + " han gandado!!!");
	}

}

// ////////////////////////
class Tablero2 {
	private static final Coordenada2 fichas[][] = new Coordenada2[Jugador2.NUMERO][Tablero2.TAMAÑO];
	public static final int TAMAÑO = 3;
	public static final char[] COLOR = new char[] { 'x', 'o', '-' };
	
	public void mostrar() {
		GestorIO gestorIO = new GestorIO();
		
		for (int i = 1; i <= Tablero2.TAMAÑO; i++) {
			for (int j = 1; j <= Tablero2.TAMAÑO; j++) {
				final Coordenada2 coordenada = new Coordenada2(i, j);
				int color = Jugador2.NUMERO;
				for (int k = 0; k < Jugador2.NUMERO; k++) {
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

	public boolean hayTER(Jugador2 jugador) {
		if (jugador.getPuestas() < Tablero.TAMAÑO) {
			return false;
		} else {
			final int color = jugador.getColor();
			final int direccion = fichas[color][0].direccion(fichas[color][1]);
			for (int i=1; i<Tablero2.TAMAÑO-1; i++){
				if (direccion != fichas[color][i].direccion(fichas[color][i+1])){
					return false;
				}
			}
			return true;
		}
	}

	public boolean ocupado(Coordenada2 coordenada, int color) {
		for (int j = 0; j < Tablero.TAMAÑO; j++) {
			if (fichas[color][j] != null && fichas[color][j].igual(coordenada)) {
				return true;
			}
		}
		return false;
	}

	public boolean ocupado(Coordenada2 coordenada) {
		return this.ocupado(coordenada, 0) || this.ocupado(coordenada, 1);
	}

	public void poner(Coordenada2 coordenada, int color) {
		int j = 0;
		while (fichas[color][j] != null) {
			j++;
		}
		fichas[color][j] = coordenada;
	}

	public void sacar(Coordenada2 coordenada, int color) {
		for (int j = 0; j < Tablero.TAMAÑO; j++) {
			if (fichas[color][j] != null && fichas[color][j].igual(coordenada)) {
				fichas[color][j] = null;
			}
		}
	}

}

// ////////////////////////
class Coordenada2 {
	private int fila;
	private int columna;

	public static final int FILA = 1;
	public static final int COLUMNA = 2;
	public static final int DIAGONAL = 3;
	public static final int INVERSA = 4;
	public static final int NINGUNA = 5;

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
	
	public Coordenada2() {
	}

	public Coordenada2(int fila, int columna) {
		this.setFila(fila - 1);
		this.setColumna(columna - 1);
	}

	public void recoger(String titulo) {
		do {
			GestorIO gestorIO = new GestorIO();
			gestorIO.out(titulo + "\n");
			gestorIO.out("Dame fila: ");
			this.setFila(gestorIO.inInt() - 1);
			gestorIO.out("Dame columna: ");
			this.setColumna(gestorIO.inInt() - 1);
			if (!this.valida()) {
				gestorIO.out("ERROR: valores entre 1 y " + Tablero2.TAMAÑO
						+ "\n");
			}
		} while (!this.valida());
	}

	public boolean valida() {
		final Intervalo rango = new Intervalo(0, Tablero.TAMAÑO - 1);
		return rango.incluye(this.getFila()) && rango.incluye(this.getColumna());
	}

	protected boolean enFila(Coordenada2 coordenada) {
		return this.getFila() == coordenada.getFila();
	}

	protected boolean enColumna(Coordenada2 coordenada) {
		return this.getColumna() == coordenada.getColumna();
	}
	
	public boolean igual(Coordenada2 coordenada) {
		return this.enFila(coordenada) 
				&& this.enColumna(coordenada);
	}

	protected boolean enDiagonal() {
		return this.getFila() - this.getColumna() == 0;
	}

	protected boolean enInversa() {
		return this.getFila() + this.getColumna() == Tablero.TAMAÑO - 1;
	}

	public int direccion(Coordenada2 coordenada) {
		if (this.enFila(coordenada)) {
			return Coordenada2.FILA;
		} else if (this.enColumna(coordenada)) {
			return Coordenada2.COLUMNA;
		} else if (this.enDiagonal() && coordenada.enDiagonal()) {
			return Coordenada2.DIAGONAL;
		} else if (this.enInversa() && coordenada.enInversa()) {
			return Coordenada2.INVERSA;
		} else {
			return Coordenada2.NINGUNA;
		}
	}

}

// ////////////////////////
class Turno2 {
	private int valor = 0;

	public void cambiar() {
		this.valor = this.noToca();
	}

	public int toca() {
		return valor;
	}

	public int noToca() {
		return (valor + 1) % Jugador2.NUMERO;
	}

}
