package paquete1;
class data5
{
  private String d1;
  data5(String s){
    d1=s;
  }
  public String getData()
  {
    return d1;
  }
}

public class B205 {
  public static void main(String[] args) {
    System.out.println((new data5("Hola pepe")).getData());
    
    

  }
}