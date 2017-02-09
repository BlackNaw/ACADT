class Intervalo {

	private double extremoInferior;
	private double extremoSuperior;

	public void desplazar(double desplazamiento) {
		extremoInferior += desplazamiento;
		extremoSuperior +=  desplazamiento;
	}
	
	public double longitud() {
		return extremoSuperior - extremoInferior;
	}
	
	
	public boolean incluye(double punto) {
		return extremoInferior <= punto && punto <= extremoSuperior;
	}
	
	
	public boolean valido () {
		return extremoInferior <= extremoSuperior;
	}

	public boolean igual(Intervalo intervalo) {
		return extremoInferior == intervalo.extremoInferior
				&& extremoSuperior == intervalo.extremoSuperior;
	}
	
	public Intervalo(double extremoInferior, double extremoSuperior) {
		this.extremoInferior = extremoInferior;
		this.extremoSuperior = extremoSuperior;
	}

	public boolean incluye(Intervalo intervalo) {
		return this.incluye(intervalo.extremoInferior)
				&& this.incluye(intervalo.extremoSuperior);
	}
	
	public void escalar(double factor) {
		double puntoMedio = this.puntoMedio();
		double longitudMitad = this.longitud() * factor / 2;
		this.extremoInferior = puntoMedio - longitudMitad;
		this.extremoSuperior = puntoMedio + longitudMitad;
	}

	public Intervalo() {
		this(0.0, 0.0);
	}
	
	public Intervalo(double extremo) {
		this(0.0, extremo);
	}

	public Intervalo(Intervalo intervalo) {
		this(intervalo.extremoInferior, intervalo.extremoSuperior);
	}	
	
	private Intervalo copia() {
		return new Intervalo(this);
	}

	public Intervalo interseccion(Intervalo intervalo) {
		Intervalo resultado;
		if (this.incluye(intervalo))
			resultado = intervalo.copia();
		else if (intervalo.incluye(this))
			resultado = this.copia();
		else if (this.incluye(intervalo.extremoInferior))
			resultado = new Intervalo(intervalo.extremoInferior,
					this.extremoSuperior);
		else
			resultado = new Intervalo(this.extremoInferior,
					intervalo.extremoSuperior);
		return resultado;
	}

	// ejercicios
	
	public double puntoMedio() {
		return this.extremoInferior + this.longitud() / 2;
	}

	public void mostrar() {
		GestorIO gestorIO = new GestorIO();
		gestorIO.out('[');
		gestorIO.out(this.extremoInferior);
		gestorIO.out(',');
		gestorIO.out(this.extremoSuperior);
		gestorIO.out(']');
	}
	
	public void recoger() {
		GestorIO gestorIO = new GestorIO();
		do {
			gestorIO.out("Extremo inferior: ");
			this.extremoInferior = gestorIO.inDouble();
			gestorIO.out("Extremo superior: ");
			this.extremoSuperior = gestorIO.inDouble();
		} while (!this.valido());
	}

	public boolean distinto(Intervalo intervalo) {
		return !this.igual(intervalo);
	}

	public boolean anterior(Intervalo intervalo) {
		return this.extremoSuperior < intervalo.extremoInferior;
	}

	public boolean posterior(Intervalo intervalo) {
		return this.extremoInferior > intervalo.extremoSuperior;
	}

	public void desplazar() {
		this.desplazar(-this.extremoInferior);
	}

	public Intervalo desplazado(double desplazamiento) {
		Intervalo resultado = this.copia();
		resultado.desplazar(desplazamiento);
		return resultado;
	}

	public Intervalo desplazado() {
		return this.desplazado(-this.extremoInferior);
	}

	public boolean intersecta(Intervalo intervalo) {
		return this.incluye(intervalo.extremoInferior)
				|| this.incluye(intervalo.extremoSuperior)
				|| intervalo.incluye(this);
	}

	public Intervalo union(Intervalo intervalo) {
		double extremoInferior;
		if (this.incluye(intervalo.extremoInferior))
			extremoInferior = this.extremoInferior;
		else
			extremoInferior = intervalo.extremoInferior;
		double extremoSuperior;
		if (this.incluye(intervalo.extremoSuperior))
			extremoSuperior = this.extremoSuperior;
		else
			extremoSuperior = intervalo.extremoSuperior;
		return new Intervalo(extremoInferior, extremoSuperior);
	}

	public Intervalo entre(Intervalo intervalo) {
		Intervalo resultado;
		if (this.anterior(intervalo))
			resultado = new Intervalo(this.extremoSuperior,
					intervalo.extremoInferior);
		else
			resultado = new Intervalo(intervalo.extremoSuperior,
					this.extremoInferior);
		return resultado;
	}

	public Intervalo simetrico() {
		return new Intervalo(-this.extremoSuperior, -this.extremoInferior);
	}

	// /////////////////////////////////////////////// Pruebas

	public static void main(String args[]) {
		GestorIO gestorIO = new GestorIO();
		Intervalo intervalos[] = new Intervalo[3];
		intervalos[0] = new Intervalo(15);
		intervalos[1] = new Intervalo();
		intervalos[2] = new Intervalo();
		intervalos[2].recoger();
		for (int i = 0; i < intervalos.length; i++)
			intervalos[i].mostrar();
		if (intervalos[2].anterior(intervalos[0]))
			gestorIO.out("Punto medio: " + intervalos[2].puntoMedio());
		intervalos[2].desplazar();
		if (intervalos[2].desplazado().distinto(intervalos[1]))
			intervalos[2].desplazado(3).mostrar();
		Intervalo resultado;
		if (intervalos[2].interseccion(intervalos[1]).igual(new Intervalo()))
			resultado = intervalos[2].union(intervalos[1]);
		else
			resultado = intervalos[2].entre(intervalos[1]);
		resultado.mostrar();
	}

}
