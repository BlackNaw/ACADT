//////////////////////////
class TER4 {
	private final Tablero4 tablero = new Tablero4();
	private final Jugador4 jugadores[] = new Jugador4[Jugador4.NUMERO];
	private final Turno4 turno = new Turno4();

	public TER4(int manuales) {
		for (int i = 0; i < manuales; i++) {
			this.jugadores[i] = new Jugador4Manual(i);
		}
		for (int i = manuales; i < Jugador4.NUMERO; i++) {
			this.jugadores[i] = new Jugador4Automatico(i);
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
		TER4 partida = new TER4(0);
		partida.jugar();
	}
}

// ////////////////////////
abstract class Jugador4 {
	private int color;
	private int puestas = 0;
	public static final int NUMERO = 2;

	public Jugador4(int color) {
		this.color = color;
	}

	public int getColor() {
		return this.color;
	}

	public int getPuestas() {
		return this.puestas;
	}

	private void setPuestas(int puestas) {
		this.puestas = puestas;
	}

	protected void incPuestas() {
		if (this.getPuestas() < 3) {
			this.setPuestas(this.getPuestas() + 1);
		}
	}

	public void turno(Tablero4 tablero) {
		if (this.puestas < Tablero.TAMAÑO) {
			this.poner(tablero);
		} else {
			this.mover(tablero);
		}
	}

	protected abstract void poner(Tablero4 tablero);

	protected abstract void mover(Tablero4 tablero);

	public void victoria() {
		new GestorIO().out("las " + Tablero4.COLOR[color] + " han gandado!!!");
	}

}

// ////////////////////////
class Jugador4Manual extends Jugador4 {

	public Jugador4Manual(int color) {
		super(color);
	}

	protected void poner(Tablero4 tablero) {
		Coordenada4TER destino = new Coordenada4TER();
		do {
			destino.recoger("Coordenada destino de puesta");
			if (tablero.ocupado(destino)) {
				new GestorIO().out("ERROR: casilla ocupada\n");
			}
		} while (tablero.ocupado(destino));
		tablero.poner(destino, this.getColor());
	}

	protected void mover(Tablero4 tablero) {
		Coordenada4TER origen = new Coordenada4TER();
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

}

// ////////////////////////
class Jugador4Automatico extends Jugador4 {

	public Jugador4Automatico(int color) {
		super(color);
	}

	protected void poner(Tablero4 tablero) {
		this.poner(tablero, null);
	}
	
	// evitar que mueva "sin mover" en la misma posición de partida
	private void poner(Tablero4 tablero, Coordenada4TER prohibido) {
		int i = 1;
		int j = 1;
		Coordenada4TER destino = new Coordenada4TER(i, j);
		while (prohibido != null && destino.igual(prohibido) || tablero.ocupado(destino)) {
			j++;
			if (j > Tablero.TAMAÑO) {
				i++;
				j = 1;
			}
			destino = new Coordenada4TER(i, j);
		}
		tablero.poner(destino, this.getColor());
		destino.mostrar("Pone en:");
		this.incPuestas();
	}

	protected void mover(Tablero4 tablero) {
		int i = 1;
		int j = 1;
		Coordenada4TER origen = new Coordenada4TER(i, j);
		while (!tablero.ocupado(origen,this.getColor())) {
			j++;
			if (j > Tablero.TAMAÑO) {
				i++;
				j = 1;
			}
			origen = new Coordenada4TER(i, j);
		}
		origen.mostrar("Retira de:");
		tablero.sacar(origen, this.getColor());
		this.poner(tablero, origen);
	}
}

// ////////////////////////
class Tablero4 {
	private static final Coordenada4TER fichas[][] = new Coordenada4TER[Jugador4.NUMERO][Tablero4.TAMAÑO];
	public static final int TAMAÑO = 3;
	public static final char[] COLOR = new char[] { 'x', 'o', '-' };

