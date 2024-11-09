package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
