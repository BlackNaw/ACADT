package conversion;

import java.util.Scanner;

public class temperatura2 {
	public static void main(String[] args) {
		int ano = 366, dia = 0, tem = 2, temperatura[][] = new int[tem][ano];
		Scanner leer = new Scanner(System.in);

		do {
			System.out.print("Introduce un día del año: ");
			dia = leer.nextInt();
		} while (dia < 0 || dia > ano);
		System.out.println("La temperaura mínima del día " + dia + " fue " + temperatura[0][(dia - 1)]
				+ " grados y máxima fue de " + temperatura[1][(dia - 1)] + " grados");
	}
}
