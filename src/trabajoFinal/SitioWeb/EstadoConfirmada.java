package trabajoFinal.SitioWeb;

import java.util.Optional;

public class EstadoConfirmada implements EstadoDeReserva {

	@Override
	public void cancelar(Reserva reserva) {


		reserva.getInmueble().getPoliticaDeCancelacion().darResarcimiento(reserva);
		reserva.getInmueble().eliminarReserva(reserva);
		reserva.setEstadoDeReserva(new EstadoCancelada());

		Optional<Reserva> reservaCondicional = reserva.obtenerReservaCondicional(reserva.getInmueble());
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