	public void mostrar() {
		GestorIO gestorIO = new GestorIO();

		for (int i = 1; i <= Tablero4.TAMAÑO; i++) {
			for (int j = 1; j <= Tablero4.TAMAÑO; j++) {
				final Coordenada4TER coordenada = new Coordenada4TER(i, j);
				int color = Jugador4.NUMERO;
				for (int k = 0; k < Jugador4.NUMERO; k++) {
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

	public boolean hayTER(Jugador4 jugador) {
		if (jugador.getPuestas() < Tablero.TAMAÑO) {
			return false;
		} else {
			final int color = jugador.getColor();
			final int direccion = fichas[color][0].direccion(fichas[color][1]);
			for (int i = 1; i < Tablero4.TAMAÑO - 1; i++) {
				if (direccion != fichas[color][i]
						.direccion(fichas[color][i + 1])) {
					return false;
				}
			}
			return true;
		}
	}

	public boolean ocupado(Coordenada4TER coordenada, int color) {
		for (int j = 0; j < Tablero.TAMAÑO; j++) {
			if (fichas[color][j] != null && fichas[color][j].igual(coordenada)) {
				return true;
			}
		}
		return false;
	}

	public boolean ocupado(Coordenada4TER coordenada) {
		return this.ocupado(coordenada, 0) || this.ocupado(coordenada, 1);
	}

	public void poner(Coordenada4TER coordenada, int color) {
		int j = 0;
		while (fichas[color][j] != null) {
			j++;
		}
		fichas[color][j] = coordenada;
	}

	public void sacar(Coordenada4TER coordenada, int color) {
		for (int j = 0; j < Tablero.TAMAÑO; j++) {
			if (fichas[color][j] != null && fichas[color][j].igual(coordenada)) {
				fichas[color][j] = null;
			}
		}
	}

}

// ////////////////////////
class Coordenada4 {
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

	protected boolean enFila(Coordenada4 coordenada) {
		return this.getFila() == coordenada.getFila();
	}

	protected boolean enColumna(Coordenada4 coordenada) {
		return this.getColumna() == coordenada.getColumna();
	}

	public boolean igual(Coordenada4 coordenada) {
		return this.enFila(coordenada) && this.enColumna(coordenada);
	}

	protected boolean enDiagonal() {
		return this.getFila() - this.getColumna() == 0;
	}

}

// ////////////////////////
class Coordenada4TER extends Coordenada4 {
	public static final int FILA = 1;
	public static final int COLUMNA = 2;
	public static final int DIAGONAL = 3;
	public static final int INVERSA = 4;
	public static final int NINGUNA = 5;

	protected void setFila(int fila) {
		super.setFila(fila - 1);
	}

	protected void setColumna(int columna) {
		super.setColumna(columna - 1);
	}

	public Coordenada4TER() {
		super();
	}

	public Coordenada4TER(int fila, int columna) {
		this.setFila(fila);
		this.setColumna(columna);
	}

	public void recoger(String titulo) {
		do {
			super.recoger(titulo);
			if (!this.valida()) {
				new GestorIO().out("ERROR: valores entre 1 y "
						+ Tablero2.TAMAÑO + "\n");
			}
		} while (!this.valida());
	}

	public boolean valida() {
		final Intervalo rango = new Intervalo(0, Tablero.TAMAÑO - 1);
		return rango.incluye(this.getFila())
				&& rango.incluye(this.getColumna());
	}

	protected boolean enInversa() {
		return this.getFila() + this.getColumna() == Tablero.TAMAÑO - 1;
	}

	public int direccion(Coordenada4TER coordenada) {
		if (this.enFila(coordenada)) {
			return Coordenada4TER.FILA;
		} else if (this.enColumna(coordenada)) {
			return Coordenada4TER.COLUMNA;
		} else if (this.enDiagonal() && coordenada.enDiagonal()) {
			return Coordenada4TER.DIAGONAL;
		} else if (this.enInversa() && coordenada.enInversa()) {
			return Coordenada4TER.INVERSA;
		} else {
			return Coordenada4TER.NINGUNA;
		}
	}
	
	public void mostrar(String titulo){
		new GestorIO().out(titulo + "(" + this.getFila() + "," + this.getColumna() + ")\n");
	}
}

// ////////////////////////
class Turno4 {
	private int valor = 0;

	public void cambiar() {
		this.valor = this.noToca();
	}

	public int toca() {
		return valor;
	}

	public int noToca() {
		return (valor + 1) % Jugador4.NUMERO;
	}

}
