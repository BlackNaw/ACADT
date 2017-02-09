import java.util.ArrayList;

public class PizzaEspañola extends Pizza{
	
	public PizzaEspañola(ArrayList<FactoryIngredientes> ingredientes) {
		super.crearPizza(ingredientes, TipoDePizza.ESPAÑOLA);
	}
	
	
}
