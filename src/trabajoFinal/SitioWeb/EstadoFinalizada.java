package trabajoFinal.SitioWeb;

public class EstadoFinalizada implements EstadoDeReserva {

	@Override
	public void cancelar(Reserva reserva) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finalizar(Reserva reserva) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finalizoLaReserva() {
		
	}
	
	@Override
	public void rankearAUnPropietario(Usuario usuario, Categoria categoria, int puntaje) throws Exception {
		usuario.getSitioWeb().estaCategoriaEspecificaPropietario(categoria);
		usuario.actualizarListaDeRankeoPropietario(new Rankeo(categoria, puntaje));
	}

	@Override
	public void rankearAUnInquilino(Usuario usuario, Categoria categoria, int puntaje) throws Exception {
		usuario.getSitioWeb().estaCategoriaEspecificaInquilino(categoria);
		usuario.actualizarListaDeRankeoInquilino(new Rankeo(categoria, puntaje));	
	}
	
	@Override
	public void rankearUnInmueble(Inmueble inmueble, Categoria categoria, int puntaje) throws Exception {
		inmueble.getSitioWeb().estaCategoriaEspecificaInmueble(categoria);
		inmueble.actualizarListaDeRankeoInmueble(new Rankeo(categoria, puntaje));	
	}
	
	public void registrarComentarioParaElPropietario(Usuario usuario, String comentario){
		usuario.getComentariosPropietario().add(comentario);
	}
	
	public void registrarComentarioParaElInquilino(Usuario usuario, String comentario){
		usuario.getComentariosInquilino().add(comentario);
	}

	@Override
	public void registrarComentarioParaElInmueble(Inmueble inmueble, String comentario) throws Exception {
		inmueble.getComentarios().add(comentario);
	}
}