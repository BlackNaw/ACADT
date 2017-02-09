import java.util.ArrayList;

public class PizzaItaliana extends Pizza{

	public PizzaItaliana(ArrayList<FactoryIngredientes> ingredientes) {
		super.crearPizza(ingredientes, TipoDePizza.ITALIANA);
	}
	
}
