package trabajoFinal.SitioWeb;

public class EstadoCancelada implements EstadoDeReserva {

	@Override
	public void cancelar(Reserva reserva) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finalizar(Reserva reserva) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finalizoLaReserva() throws Exception  {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

	@Override
	public void rankearAUnPropietario(Usuario usuario, Categoria categoria, int puntaje) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

	@Override
	public void rankearAUnInquilino(Usuario usuario, Categoria categoria, int puntaje) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

	@Override
	public void rankearUnInmueble(Inmueble inmueble, Categoria categoria, int puntaje) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");	
	}

	@Override
	public void registrarComentarioParaElPropietario(Usuario usuario, String comentario) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

	@Override
	public void registrarComentarioParaElInquilino(Usuario usuario, String comentario) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

	@Override
	public void registrarComentarioParaElInmueble(Inmueble inmueble, String comentario) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

}