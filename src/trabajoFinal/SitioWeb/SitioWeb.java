package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SitioWeb {

	private List<Usuario> todosLosUsuarios = new ArrayList<>();
	private List<Inmueble> inmuebles = new ArrayList<>();
	private List<Categoria> todasLasCategoriasDeInmueble = new ArrayList<>();
	private List<Categoria> todasLasCategoriasDeInquilino= new ArrayList<>();
	private List<Categoria> todasLasCategoriasDePropietario = new ArrayList<>();
	private List<TipoDeServicio> todosLosTiposDeServicios = new ArrayList<>();
	private List<TipoDeInmueble> todosLosTiposDeInmueble = new ArrayList<>();
	private List<Filtro> filtros = new ArrayList<>();

	public void registrarUsuario(Usuario usuario) {
		this.todosLosUsuarios.add(usuario);
		usuario.registrarEnSitioWeb(this); 
	}

	public List<Usuario> getUsuariosRegistrados() {
		return this.todosLosUsuarios;
	}

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

	public Optional<TipoDeInmueble> seleccionarTipoDeInmueble(TipoDeInmueble tipoDeInmueble) {

		return this.todosLosTiposDeInmueble.stream()
				   .filter(tipoInmueble -> tipoInmueble.getTipoDeInmueble().equals(tipoDeInmueble.getTipoDeInmueble())).findFirst();

	} 

	public void altaInmueble(Inmueble inmueble) {
		this.inmuebles.add(inmueble);
	}
	
	public List<Inmueble> getTodosLosInmuebles() {
		return this.inmuebles;
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
	
	public List<Inmueble> buscarInmuebles(String ciudad, LocalDate fechaEntrada, LocalDate fechaSalida, int capacidad, double precioMinimo,
			double precioMaximo){


		filtros.add(new FiltroCiudad(ciudad));
		filtros.add(new FiltroFechas(fechaEntrada, fechaSalida));

		if (capacidad != 0) {
			filtros.add(new FiltroCapacidad(capacidad));
		}

		if (precioMinimo != 0 || precioMaximo != 0) {
			filtros.add(new FiltroPrecio(precioMinimo, precioMaximo));
		}

		List<Inmueble> resultado = this.inmuebles;
		for (Filtro filtro : filtros) {
			resultado = filtro.filtrar(resultado);
		}
		return resultado;
	}
}
