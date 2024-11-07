package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SitioWeb {
	private List<Reserva> reservas = new ArrayList<Reserva>();
	private List<Inmueble> inmuebles = new ArrayList<Inmueble>();
	private List<Categoria> categorias = new ArrayList<Categoria>();
	private List<TipoDeServicio> todosLosTiposDeServicios = new ArrayList<TipoDeServicio>();
	private List<TipoDeInmueble> todosLosTiposDeInmueble = new ArrayList<TipoDeInmueble>();
	private List<FormaDePago> todasLasFormasDePago = new ArrayList<FormaDePago>();
	
	public Boolean getCategoriaEspecifica(Categoria categoriaEspecifica){
		return this.categorias.stream()
		        .anyMatch(categoria -> categoria.nombreCategoria().equals(categoriaEspecifica.nombreCategoria()));
	}
	
	public List<Reserva> getReservas(Usuario usuario){
		return reservas.stream()
		        .filter(reserva -> reserva.getUsuario().equals(usuario))
		        .collect(Collectors.toList());
	}
	
	public List<Reserva> getReservasFuturas(Usuario usuario, LocalDate fechaActual){
		return reservas.stream()
		        .filter(reserva -> reserva.getUsuario().equals(usuario) 
		        				   && 
		        				   reserva.getFechaDeIngreso().isAfter(fechaActual))
		        .collect(Collectors.toList());
	}
	
	public List<Reserva> getReservasEnCiudad(Usuario usuario, String ciudad){
		return reservas.stream()
		        .filter(reserva -> reserva.getUsuario().equals(usuario) 
		        				   && 
		        				   reserva.getCiudad().equalsIgnoreCase(ciudad))
		        .collect(Collectors.toList());
	}
	
	public List<String> getCiudadesReservadas(Usuario usuario){
		return reservas.stream()
		        .map(Reserva::getCiudad)
		        .collect(Collectors.toList());
	}
	
	public List<String> seleccionarTiposDeServicio(List<String> servicios){
		
		//Filtra elementos de tiposDeServicio que están presentes en servicios
		return this.todosLosTiposDeServicios.stream()
											.map(TipoDeServicio::getTipoDeServicio)
			    							.filter(servicios::contains)
			    							.collect(Collectors.toList());
	}
	
	public List<FormaDePago> seleccionarFormasDePago(List<FormaDePago> formasDePago){
		
		//Filtra elementos de todasLasFormasDePago que están presentes en formasDePago
		return this.todasLasFormasDePago.stream()
                						.filter(formasDePago::contains)
                						.collect(Collectors.toList());
	}
	
	public Optional<String> seleccionarTipoDeInmueble(String tipoDeInmueble) {
		
		return this.todosLosTiposDeInmueble.stream()
										   .map(TipoDeInmueble::getTipoDeInmueble)
			    						   .filter(tipoInmueble -> tipoInmueble.equals(tipoDeInmueble))
			    						   .findFirst();

	}
	
	public void altaInmueble(Inmueble inmueble) {
		this.inmuebles.add(inmueble);
	}
	
	public void setTiposDeServicios(List<TipoDeServicio> todasLosTiposDeServicios) {
		this.todosLosTiposDeServicios = todasLosTiposDeServicios;
	}
	

	public void setFormasDePago(List<FormaDePago> todasLasFormasDePago) {
		this.todasLasFormasDePago = todasLasFormasDePago;
	}
	
	public void setTiposDeInmueble(List<TipoDeInmueble> todosLosTiposDeInmueble) {
		this.todosLosTiposDeInmueble = todosLosTiposDeInmueble;
	}
	
}
