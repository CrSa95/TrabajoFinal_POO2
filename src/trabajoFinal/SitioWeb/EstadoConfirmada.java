package trabajoFinal.SitioWeb;

public class EstadoConfirmada implements EstadoDeReserva {

	@Override
	public void cancelar(Reserva reserva) {
		reserva.getInmueble().getPoliticaDeCancelacion().darResarcimiento();
		reserva.setEstadoDeReserva(new EstadoCancelada());
	}

	@Override
	public void finalizar(Reserva reserva) {
		
		reserva.setEstadoDeReserva(new EstadoFinalizada());
	}

}
