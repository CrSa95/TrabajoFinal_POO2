package trabajoFinal.SitioWeb;

import java.time.LocalDate;

public class Reserva {
	private Usuario usuario;
	private LocalDate fechaDeIngreso;
	private LocalDate fechaDeEgreso;
	private Inmueble inmueble;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public LocalDate getFechaDeIngreso() {
		return fechaDeIngreso;
	}
	
	public LocalDate getFechaDeEgreso() {
		return fechaDeEgreso;
	}
	
	public void cancelarReserva() {
		
	}
	
	public Inmueble getInmueble() {
		return this.inmueble;
	}
}
