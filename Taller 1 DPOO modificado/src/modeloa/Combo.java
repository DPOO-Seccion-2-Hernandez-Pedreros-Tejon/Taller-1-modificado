package src.modeloa;

public class Combo implements IProducto
{

	private double descuento;
	private String nombreCombo;
	private int precio = 0;
	private int calorias;
	
	public Combo(String nombreCo, double descuentoCo)
	{
		nombreCombo = nombreCo;
		descuento = descuentoCo;
		calorias = 0;
	}
	public void agregarItemACombo(ProductoAjustado itemCombo)
	{
		precio += itemCombo.getPrecio() - descuento / 100 * itemCombo.getPrecio();	
		calorias += itemCombo.getCalorias();
	}
	
	public int getPrecio() 
	{	
		return precio; 
	}
	public int getCalorias()
	{
		return calorias;
	}
	public String getNombre() 
	{
		return nombreCombo;
	}

	public String generarTextoFactura() 
	{	
		int calordias = this.getCalorias();
		return ("   " + nombreCombo + ": 		$" + Integer.toString(this.getPrecio()) + "      Calorias 	:" + Integer.toString(calordias) + "\n");
	}

}