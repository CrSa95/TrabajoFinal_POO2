package trabajoFinal.SitioWeb;

import java.time.LocalDate;

public class Reserva {
	private Usuario usuario;
	private LocalDate fechaDeIngreso;
	private LocalDate fechaDeEgreso;
	private String ciudad;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public LocalDate getFechaDeIngreso() {
		return fechaDeIngreso;
	}
	
	public LocalDate getFechaDeEgreso() {
		return fechaDeEgreso;
	}
	
	public String getCiudad() {
		return ciudad;
	}
	
	public void cancelarReserva() {
		
	}
}
