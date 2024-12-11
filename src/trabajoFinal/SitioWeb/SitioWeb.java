package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.List;
import java.util.stream.Collectors;

public class SitioWeb {

	private List<Usuario> todosLosUsuarios = new ArrayList<>();
	private List<Inmueble> inmuebles = new ArrayList<>();
	private List<Categoria> todasLasCategoriasDeInmueble = new ArrayList<>();
	private List<Categoria> todasLasCategoriasDeInquilino= new ArrayList<>();
	private List<Categoria> todasLasCategoriasDePropietario = new ArrayList<>();
	private List<TipoDeServicio> todosLosTiposDeServicios = new ArrayList<>();
	private List<TipoDeInmueble> todosLosTiposDeInmueble = new ArrayList<>();

	public void registrarUsuario(Usuario usuario) {
		this.todosLosUsuarios.add(usuario);
		usuario.registrarEnSitioWeb(this); 
	}

	public List<Usuario> getUsuariosRegistrados() {
		return this.todosLosUsuarios;
	}
	
	public List<TipoDeServicio> getTiposDeServicios(){
		return this.todosLosTiposDeServicios;
	}
	
	public List<TipoDeInmueble> getTiposDeInmuebles(){
		return this.todosLosTiposDeInmueble;
	}
	
	public void estaCategoriaEspecificaInmueble(Categoria categoriaEspecifica) throws Exception{ 
		this.todasLasCategoriasDeInmueble.stream()
		        						 .filter(categoria -> categoria.nombreCategoria().equals(categoriaEspecifica.nombreCategoria()))
		        						 .findFirst()
		        						 .orElseThrow(() -> new Exception("Error: La categoria" + categoriaEspecifica.nombreCategoria() + "es incorrecta.")); 
	}

	public void estaCategoriaEspecificaInquilino(Categoria categoriaEspecifica) throws Exception{
		this.todasLasCategoriasDeInquilino.stream()
		   								  .filter(categoria -> categoria.nombreCategoria().equals(categoriaEspecifica.nombreCategoria()))
		        						  .findFirst()
				        				  .orElseThrow(() -> new Exception("Error: La categoria " + categoriaEspecifica.nombreCategoria() + "es incorrecta."));
	}

	public void estaCategoriaEspecificaPropietario(Categoria categoriaEspecifica) throws Exception{
		this.todasLasCategoriasDePropietario.stream()
		        							.filter(categoria -> categoria.nombreCategoria().equals(categoriaEspecifica.nombreCategoria()))
		        							.findFirst()
		        							.orElseThrow(() -> new Exception("Error: La categoria" + categoriaEspecifica.nombreCategoria() + "es incorrecta."));
	}


	public void altaInmueble(Inmueble inmueble) {
		this.inmuebles.add(inmueble);
	}
	
	public List<Inmueble> getTodosLosInmuebles() {
		return this.inmuebles;
	}
	
	public List<Inmueble> buscarInmuebles(Busqueda busqueda){
		return busqueda.aplicarFiltros(this.inmuebles);
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
	
	public double calcularTasaOcupacion() {
        long inmueblesOcupados = inmuebles.stream()
            .filter(inmueble -> inmueble.estaAlquiladoActualmente())
            .count();
        return (double) inmueblesOcupados / inmuebles.size();
    }
	
	public List<Usuario> obtenerTopTresInquilinoQueMasAlquilaron() {
        return this.todosLosUsuarios.stream()
                .sorted((u1, u2) -> Long.compare(this.cantidadDeVecesQueAlquiloUnInquilino(u2), this.cantidadDeVecesQueAlquiloUnInquilino(u1)))
                .limit(3)
                .collect(Collectors.toList());
    }
	
	public List<Reserva> getReservasFuturas(LocalDate fechaActual, Usuario usuario){
		return usuario.getReservas().stream()
		        .filter(reserva -> reserva.getFechaDeIngreso().isAfter(fechaActual))
		        .collect(Collectors.toList());
	}


	public List<Reserva> getReservasEnCiudad(String ciudad, Usuario usuario){
		return usuario.getReservas().stream()
		        .filter(reserva -> reserva.getInmueble().getCiudad().equalsIgnoreCase(ciudad))
		        .collect(Collectors.toList());
	}


	public List<String> getCiudadesReservadas(Usuario usuario){
		return usuario.getReservas().stream()
					.filter(reserva -> reserva.getInquilino().equals(usuario))
	            	.map(reserva -> reserva.getInmueble().getCiudad())
	            	.distinct()
	            	.collect(Collectors.toList());
	}
	
	public long cantidadDeVecesQueAlquiloUnPropietarioSusInmuebles(Usuario usuario) { //
	    return usuario.getReservas().stream()
	               .filter(reserva -> reserva.getEstadoDeReserva().esFinalizado())
	               .count();
	}

	public long cantidadDeVecesQueUnPropietarioAlquiloUnInmueble(Inmueble inmueble, Usuario usuario) { //
	    return inmueble.getReservas().stream()
	               .filter(reserva -> reserva.getEstadoDeReserva().esFinalizado() 
	                                  && inmueble.getPropietario() == usuario)
	               .count();
	}

	public long cantidadDeVecesQueAlquiloUnInquilino(Usuario usuario) {
	    return usuario.getReservas().stream()
	               .filter(reserva -> reserva.getEstadoDeReserva().esFinalizado())
	               .count();
	}
}
