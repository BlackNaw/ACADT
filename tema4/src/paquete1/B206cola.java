package paquete1;
class cola {
  private int cola[];
   private int inicio,fin;
 cola (int size)
   {
    cola = new int[size];
    fin= -1;
   inicio= -1;
  
   }
  public int sacar()
  {
    if(inicio==10 || inicio == -1 ) return 0;
      else return cola[inicio++];
    }
   public void poner(int pon)
   {
    /* if(fin >10) return 0;
     else {
       cola[++fin]=push_this;
       return 1;
      }
       */
	   if(fin <10) cola[++fin]=pon;
	   if(fin==0) inicio=0;
    
   }
}
public class B206cola {
  public static void main(String[] args) {
    int valoractual;
    cola cola1 = new cola(10);
    System.out.println("Añadiendo valores ahora ...");
    for(int i=0; i<10;i++){
      cola1.poner(i);
      System.out.println("Valor añadido-->"+i);
         }
    System.out.println("Sacando valores ahora...");
    for(int i=0;i<10;i++){
    	valoractual =cola1.sacar();
      System.out.println("Valor sacado--->"+valoractual);
    }
  }
}