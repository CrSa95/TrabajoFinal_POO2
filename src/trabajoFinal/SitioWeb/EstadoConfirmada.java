package trabajoFinal.SitioWeb;

import java.util.Optional;


public class EstadoConfirmada extends EstadoDeReserva {

	public void cancelar(Reserva reserva) {	
		Inmueble inmueble = reserva.getInmueble();

		inmueble.getPoliticaDeCancelacion().darResarcimiento(reserva);
		inmueble.eliminarReserva(reserva);
		inmueble.getPropietario().recibirMail("La reserva fue cancelada");
		reserva.getInquilino().eliminarReserva(reserva);
		reserva.setEstadoDeReserva(new EstadoCancelada());
		
		Optional<Reserva> reservaCondicional = reserva.obtenerReservaCondicional(inmueble);

		if (reservaCondicional.isPresent()) {
			reservaCondicional.get().evaluarReserva();
		}
	}

	public void finalizar(Reserva reserva) {
		reserva.getInmueble().eliminarReserva(reserva);
		reserva.setEstadoDeReserva(new EstadoFinalizada());
	}
}