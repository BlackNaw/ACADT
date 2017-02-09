package paquete1;

class silla{
	public void versilla(){
		System.out.println("Esto es una silla");
	}
	public void sentarse(){
		System.out.println("Esto es una silla");
	}
}
class mesa{
	public void vermesa(){
		System.out.println("Esto es una mesa");
	}
}
public class muebles {
	
	public void usarsilla(){
		silla elemento = new silla();
		elemento.versilla();
	}

	public void usarmesa(){
		mesa elemento = new mesa();
		elemento.vermesa();
	}

}
