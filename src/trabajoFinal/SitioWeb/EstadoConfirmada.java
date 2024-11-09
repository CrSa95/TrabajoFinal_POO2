package trabajoFinal.SitioWeb;

import java.util.Optional;

public class EstadoConfirmada implements EstadoDeReserva {

	@Override
	public void cancelar(Reserva reserva) {
		
		Optional<Reserva> reservaCondicional = reserva.obtenerReservaCondicional(reserva.getInmueble());
		reserva.getInmueble().getPoliticaDeCancelacion().darResarcimiento(reserva);
		
		if (reservaCondicional.isPresent()) {
		
			reservaCondicional.get().evaluarReserva();
		}
		
		reserva.setEstadoDeReserva(new EstadoCancelada());
		
		
	}

	@Override
	public void finalizar(Reserva reserva) {
		
		reserva.setEstadoDeReserva(new EstadoFinalizada());
	}

}
