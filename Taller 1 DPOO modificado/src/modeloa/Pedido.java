package src.modeloa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Pedido 
{
	public static int numeroPedidos = 0;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private ArrayList<IProducto> itemsPedido;
	private int caloriasPedido;
	
	public Pedido(String pnombreCliente, String direccion)
	{
		nombreCliente = pnombreCliente;
		direccionCliente = direccion;
		numeroPedidos += 1;
		idPedido = numeroPedidos;
		itemsPedido = new ArrayList<IProducto>();
		caloriasPedido = 0;
	}
	
	public void agregarProducto(IProducto producto)
	{
		getItemsPedido().add(producto);
		caloriasPedido = getCaloriasPedido() + producto.getCalorias();	
		}
	
	public int getIdPedido()
	{
		return idPedido;
	}
	

	private int getPrecioNeto()
	{
		int precio = 0;
		for(IProducto producto: getItemsPedido())
		{
			precio += producto.getPrecio();
		}
		return precio;
	}
	
	private int getPrecioIva()
	{
		return (int)(19.0 * this.getPrecioNeto() / 100.0);
	}
	
	private int getPrecioTotal()
	{
		return this.getPrecioNeto() + this.getPrecioIva();
	}
	
	public String generarTextoFactura()
	{
		String txtProductos = "";
		
		for(IProducto producto: getItemsPedido())
		{
			txtProductos += producto.generarTextoFactura();
		}
		
		return "	HAMBURGUESAS & HAMBURGUESAS\n"
				+ "\n"
				+ "   ID DEL PEDIDO:		" + Integer.toString(this.getIdPedido()) + "\n"
				+ "   NOMBRE DEL CLIENTE: 		" + nombreCliente + "\n"
				+ "   DIRECCI�N DE FACTURACI�N: 	" + direccionCliente + "\n"
				+ "\n"
				+ "   PRODUCTOS DE LA ORDEN\n"
				+ txtProductos + "\n"
				+ "   VALOR NETO DEL PEDIDO:	 $" + Integer.toString(this.getPrecioNeto()) + "\n"
				+ "   VALOR DEL IVA DEL PEDIDO:	 $" + Integer.toString(this.getPrecioIva()) + "\n"
				+ "   VALOR TOTAL DEL PEDIDO:	 $" + Integer.toString(this.getPrecioTotal()) + "\n\n"
				+ "   CANTIDAD TOTAL DE CALORIAS:	 " + Integer.toString(this.getCaloriasPedido()) + "\n\n\n";
	}
	
	public void guardarFactura() throws IOException
	{
		File file = new File("FACTURA#" + Integer.toString(idPedido) + ".txt");
		if(file.createNewFile())
		{
			System.out.println("File created: " + file.getName());
		}
		else
		{
			System.out.println("File already exists.");
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("FACTURA#" + Integer.toString(idPedido) + ".txt"));
		writer.write(this.generarTextoFactura());
		writer.close();
	}

	public int getCaloriasPedido() 
	{
		return caloriasPedido;
	}
	
	public boolean equals (Pedido pedido)
	{
		ArrayList<IProducto> items1 = this.getItemsPedido();
		ArrayList<IProducto> items2 = pedido.getItemsPedido();
		boolean iguales = true;
		for (IProducto item1: items1)
		{
			for (IProducto item2: items2)
			{
				if (!item1.equals(item2))
				{
					iguales = false;
					break;
				}
			}
		}
		
		return iguales;
	}

	public ArrayList<IProducto> getItemsPedido() {
		return itemsPedido;
	}
	
	
}