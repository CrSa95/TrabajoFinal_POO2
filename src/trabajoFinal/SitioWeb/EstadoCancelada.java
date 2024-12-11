package trabajoFinal.SitioWeb;

public class EstadoCancelada extends EstadoDeReserva {

	@Override
	public void cancelar(Reserva reserva)throws Exception{
		throw new Exception("Error: La Reserva se encuentra cancelada.");
	}
	
	@Override
	public void finalizar(Reserva reserva) throws Exception{
		throw new Exception("Error: La Reserva se encuentra cancelada.");
	}
	
}