package paquete1;
class bata
{
  protected String varcla;

  bata(String s) {
    varcla = s; // varcla = new String(s)
  }
}
class clacla{
  public void rewrite(bata d)
  {
    d.varcla = "¡Hola a Java!";
  }
}

public class B218 {
  public static void main(String[] args) {
    bata d = new bata("¡Hola desde Java!");
    clacla c = new clacla();
    c.rewrite(d);
    System.out.println(d.varcla);
  }
}