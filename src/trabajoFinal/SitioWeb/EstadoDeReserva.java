package trabajoFinal.SitioWeb;

public interface EstadoDeReserva {

	public void cancelar(Reserva reserva);
	public void finalizar(Reserva reserva);
	public boolean esFinalizado();
	public void rankearAUnPropietario(Usuario usuario, Categoria categoria, int puntaje) throws Exception;
	public void rankearAUnInquilino(Usuario usuario, Categoria categoria, int puntaje) throws Exception;
	public void rankearUnInmueble(Inmueble inmueble, Categoria categoria, int puntaje) throws Exception;
	public void registrarComentarioParaElPropietario(Usuario usuario, String comentario) throws Exception;
	public void registrarComentarioParaElInquilino(Usuario usuario, String comentario) throws Exception;
	public void registrarComentarioParaElInmueble(Inmueble inmueble, String comentario) throws Exception;
}
