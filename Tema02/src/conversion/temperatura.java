package conversion;

public class temperatura {

	public static void main(String[] args) {
		float arreglo[][][] = new float[12][][];
		arreglo[0]= new float[32][2];
		arreglo[1]= new float[30][2];
		arreglo[2]= new float[32][2];
		arreglo[3]= new float[31][2];
		arreglo[4]= new float[32][2];
		arreglo[5]= new float[31][2];
		arreglo[6]= new float[32][2];
		arreglo[7]= new float[32][2];
		arreglo[8]= new float[31][2];
		arreglo[9]= new float[32][2];
		arreglo[10]= new float[31][2];
		arreglo[11]= new float[32][2];
		
		arreglo[9][20][0]=10.5f;
		arreglo[9][20][1]=31.7f;
		
		String saludo="Hola";
		
		System.out.println("Temperatura máxima "+arreglo[9][20][1]+"ºC y mínima de "+arreglo[9][20][0]+"ºC");
		System.out.println("Saludo tiene "+saludo.length());
		System.out.println(saludo.charAt(2));
	}

}
