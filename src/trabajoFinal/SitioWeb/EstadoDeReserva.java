package trabajoFinal.SitioWeb;

public abstract class EstadoDeReserva {

	public abstract void cancelar(Reserva reserva) throws Exception;
	
	public abstract void finalizar(Reserva reserva) throws Exception;
	
	public boolean esFinalizado() {
		return false;
	}
	
	public void rankearPropietario(Usuario usuario, Categoria categoria, int puntaje) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
	public void rankearInquilino(Usuario usuario, Categoria categoria, int puntaje) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
	public void rankearInmueble(Inmueble inmueble, Categoria categoria, int puntaje) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
	public void registrarComentarioPropietario(Usuario usuario, String comentario) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
	public void registrarComentarioInquilino(Usuario usuario, String comentario) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
	public void registrarComentarioInmueble(Inmueble inmueble, String comentario) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
}
