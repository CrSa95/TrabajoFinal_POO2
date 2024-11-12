package trabajoFinal.SitioWeb;

import java.time.LocalDate;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Reserva {

	private Inmueble inmueble;
	private Usuario inquilino;
	private FormaDePago formaDePago;
	private LocalDate fechaDeIngreso;
	private LocalDate fechaDeEgreso;
	private EstadoDeReserva estadoDeReserva;
	private List<Reserva> reservasCondicionales = new ArrayList<Reserva>();
	private Manager manager;
	
	public Reserva(Inmueble inmueble, Usuario inquilino, FormaDePago formaDePago, LocalDate fechaDeIngreso,LocalDate fechaDeEgreso) {
		this.inmueble = inmueble;
		this.inquilino = inquilino;
		this.formaDePago = formaDePago;
		this.fechaDeIngreso = fechaDeIngreso;
		this.fechaDeEgreso = fechaDeEgreso;
	} 
	
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	public Manager getManager() {
		return this.manager;
	}
	
	public FormaDePago getFormaDePago() {
		return this.formaDePago;
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
		this.manager.cancelacionDeReserva(this.inmueble);
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
			this.inmueble.agregarReserva(this);
			this.inquilino.registrarReserva(this);
			this.estadoDeReserva = new EstadoConfirmada();
			this.inmueble.sumarCantidadDeVecesAlquilado();
			this.inmueble.getPropietario().sumarCantidadDeVecesQueAlquile();
			this.manager.altaDeReserva();
		}
	}

	public Optional<Reserva> obtenerReservaCondicional(Inmueble inmueble) {
		return this.reservasCondicionales.stream().filter(unaReserva -> unaReserva.getInmueble()
												.equals(inmueble)).findFirst();
	}

	public void setEstadoDeReserva(EstadoDeReserva estadoDeReserva) {
		this.estadoDeReserva = estadoDeReserva;
	}

	public EstadoDeReserva getEstadoDeReserva() {
		return this.estadoDeReserva;
	}
}
