package trabajoFinal.SitioWeb;

import java.time.LocalDateTime;

public class SolicitudDeReserva{

	private Inmueble inmueble;
	private Usuario inquilino;
	private FormaDePago formaDePago;
	private LocalDateTime fechaDeIngreso;
	private LocalDateTime fechaDeEgreso;
	private EstadoDeSolicitud estadoDeSolicitud;
	private Reserva reserva;
	
	public SolicitudDeReserva(Inmueble inmueble, Usuario inquilino, FormaDePago formaDePago, LocalDateTime fechaDeIngreso,LocalDateTime fechaDeEgreso) {
		this.inmueble = inmueble;
		this.inquilino = inquilino;
		this.formaDePago = formaDePago;
		this.fechaDeIngreso = fechaDeIngreso;
		this.fechaDeEgreso = fechaDeEgreso;
		this.estadoDeSolicitud = new EstadoDeSolicitudPendiente();
	}

	public void solicitarReserva() {
		this.inmueble.getPropietatio().recivirSolicitud(this); // Como hacer que el dueño pueda rechazar o aceptar??. No hay ninguna logica a seguir. 
	}

	public void aprobarSolicitud() {
		this.estadoDeSolicitud.aprobar(this);
	}
	
	public void rechazarSolicitud() {
		this.estadoDeSolicitud.rechazar(this);
	}
	
	public EstadoDeSolicitud getEstado() {
		return this.estadoDeSolicitud;
	}
	
	public void setEstadoDeSolicitus(EstadoDeSolicitud estadoDeSolicitud) {
		this.estadoDeSolicitud = estadoDeSolicitud;
	}

	public void realizarReserva() {
		this.reserva = new Reserva(this.inmueble,this.inquilino, this.formaDePago, this.fechaDeIngreso, this.fechaDeEgreso);
		this.inquilino.agregarReserva(this.reserva);
		
		// Quiza hay que agregar en una lista al inmueble, sino no puede buscar para comparar las fechas de una nueva reserva con las que estan activas. 
	}

	public void notificarAInquilino() {
		// Falta hacer, puede ser una clase Mail que maneje de los mails del sistema. 
	}

}
