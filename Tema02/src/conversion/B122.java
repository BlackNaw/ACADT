package conversion;

public class B122 {
  public static void main(String[] args) {
    String s1="He dibujado un bonito dibujo";
    
    StringBuffer si = new StringBuffer("¡Hola desde Java!");
    si.replace(6, 11, "a");
    System.out.println(si);
   

    // cadena1.indexOf(cadena2) devuelve la posicion a partir de la cual
    //  coincide cadena2 en cadena1 por primera vez
    //System.out.println("La primera vez \"dibu\"  que aparece \"dibu\" es " +
        //"en la posición " + s1.indexOf("dibu"));

    // cadena1.lastIndexOf(cadena2) devuelve la posicion a partir de la cual
   //  coincide cadena2 en cadena1 la ultima vez
  // System.out.println("La primera vez que \"dibu\" aparece \"dibu\" es " +
     //  "en la posición " + s1.lastIndexOf("dibu"));
    int a=-1,b=-1;
    
    do{
    	b=s1.indexOf("dibu",++b);
    	System.out.println(b);
    	a++;}while(b!=-1);
    System.out.println("existe "+ a +" veces");

    // replace permite reemplazar todas las ocurrencias de un caracter por otro
    String s2="Quien no diga jacha, jigo y jiguera no es de esta tierra";
    System.out.println(s2.replace('j','h'));
    System.out.println(s2);    
    s2="hola viernes";
    System.out.println(s2);
    System.out.println("Hoy es Viernes".toLowerCase());
    System.out.println("Hoy es Viernes".toUpperCase());

  }
}