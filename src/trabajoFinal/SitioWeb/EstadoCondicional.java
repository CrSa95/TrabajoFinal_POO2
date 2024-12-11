package trabajoFinal.SitioWeb;

public class EstadoCondicional extends EstadoDeReserva{

	@Override
	public void cancelar(Reserva reserva) throws Exception{
		throw new Exception("Error: La Reserva esta en estado condicional.");
	}
	
	@Override
	public void finalizar(Reserva reserva) throws Exception{
		throw new Exception("Error: La Reserva esta en estado condicional.");
	}
}

