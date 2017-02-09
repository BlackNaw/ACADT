import java.util.ArrayList;

public class Pizza {
	
	
	protected void crearPizza(ArrayList<FactoryIngredientes> ingredientes, TipoDePizza pais) {
		System.out.println("Pizza " + pais.name());
		for (FactoryIngredientes factoryIngredientes : ingredientes) {
			System.out.print("Ingrediente " + factoryIngredientes.getNombre() + "\t");
			System.out.println("Cantidad " + factoryIngredientes.getCantidad());
		}
	}

}
