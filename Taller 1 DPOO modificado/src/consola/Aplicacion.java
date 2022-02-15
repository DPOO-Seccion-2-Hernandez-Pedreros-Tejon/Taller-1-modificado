package src.consola;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import src.modeloa.IProducto;
import src.modeloa.Restaurante;

public class Aplicacion 
{
	
	private Restaurante rest = new Restaurante();
	
	public void ejecutarAplicacion() throws FileNotFoundException, IOException
	{
		System.out.println("Hamburguesas & Hamburguesas\n");

		boolean continuar = true;
		rest.cargarInformacionRestaurante();
		while (continuar)
		{
			try
			{
				mostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opcion"));
				if (opcion_seleccionada == 1)
					rest.mostrarProductos();
				else if (opcion_seleccionada == 2)
					iniciarPedido();
				else if (opcion_seleccionada == 3)
					agregarProducto();
				else if (opcion_seleccionada == 4)
					agregarBebida();
				else if (opcion_seleccionada == 5)
					rest.cerrarYGuardarPedido();
				else if (opcion_seleccionada == 6)
					buscarPedido();
				else if (opcion_seleccionada == 0)	
				{
					System.out.println("Saliendo de la aplicacion ...");
					continuar = false;
				}
				
				else
				{
					System.out.println("Por favor seleccione una opci�n válida.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los n�meros de las opciones.");
			}
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

	/**
	 * Muestra al usuario el menú con las opciones para que escoja la siguiente
	 * acción que quiere ejecutar.
	 */
	public void mostrarMenu()
	{
		System.out.println("\nOpciones de la aplicacion\n");
		System.out.println("1. Mostrar menu");
		System.out.println("2. Iniciar un nuevo pedido");
		System.out.println("3. Agregar un elemento a un pedido");
		System.out.println("4. Elegir o agregar una bebida en un pedido");
		System.out.println("5. Cerrar un pedido y guardar la factura");
		System.out.println("6. Consultar la informacion de un pedido dado su id");
		System.out.println("0. Salir de la aplicacion");
	}
	
	public void iniciarPedido()
	{
		String nombre = input("Ingrese su nombre para el pedido");
		String direccion = input("Ingrese su direccion para el pedido");
		rest.iniciarPedido(nombre, direccion);
		System.out.println("\nSe ha iniciado un nuevo pedido para " + nombre + " en la direccion: " + direccion);
	}
	
	public void agregarProducto()
	{
		if(rest.getPedidoEnCurso() == null)
		{
			System.out.println("\nTiene que tener un pedido en curso para agregar productos\n");
			return;
		}
		rest.mostrarProductos();
		String noProducto = input("\nIngrese el numero del producto que quiere agregar (si quiere volver al men�, ingrese 0)");
		if(noProducto.equals("0"))
		{
			return;
		}
		IProducto producto = rest.hallarProducto(Integer.parseInt(noProducto) - 1);
		rest.getPedidoEnCurso().agregarProducto(producto);
		System.out.println("\nSe ha agregado el producto al pedido\n");
	}
	
	public void agregarBebida()
	{
		if(rest.getPedidoEnCurso() == null)
		{
			System.out.println("\nTiene que tener un pedido en curso para agregar productos\n");
			return;
		}
		rest.mostrarBebidas();
		String noProducto = input("\nIngrese el n�mero del producto que quiere agregar (si quiere volver al men�, ingrese 0)");
		if(noProducto.equals("0"))
		{
			return;
		}
		IProducto producto = rest.hallarBebida(Integer.parseInt(noProducto) - 1);
		rest.getPedidoEnCurso().agregarProducto(producto);
		System.out.println("\nSe ha agregado el producto al pedido\n");
	}
	
	public void buscarPedido()
	{
		int id = Integer.parseInt(input("Ingrese el id de un pedido"));
		rest.buscarPedido(id);
	}
	

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Aplicacion app = new Aplicacion();
		app.ejecutarAplicacion();
	}

}
