package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {
	
	private Inmueble inmueble;
	private Usuario inquilino;
	private FormaDePago formaDePago;
	private LocalDateTime fechaDeIngreso;
	private LocalDateTime fechaDeEgreso;
	private EstadoDeReserva estadoDeReserva;
	private Reserva reserva;
	
	
	public Reserva(Inmueble inmueble, Usuario inquilino, FormaDePago formaDePago, LocalDateTime fechaDeIngreso,LocalDateTime fechaDeEgreso) {
		this.inmueble = inmueble;
		this.inquilino = inquilino;
		this.formaDePago = formaDePago;
		this.fechaDeIngreso = fechaDeIngreso;
		this.fechaDeEgreso = fechaDeEgreso;
		this.estadoDeReserva = new EstadoConfirmada();
	}
	
	public Usuario getInquilino() {
		return this.inquilino;
	}
	
	public LocalDateTime getFechaDeIngreso() {
		return this.fechaDeIngreso;
	}
	
	public LocalDateTime getFechaDeEgreso() {
		return this.fechaDeEgreso;
	}
	
	public void finalizarReserva() {
		this.estadoDeReserva.finalizar(reserva);
	}
	
	public void cancelarReserva() {
		this.estadoDeReserva.cancelar(reserva);
	}

	public Inmueble getInmueble() {
		return this.inmueble;
	}

	public void setEstadoDeReserva(EstadoDeReserva estadoDeReserva) {
		this.estadoDeReserva = estadoDeReserva;
	}
}
