package trabajoFinal.SitioWeb;

public interface EstadoDeReserva {

	public void cancelar(Reserva reserva);
	public void finalizar(Reserva reserva);
	public void finalizoLaReserva() throws Exception;

}
