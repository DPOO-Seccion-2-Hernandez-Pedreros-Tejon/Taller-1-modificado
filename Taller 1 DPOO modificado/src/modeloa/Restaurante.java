package src.modeloa;

import java.io.BufferedReader;
/**import java.io.File;*/
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Restaurante 
{
	
	private ArrayList<Pedido> pedidos;
	
	private Pedido pedidoEnCurso;
	
	private ArrayList<Ingrediente> ingredientes;
	
	private ArrayList<ProductoAjustado> productosMenu;
	
	private ArrayList<Combo> combos;
	
	private ArrayList<ProductoAjustado> bebidas;
	
	public Restaurante() 
	{
		pedidos = new ArrayList<Pedido>();
		ingredientes = new ArrayList<Ingrediente>();
		productosMenu = new ArrayList<ProductoAjustado>();
		combos = new ArrayList<Combo>();
		bebidas = new ArrayList<ProductoAjustado>();
		pedidoEnCurso = null;
	}
	
	public void iniciarPedido(String nombreCliente, String direccionCliente)
	{	
		pedidoEnCurso = new Pedido(nombreCliente, direccionCliente);
	}
	
	public void cargarInformacionRestaurante() throws FileNotFoundException, IOException
	{
		 cargarIngredientes();
		 
		 cargarMenu();
		 
		 cargarCombos();
		 
		 cargarBebidas();
		 
	}
	
	private void cargarIngredientes() throws IOException,FileNotFoundException
	{
		
		FileReader file = new FileReader("data/ingredientes.txt");
		BufferedReader br = new BufferedReader(file);
		String message = "";
		String line = br.readLine();
		while(line != null)
		{
			String[] ingredienteCosto = line.split(";");
			ingredientes.add( new Ingrediente(ingredienteCosto[0], Integer.parseInt(ingredienteCosto[1])) );
			line = br.readLine();
		}
		System.out.println(message);
		br.close();
		
	}
	
	
	private void cargarMenu() throws IOException, FileNotFoundException
	{
		
		FileReader file = new FileReader("Data/menu.txt");
		BufferedReader br = new BufferedReader(file);
		String message = "";
		String line = br.readLine();
		while(line != null)
		{
			String[] productoCosto = line.split(";");
			productosMenu.add( new ProductoAjustado(productoCosto[0], Integer.parseInt(productoCosto[1]), Integer.parseInt(productoCosto[2])) );
			line = br.readLine();
		}
		System.out.println(message);
		br.close();
		
	}
	
	private void cargarBebidas() throws IOException, FileNotFoundException
	{
		
		FileReader file = new FileReader("Data/bebidas.txt");
		BufferedReader br = new BufferedReader(file);
		String message = "";
		String line = br.readLine();
		while(line != null)
		{
			String[] productoCosto = line.split(";");
			bebidas.add( new ProductoAjustado(productoCosto[0], Integer.parseInt(productoCosto[1]), Integer.parseInt(productoCosto[2]))  );
			line = br.readLine();
		}
		System.out.println(message);
		br.close();
		
	}
	
	private void cargarCombos() throws IOException, FileNotFoundException
	{
		
		FileReader file = new FileReader("Data/combos.txt");
		BufferedReader br = new BufferedReader(file);
		String message = "";
		String line = br.readLine();
		while(line != null)
		{
			String lineacombo = line.replace("%", "");
			String[] comboDescuentoIngredientes = lineacombo.split(";");
			String nombreCombo = comboDescuentoIngredientes[0];
			String descuentoCombo = comboDescuentoIngredientes[1];
			int i = 2;
			int max = comboDescuentoIngredientes.length;
			
			Combo combo = new Combo(nombreCombo, Double.parseDouble(descuentoCombo));
			
			while(i < max)
			{
				String prod = comboDescuentoIngredientes[i];
				for(ProductoAjustado producto: productosMenu)
				{
					if(producto.getNombre().equals(prod))  
					{
						combo.agregarItemACombo(producto);
						break;
					}
				}
				i ++;
			}
			
			combos.add(combo);
			
			line = br.readLine();
		}
		System.out.println(message);
		br.close();
		
	}
	
	public Pedido getPedidoEnCurso()
	{
		return pedidoEnCurso;
	}
	
	public IProducto hallarProducto(int noProducto)
	{
		if(productosMenu.size() <= noProducto)
		{
			noProducto -= productosMenu.size();
			return combos.get(noProducto);
		}
		
		else
		{
			boolean seguir = true;
			ProductoAjustado agregar = productosMenu.get(noProducto); 
			ProductoAjustado producto = new ProductoAjustado(agregar.getNombre(), agregar.getPrecio(), agregar.getCalorias());
			while(seguir)
			{
				System.out.println("Quieres hacer una modificación a este producto");
				System.out.println("\n1. Sí");
				System.out.println("2. No");
				String modificar = input("Seleccione una opción: ");
			
				if(modificar.equals("1"))
				{
					System.out.println("Cómo quieres modificar este producto");
					System.out.println("\n1. Agregar ingredientes (costo adicional)");
					System.out.println("2. Eliminar ingredientes");
					modificar = input("Seleccione una opción: ");
				
					if(modificar.equals("1"))
					{
						this.mostrarIngredientesConPrecio();
						String noIngrediente = input("Escoja el número del ingrediente a agregar");
						Ingrediente ingrediente = ingredientes.get(Integer.parseInt(noIngrediente) - 1);
						producto.agregarIngrediente(ingrediente);
						System.out.println("\nSe agregó correctamente el ingrediente\n");
					}
					else
					{
						this.mostrarIngredientesSinPrecio();
						String noIngrediente = input("Escoja el número del ingrediente a eliminar");
						Ingrediente ingrediente = ingredientes.get(Integer.parseInt(noIngrediente) - 1);
						producto.eliminarIngrediente(ingrediente);
						System.out.println("\nSe eliminó correctamente el ingrediente\n");
					}
				}
				else
				{
					
					seguir = false;
				}
			}
			return producto;
		}
	}
	
	public IProducto hallarBebida(int noProducto)
	{
		if(bebidas.size() <= noProducto)
		{
			noProducto -= bebidas.size();
			return combos.get(noProducto);
		}
		
		else
		{
			
			ProductoAjustado agregar = bebidas.get(noProducto); 
			ProductoAjustado producto = new ProductoAjustado(agregar.getNombre(), agregar.getPrecio(), agregar.getCalorias());
			return producto;
		}
	}
	
	public void mostrarProductos()
	{
		System.out.println("\nPRODUCTOS DEL MEN�\n");
		
		int i = 0;
		for(ProductoAjustado producto: productosMenu)
		{
			i += 1;
			System.out.println(Integer.toString(i) + ". " + producto.getNombre() + "     $" + Integer.toString(producto.getPrecio()) );
		}
		
		System.out.println("\nCOMBOS\n");
		
		for(Combo combo: combos)
		{
			i += 1;
			System.out.println(Integer.toString(i) + ". " + combo.getNombre() + "     $" + Integer.toString(combo.getPrecio()) );
		}
		
		System.out.println("\nBebidas\n");
		
		for(ProductoAjustado bebida: bebidas)
		{
			i += 1;
			System.out.println(Integer.toString(i) + ". " + bebida.getNombre() + "     $" + Integer.toString(bebida.getPrecio()) );
		}
	}
	
	public void mostrarBebidas()
	{
		System.out.println("\nBebidas DEL MEN�\n");
		
		int i = 0;
		for(ProductoAjustado bebida: bebidas)
		{
			i += 1;
			System.out.println(Integer.toString(i) + ". " + bebida.getNombre() + "     $" + Integer.toString(bebida.getPrecio()) );
		}
		
		
	}
	public void mostrarIngredientesConPrecio()
	{
		System.out.println("\nINGREDIENTES DEL MEN�\n");
		
		int i = 0;
		for(Ingrediente ingrediente: ingredientes)
		{
			i += 1;
			System.out.println(Integer.toString(i) + ". " + ingrediente.getNombre() + "     $" + Integer.toString(ingrediente.getCosto()) );
		}
	}
	
	public void mostrarIngredientesSinPrecio()
	{
		System.out.println("\nINGREDIENTES DEL MEN�\n");
		
		int i = 0;
		for(Ingrediente ingrediente: ingredientes)
		{
			i += 1;
			System.out.println(Integer.toString(i) + ". " + ingrediente.getNombre());
		}
	}
	
	public void cerrarYGuardarPedido() throws IOException
	{
		if(pedidoEnCurso == null)
		{
			System.out.println("\nTiene que tener un pedido en curso para agregar productos\n");
			return;
		}
		
		for (Pedido pedidosP: pedidos)
		{
			if (pedidosP.equals(pedidoEnCurso))
			{
				int id = pedidosP.getIdPedido();
				System.out.println( "\n El pedido antiguo con id " + Integer.toString(id) 
				+ " es idéntico al pedido actual.\n");
			}
		}
		
		pedidos.add(pedidoEnCurso);
		pedidoEnCurso.guardarFactura();
		System.out.println("\nSe ha guardado la factura y se ha cerrado el pedido\n");
		pedidoEnCurso = null;
	}
	
	public void buscarPedido(int id)
	{
		Pedido pedidoBuscado = null;
		
		for(Pedido pedido: pedidos)
		{
			if(((Integer)pedido.getIdPedido()).equals(id))
			{
				pedidoBuscado = pedido;
				break;
			}
		}
		
		if(pedidoBuscado == null)
		{
			System.out.println("\nNo se encontr� un pedido con ese c�digo ID\n");
		}
		
		else
		{
			System.out.println(pedidoBuscado.generarTextoFactura());
		}
	}
	
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
}
