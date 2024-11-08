package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SitioWeb {
	private List<Reserva> reservas = new ArrayList<Reserva>();
	private List<Inmueble> inmuebles = new ArrayList<Inmueble>();
	private List<Filtro> filtros = new ArrayList<Filtro>();
	
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
	
}
