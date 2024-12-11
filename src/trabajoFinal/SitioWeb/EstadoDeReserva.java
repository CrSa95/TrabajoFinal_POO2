package trabajoFinal.SitioWeb;

public abstract class EstadoDeReserva {

	public abstract void cancelar(Reserva reserva) throws Exception;
	
	public abstract void finalizar(Reserva reserva) throws Exception;
	
	public boolean esFinalizado() {
		return false;
	}
	
	public void rankearAUnPropietario(Usuario usuario, Categoria categoria, int puntaje) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
	public void rankearAUnInquilino(Usuario usuario, Categoria categoria, int puntaje) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
	public void rankearUnInmueble(Inmueble inmueble, Categoria categoria, int puntaje) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
	public void registrarComentarioParaElPropietario(Usuario usuario, String comentario) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
	public void registrarComentarioParaElInquilino(Usuario usuario, String comentario) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
	public void registrarComentarioParaElInmueble(Inmueble inmueble, String comentario) throws Exception{
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
}
