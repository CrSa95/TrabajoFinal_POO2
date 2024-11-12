package trabajoFinal.SitioWeb;

import java.time.LocalDate;

public class SolicitudDeReserva{

	private Inmueble inmueble;
	private Usuario inquilino;
	private FormaDePago formaDePago;
	private LocalDate fechaDeIngreso;
	private LocalDate fechaDeEgreso;
	private EstadoDeSolicitud estadoDeSolicitud;
	private Reserva reserva;

	public SolicitudDeReserva(Inmueble inmueble, Usuario inquilino, FormaDePago formaDePago, LocalDate fechaDeIngreso,LocalDate fechaDeEgreso) {
		this.inmueble = inmueble;
		this.inquilino = inquilino;
		this.formaDePago = formaDePago;
		this.fechaDeIngreso = fechaDeIngreso;
		this.fechaDeEgreso = fechaDeEgreso;
		this.estadoDeSolicitud = new EstadoDeSolicitudPendiente();
	}

	public Inmueble getInmueble() {
		return this.inmueble;
	}

	public void solicitarReserva() {
		this.inmueble.getPropietario().recibirSolicitudReserva(this);
	}

	public void aprobarSolicitud() {
		this.estadoDeSolicitud.aprobar(this);
	}

	public EstadoDeSolicitud getEstado() {
		return this.estadoDeSolicitud;
	}

	public void setEstadoDeSolicitud(EstadoDeSolicitud estadoDeSolicitud) {
		this.estadoDeSolicitud = estadoDeSolicitud;
	}

	public void realizarReserva() {
		this.reserva = new Reserva(this.inmueble,this.inquilino, this.formaDePago, this.fechaDeIngreso, this.fechaDeEgreso);
		this.reserva.evaluarReserva();
	}

	public void notificarAInquilino() {
		this.inquilino.recibirMail("Solicitud de reserva aceptada");
	}

}
