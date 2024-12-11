package trabajoFinal.SitioWeb;

import java.util.List;

public interface UsuarioInquilino {
	
	public SitioWeb getSitioWeb();
	public String getNombre();
	public String getMail();
	public int getTelefono();
	public List<String> getComentariosInquilino();
	public List<Rankeo> getRankeosInquilino();
	public void cancelarReserva(Reserva reserva) throws Exception;
	public void rankearUnInquilino(Reserva reserva, Categoria categoria, int puntaje) throws Exception;
	public void actualizarListaDeRankeoInquilino(Rankeo rankeo);
	public void agregarUnComentarioAlInqulino(Reserva reserva, String comentario) throws Exception;
	public Boolean estaElRankeo(Rankeo rankeo, List<Rankeo> listaDeRankeo);
	public void actualizarPuntajeDeRankeo(Rankeo rankeo, List<Rankeo> listaDeRankeo);
	public int calcularPromedioTotal(List<Rankeo> rankeos);
	public void registrarReserva(Reserva reserva);
	public void eliminarReserva(Reserva reserva);
}


