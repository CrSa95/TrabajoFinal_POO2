package trabajoFinal.SitioWeb;

import java.util.List;

public interface UsuarioPropietario {
	
	public List<Inmueble> inmueblesAlquilados();
	public void recibirSolicitudReserva(SolicitudDeReserva solicitudDeReserva);
	public double recibirResarcimiento(double dineroResarcido);
	public void datosDelPropietario(Inmueble inmueble);
	public void rankearAInquilino(Reserva reserva, Categoria categoria, int puntaje) throws Exception;
	public void actualizarListaDeRankeoInquilino(Rankeo rankeo);
	public void dejarUnComentarioAlInqulino(Reserva reserva, String comentario) throws Exception;	
}
