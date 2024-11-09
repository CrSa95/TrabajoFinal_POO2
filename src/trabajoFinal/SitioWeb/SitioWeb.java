package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SitioWeb {
	
	private List<Inmueble> inmuebles = new ArrayList<Inmueble>();
	private List<Categoria> todasLasCategoriasDeInmueble = new ArrayList<Categoria>();
	private List<Categoria> todasLasCategoriasDeInquilino= new ArrayList<Categoria>();
	private List<Categoria> todasLasCategoriasDePropietario = new ArrayList<Categoria>();
	private List<TipoDeServicio> todosLosTiposDeServicios = new ArrayList<TipoDeServicio>();
	private List<TipoDeInmueble> todosLosTiposDeInmueble = new ArrayList<TipoDeInmueble>();
	private List<Filtro> filtros = new ArrayList<Filtro>();
	
	public Boolean getCategoriaEspecificaInmueble(Categoria categoriaEspecifica){
		return this.todasLasCategoriasDeInmueble.stream()
		        .anyMatch(categoria -> categoria.nombreCategoria().equals(categoriaEspecifica.nombreCategoria()));
	}
	
	public Boolean getCategoriaEspecificaInquilino(Categoria categoriaEspecifica){
		return this.todasLasCategoriasDeInquilino.stream()
		        .anyMatch(categoria -> categoria.nombreCategoria().equals(categoriaEspecifica.nombreCategoria()));
	}
	
	public Boolean getCategoriaEspecificaPropietario(Categoria categoriaEspecifica){
		return this.todasLasCategoriasDePropietario.stream()
		        .anyMatch(categoria -> categoria.nombreCategoria().equals(categoriaEspecifica.nombreCategoria()));
	}
	
	public List<TipoDeServicio> seleccionarTiposDeServicio(List<TipoDeServicio> servicios){
		
		//Filtra elementos de tiposDeServicio que están presentes en servicios
		return todosLosTiposDeServicios.stream()
										.filter(servicios::contains)
										.collect(Collectors.toList());
	}
	
	public Boolean seleccionarTipoDeInmueble(TipoDeInmueble tipoDeInmueble) {
		
		return this.todosLosTiposDeInmueble.stream()
											.anyMatch(tipoInmueble -> tipoInmueble.equals(tipoDeInmueble));

	}
	
	public void altaInmueble(Inmueble inmueble) {
		this.inmuebles.add(inmueble);
	}
	
	//Metodos de administrador
	public void altaTipoDeServicio(TipoDeServicio tipoDeServicio) {
		this.todosLosTiposDeServicios.add(tipoDeServicio);
	}
	
	public void altaTipoDeInmueble(TipoDeInmueble tipoDeInmueble) {
		this.todosLosTiposDeInmueble.add(tipoDeInmueble);
	}
	
	public void altaTipoDeCategoriaInmueble(Categoria categoria) {
		this.todasLasCategoriasDeInmueble.add(categoria);
	}
	
	public void altaTipoDeCategoriaPropietario(Categoria categoria) {
		this.todasLasCategoriasDePropietario.add(categoria);
	}
	
	public void altaTipoDeCategoriaInquilino(Categoria categoria) {
		this.todasLasCategoriasDeInquilino.add(categoria);
	}
	
	public List<Inmueble> buscarInmuebles(String ciudad, LocalDateTime fechaEntrada, LocalDateTime fechaSalida, Integer capacidad, Double precioMinimo, 
			Double precioMaximo){


		filtros.add(new FiltroCiudad(ciudad));
		filtros.add(new FiltroFechas(fechaEntrada, fechaSalida));

		if (capacidad != null) {
			filtros.add(new FiltroCapacidad(capacidad));
		}

		if (precioMinimo != null || precioMaximo != null) {
			filtros.add(new FiltroPrecio(precioMinimo, precioMaximo));
		}

		List<Inmueble> resultado = new ArrayList<>(inmuebles);
		for (Filtro filtro : filtros) {
			resultado = filtro.filtrar(resultado);
		}
		return resultado;
	}
}
