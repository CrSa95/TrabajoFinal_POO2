package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Reserva {

	private Inmueble inmueble;
	private Usuario inquilino;
	private FormaDePago formaDePago;
	private LocalDate fechaDeIngreso;
	private LocalDate fechaDeEgreso;
	private EstadoDeReserva estadoDeReserva;
	private PriorityQueue<Reserva> reservasCondicionales = new PriorityQueue<Reserva>();
	
	public Reserva(Inmueble inmueble, Usuario inquilino, FormaDePago formaDePago, LocalDate fechaDeIngreso,LocalDate fechaDeEgreso) {
		this.inmueble = inmueble;
		this.inquilino = inquilino;
		this.formaDePago = formaDePago;
		this.fechaDeIngreso = fechaDeIngreso;
		this.fechaDeEgreso = fechaDeEgreso;
	}
	
	public Usuario getUsuario() {
		return this.inquilino;
	}
	
	public LocalDate getFechaDeIngreso() {
		return this.fechaDeIngreso;
	}
	
	public LocalDate getFechaDeEgreso() {
		return this.fechaDeEgreso;
	}
	
	public void finalizarReserva() {
		this.estadoDeReserva.finalizar(this);
	}
  
	public void cancelarReserva() {
		this.estadoDeReserva.cancelar(this);
	}

	public Inmueble getInmueble() {
		return this.inmueble;
	}
	
	public void evaluarReserva() {
		if (inmueble.estaReservado(this)) {
			this.reservasCondicionales.add(this);
			this.estadoDeReserva = new EstadoCondicional();
		}
		else {
			inmueble.agregarReserva(this);
			this.estadoDeReserva = new EstadoConfirmada();
		}
	}
	
	public Optional<Reserva> obtenerReservaCondicional(Inmueble inmueble) {
		return this.reservasCondicionales.stream().filter(unaReserva -> unaReserva.getInmueble()
												.equals(inmueble)).findFirst();
	}
	
	public void setEstadoDeReserva(EstadoDeReserva estadoDeReserva) {
		this.estadoDeReserva = estadoDeReserva;
	}
	
}
