import java.util.ArrayList;

public class PizzaFactory {
	public Pizza getPizza(TipoDePizza tipoPizza) {
		assert tipoPizza != null;
		if (tipoPizza == TipoDePizza.ESPAÑOLA) {
			return new PizzaEspañola(new ControladorCocineroEspañol().getIngredientes());
		} else if (tipoPizza == TipoDePizza.ITALIANA) {
			return new PizzaItaliana(new ControladorCocineroItaliano().getIngredientes());
		}
		return null;
	}

}
