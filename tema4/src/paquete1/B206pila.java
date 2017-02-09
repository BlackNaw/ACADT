package paquete1;
class stack {
  private int stack_data[];
   private int stack_ptr;
   stack (int size)
   {
    stack_data = new int[size];
    stack_ptr= -1;
   }
  public int pop()
  {
    if(stack_ptr==-1) return 0;
      else return stack_data[stack_ptr--];
    }
   public void push(int push_this)
   {
    /* if(stack_ptr >10) return 0;
     else {
       stack_data[++stack_ptr]=push_this;
       return 1;
      }
       */
	   if(stack_ptr <10) stack_data[++stack_ptr]=push_this;
    
   }
}
public class B206pila {
  public static void main(String[] args) {
    int popped_value;
    int tamano=10;
    System.out.println(tamano);
    stack stack1 = new stack(tamano);
    System.out.println("Añadiendo valores ahora ...");
    for(int i=0; i<tamano;i++){
      stack1.push(i);
      System.out.println("Valor añadido-->"+i);
         }
    System.out.println("Quitando valores ahora...");
    for(int i=0;i<10;i++){
      popped_value=stack1.pop();
      System.out.println("Valor quitado--->"+popped_value);
    }
  }
}