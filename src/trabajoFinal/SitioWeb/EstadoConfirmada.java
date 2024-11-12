package trabajoFinal.SitioWeb;

import java.util.Optional;


public class EstadoConfirmada implements EstadoDeReserva {

	@Override
	public void cancelar(Reserva reserva) {	
		Inmueble inmueble = reserva.getInmueble();

		inmueble.getPoliticaDeCancelacion().darResarcimiento(reserva);
		inmueble.eliminarReserva(reserva);
		inmueble.restarCantidadDeVecesAlquilado();
		inmueble.getPropietario().recibirMail("La reserva fue cancelada");
		reserva.getUsuario().eliminarReserva(reserva);
		inmueble.getPropietario().restarCantidadDeVecesQueAlquilo();;
		reserva.setEstadoDeReserva(new EstadoCancelada());
		
		Optional<Reserva> reservaCondicional = reserva.obtenerReservaCondicional(inmueble);

		if (reservaCondicional.isPresent()) {
			reservaCondicional.get().evaluarReserva();
		}
	}

	@Override
	public void finalizar(Reserva reserva) {
		reserva.getInmueble().eliminarReserva(reserva);
		reserva.setEstadoDeReserva(new EstadoFinalizada());
	}
} 