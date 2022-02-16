package src.modeloa;

import java.util.ArrayList;

public class ProductoAjustado implements IProducto
{
	
	private ArrayList<Ingrediente> agregados;
	private ArrayList<Ingrediente> eliminados;
	private int precioInicial;
	private String nombre;
	private int calorias;
	
	public ProductoAjustado( String pnombre, int precioBase, int calorias)
	{
		nombre = pnombre;
		agregados = new ArrayList<Ingrediente>();
		eliminados = new ArrayList<Ingrediente>();
		precioInicial = precioBase;
		this.calorias = calorias;
	}
	
	public void agregarIngrediente(Ingrediente ingrediente)
	{
		agregados.add(ingrediente);
	}
	
	public void eliminarIngrediente(Ingrediente ingrediente)
	{
		eliminados.add(ingrediente);
	}
	
	public int getPrecio() 
	{	
		int precioAdicional = 0;
		for(Ingrediente ingrediente: agregados)
		{
			precioAdicional += ingrediente.getCosto();
		}
		return precioInicial + precioAdicional;
	}

	public String getNombre() 
	{
		String nombreAdicional = "";
		for(Ingrediente ingrediente: agregados)
		{
			nombreAdicional += (" con " + ingrediente.getNombre()); 
		}
		for(Ingrediente ingrediente: eliminados)
		{
			nombreAdicional += (" sin " + ingrediente.getNombre()); 
		}
		return nombre + nombreAdicional;
	}
	
	public int getCalorias()
	{
		return calorias;
	}
	public String generarTextoFactura() 
	{
		int calordias = this.getCalorias();
		String mensaje = "   " + this.getNombre() + ": 		$" + Integer.toString(this.getPrecio()) + "      Calorias 	:" + Integer.toString(calordias) + "\n";
		return (mensaje);
	}
	
	public boolean equals(IProducto producto)
	{
		if (this.getNombre() == producto.getNombre()) 
		{
			return true;
		}
		else return false;
	}
}