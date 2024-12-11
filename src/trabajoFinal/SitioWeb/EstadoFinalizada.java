package trabajoFinal.SitioWeb;

public class EstadoFinalizada extends EstadoDeReserva {
	
	@Override
	public void cancelar(Reserva reserva) throws Exception{
		throw new Exception("Error: La Reserva esta finalizada.");
	}
	
	@Override
	public void finalizar(Reserva reserva) throws Exception{
		throw new Exception("Error: La Reserva esta finalizada.");
	}
	
	@Override
	public boolean esFinalizado(){
		return  true;
	}
	
	@Override
	public void rankearPropietario(Usuario usuario, Categoria categoria, int puntaje) throws Exception {
		usuario.getSitioWeb().estaCategoriaEspecificaPropietario(categoria);
		usuario.actualizarListaDeRankeoPropietario(new Rankeo(categoria, puntaje));
	}

	@Override
	public void rankearInquilino(Usuario usuario, Categoria categoria, int puntaje) throws Exception {
		usuario.getSitioWeb().estaCategoriaEspecificaInquilino(categoria);
		usuario.actualizarListaDeRankeoInquilino(new Rankeo(categoria, puntaje));	
	}
	
	@Override
	public void rankearInmueble(Inmueble inmueble, Categoria categoria, int puntaje) throws Exception {
		inmueble.getSitioWeb().estaCategoriaEspecificaInmueble(categoria);
		inmueble.actualizarListaDeRankeoInmueble(new Rankeo(categoria, puntaje));	
	}
	
	public void registrarComentarioPropietario(Usuario usuario, String comentario){
		usuario.getComentariosPropietario().add(comentario);
	}
	
	public void registrarComentarioInquilino(Usuario usuario, String comentario){
		usuario.getComentariosInquilino().add(comentario);
	}

	@Override
	public void registrarComentarioInmueble(Inmueble inmueble, String comentario) throws Exception {
		inmueble.getComentarios().add(comentario);
	}
}