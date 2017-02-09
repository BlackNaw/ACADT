
// Tablero con vector de 3x3 caracteres

//////////////////////////
class TER {
	private final Tablero tablero = new Tablero();
	private final Jugador jugadores[] = new Jugador[2];
	private final Turno turno = new Turno();

	public TER() {
		for (int i = 0; i < this.jugadores.length; i++) {
			this.jugadores[i] = new Jugador(Jugador.FICHAS[i]);
		}
	}

	public void jugar() {
		tablero.mostrar();
		for (int i = 0; i < Tablero.TAMAÑO * 2 - 1; i++) {
			jugadores[turno.toca()].poner(tablero);
			turno.cambiar();
			tablero.mostrar();
		}
		if (tablero.hayTER())
			jugadores[turno.noToca()].victoria();
		else {
			jugadores[turno.toca()].poner(tablero);
			tablero.mostrar();
			while (!tablero.hayTER()) {
				turno.cambiar();
				jugadores[turno.toca()].mover(tablero);
				tablero.mostrar();
			}
			jugadores[turno.toca()].victoria();
		}
	}

	public static void main(String arg[]) {
		TER partida = new TER();
		partida.jugar();
	}
}

// ////////////////////////
class Jugador {
	private char color;
	public static final char[] FICHAS = new char[] { 'x', 'o' };

	public char getColor() {
		return this.color;
	}

	public Jugador(char color) {
		this.color = color;
	}

	public void poner(Tablero tablero) {
		Coordenada destino = new Coordenada();
		do {
			destino.recoger("Coordenada destino de puesta");
			if (tablero.ocupado(destino)) {
				new GestorIO().out("ERROR: casilla ocupada\n");
			}
		} while (tablero.ocupado(destino));
		tablero.poner(destino, this.getColor());
	}

	public void mover(Tablero tablero) {
		Coordenada origen = new Coordenada();
		do {
			do {
				origen.recoger("Coordenada origen de movimiento");
				if (!tablero.ocupado(origen, this.getColor())) {
					new GestorIO().out("ERROR: no es su ficha\n");
				}
			} while (!tablero.ocupado(origen, this.getColor()));
			tablero.sacar(origen);
			this.poner(tablero);
			// evitar que mueva "sin mover" en la misma posición
			if (tablero.ocupado(origen, this.getColor())) {
				new GestorIO().out("ERROR: no ha realizado movimiento\n");
			}
		} while (tablero.ocupado(origen, this.getColor()));
	}

	public void victoria() {
		new GestorIO().out("las " + this.getColor() + " han gandado!!!");
	}

}

// ////////////////////////
class Tablero {
	private final char fichas[][] = new char[Tablero.TAMAÑO][Tablero.TAMAÑO];
	private final char VACIO = '-';
	public static final int TAMAÑO = 3;

	public Tablero() // constructor
	{
		for (int i = 0; i < Tablero.TAMAÑO; i++)
			for (int j = 0; j < Tablero.TAMAÑO; j++)
				fichas[i][j] = VACIO;
	}

	public void mostrar() {
		GestorIO gestorIO = new GestorIO();
		for (int i = 0; i < Tablero.TAMAÑO; i++) {
			for (int j = 0; j < Tablero.TAMAÑO; j++) {
				gestorIO.out(fichas[i][j]);
				gestorIO.out(' ');
			}
			gestorIO.out('\n');
		}
		gestorIO.out('\n');
	}

	public boolean hayTER() {
		return this.hayTER('o') || this.hayTER('x');
	}

	private boolean hayTER(char color) {
		boolean victoria = false;
		int diagonal = 0;
		int inversa = 0;
		int filas[] = new int[Tablero.TAMAÑO];
		int columnas[] = new int[Tablero.TAMAÑO];

		for (int i = 0; i < Tablero.TAMAÑO; i++) {
			filas[i] = 0;
			columnas[i] = 0;
			for (int j = 0; j < Tablero.TAMAÑO; j++)
				if (fichas[i][j] == color) {
					if (i - j == 0)
						diagonal++;
					if (i + j == Tablero.TAMAÑO - 1)
						inversa++;
					filas[i]++;
					columnas[j]++;
				}
		}
		if ((diagonal == Tablero.TAMAÑO) || (inversa == Tablero.TAMAÑO))
			victoria = true;
		else
			for (int i = 0; i < Tablero.TAMAÑO; i++)
				if ((columnas[i] == Tablero.TAMAÑO)
						|| (filas[i] == Tablero.TAMAÑO))
					victoria = true;
		return victoria;
	}

	public boolean ocupado(Coordenada coordenada, char color) {
		return fichas[coordenada.getFila()][coordenada.getColumna()] == color;
	}

	public boolean ocupado(Coordenada coordenada) {
		return !this.ocupado(coordenada, VACIO);
	}

	public void poner(Coordenada coordenada, char color) {
		fichas[coordenada.getFila()][coordenada.getColumna()] = color;
	}

	public void sacar(Coordenada coordenada) {
		this.poner(coordenada, VACIO);
	}

}

// ////////////////////////
class Coordenada {
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
		do {
			GestorIO gestorIO = new GestorIO();
			gestorIO.out(titulo + "\n");
			gestorIO.out("Dame fila: ");
			this.setFila(gestorIO.inInt() - 1);
			gestorIO.out("Dame columna: ");
			this.setColumna(gestorIO.inInt() - 1);
			if (!this.valida()) {
				gestorIO.out("ERROR: valores entre 1 y " + Tablero.TAMAÑO
						+ "\n");
			}
		} while (!this.valida());
	}

	public boolean valida() {
		final Intervalo rango = new Intervalo(0, Tablero.TAMAÑO - 1);
		return rango.incluye(this.getFila()) && rango.incluye(this.getColumna());
	}



}

// ////////////////////////
class Turno {
	private int valor = 0;

	public void cambiar() {
		this.valor = this.noToca();
	}

	public int toca() {
		return valor;
	}

	public int noToca() {
		return (valor + 1) % 2;
	}

}
