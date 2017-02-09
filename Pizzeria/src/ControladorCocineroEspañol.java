import java.util.ArrayList;

public class ControladorCocineroEspañol implements IControladorCocinero {

	private ArrayList<FactoryIngredientes> ingredientes = null;

	public ControladorCocineroEspañol() {
		if (ingredientes == null) {
			ingredientes = new ArrayList<FactoryIngredientes>();
			ingredientes.add(new QuesoMozzarellaSpain());
			ingredientes.add(new TomatePeraSpain());
		}
	}

	@Override
	public ArrayList<FactoryIngredientes> getIngredientes() {
		return ingredientes;
	}

}
