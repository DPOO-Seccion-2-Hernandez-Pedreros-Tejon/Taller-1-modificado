package src.modeloa;

public class Combo implements IProducto
{

	private double descuento;
	private String nombreCombo;
	private int precio = 0;
	
	public Combo(String nombreCo, double descuentoCo)
	{
		nombreCombo = nombreCo;
		descuento = descuentoCo;
	}
	public void agregarItemACombo(ProductoAjustado itemCombo)
	{
		precio += itemCombo.getPrecio() - descuento / 100 * itemCombo.getPrecio();	
	}
	
	public int getPrecio() 
	{	
		return precio; 
	}

	public String getNombre() 
	{
		return nombreCombo;
	}

	public String generarTextoFactura() 
	{
		return ("   " + nombreCombo + ": 		$" + Integer.toString(this.getPrecio()) + "\n");
	}

}
