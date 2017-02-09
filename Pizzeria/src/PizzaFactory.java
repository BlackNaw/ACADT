import java.util.ArrayList;

public class PizzaFactory {
	public Pizza getPizza(TipoDePizza tipoPizza) {
		assert tipoPizza != null;
		if (tipoPizza == TipoDePizza.ESPA�OLA) {
			return new PizzaEspa�ola(new ControladorCocineroEspa�ol().getIngredientes());
		} else if (tipoPizza == TipoDePizza.ITALIANA) {
			return new PizzaItaliana(new ControladorCocineroItaliano().getIngredientes());
		}
		return null;
	}

}
