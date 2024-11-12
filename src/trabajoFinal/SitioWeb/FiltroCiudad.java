package trabajoFinal.SitioWeb;

import java.util.List;

public class FiltroCiudad implements Filtro{

	private String ciudad;


	public FiltroCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	@Override
	public List<Inmueble> filtrar(List<Inmueble> inmuebles) {
		return inmuebles.stream()
		        .filter(inmueble -> inmueble.getCiudad().equalsIgnoreCase(ciudad))
		        .toList();
	}
}