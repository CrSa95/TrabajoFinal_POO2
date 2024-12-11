package trabajoFinal.SitioWeb;

import java.util.List;

public interface UsuarioPropietario {
	
	public SitioWeb getSitioWeb();
	public String getNombre();
	public String getMail();
	public int getTelefono();
	public List<String> getComentariosPropietario();
	public List<Rankeo> getRankeosPropietario();
	public void recibirSolicitudReserva(SolicitudDeReserva solicitudDeReserva);
	public void rankearUnPropietario(Reserva reserva, Categoria categoria, int puntaje) throws Exception;
	public void actualizarListaDeRankeoPropietario(Rankeo rankeo);
	public void agregarUnComentarioAlPropietario(Reserva reserva, String comentario) throws Exception;
	public Boolean estaElRankeo(Rankeo rankeo, List<Rankeo> listaDeRankeo);
	public void actualizarPuntajeDeRankeo(Rankeo rankeo, List<Rankeo> listaDeRankeo);
	public int calcularPromedioTotal(List<Rankeo> rankeos);
	public void registrarReserva(Reserva reserva);
	public void eliminarReserva(Reserva reserva);
	public List<Inmueble> inmueblesAlquilados();
	public double recibirResarcimiento(double dineroResarcido);
}
