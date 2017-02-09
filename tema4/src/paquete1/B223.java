package paquete1;
class ArrayFactory
{
  public int[] getNewArray()
      {
         int array[]={1, 2, 3, 4, 5};
         return array;
      }
}

public class B223 {
  public static void main(String[] args) {
    ArrayFactory af= new ArrayFactory();
    int array[]=af.getNewArray();
    for( int i=0; i<array.length; i++){System.out.println("array["+i+"] = "+ array[i]);}
    array[2]=7;
    for( int i=0; i<array.length; i++){System.out.println("array["+i+"] = "+ array[i]);}
    int pepe[]=af.getNewArray();
    for( int i=0; i<array.length; i++){System.out.println("array["+i+"] = "+ array[i]);}
    for( int i=0; i<pepe.length; i++){System.out.println("pepe["+i+"] = "+ pepe[i]);}
  }
}