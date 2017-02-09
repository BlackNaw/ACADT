import java.util.ArrayList;

public class ControladorCocineroItaliano implements IControladorCocinero {

	private ArrayList<FactoryIngredientes> ingredientes=null;

	public ControladorCocineroItaliano() {
		if (ingredientes == null) {
			ingredientes = new ArrayList<FactoryIngredientes>();
			ingredientes.add(new QuesoMozzarellaItalian());
			ingredientes.add(new TomatePeraItalian());
		}
	}

	@Override
	public ArrayList<FactoryIngredientes> getIngredientes() {
		return ingredientes;
	}

}
