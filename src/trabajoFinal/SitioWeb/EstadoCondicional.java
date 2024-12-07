package trabajoFinal.SitioWeb;

public class EstadoCondicional implements EstadoDeReserva{

	@Override
	public void cancelar(Reserva reserva) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finalizar(Reserva reserva) {


	}

	@Override
	public void finalizoLaReserva() throws Exception  {
		
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

}
