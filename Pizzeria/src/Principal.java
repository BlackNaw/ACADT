
public class Principal {

	public static void main(String[] args) {
		PizzaFactory pizzaFactory = new PizzaFactory();
		Pizza pizza= pizzaFactory.getPizza(TipoDePizza.ITALIANA);
		
		
	}

}
