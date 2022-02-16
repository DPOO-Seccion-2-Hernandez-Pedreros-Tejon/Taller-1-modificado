package src.modeloa;

public interface IProducto 
{
	public int getPrecio();
	
	public String getNombre();
	
	public String generarTextoFactura(); 
	
	public int getCalorias();
	
	public boolean equals(IProducto producto);
}