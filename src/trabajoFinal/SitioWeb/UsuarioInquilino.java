package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.util.List;

public interface UsuarioInquilino {
	
	public long cantidadDeVecesQueAlquiloUnInquilino();
	public void cancelarReserva(Reserva reserva) throws Exception;
	public void datosDelInquilino();
	public void rankearAPropietario(Reserva reserva, Categoria categoria, int puntaje) throws Exception;
	public void actualizarListaDeRankeoPropietario(Rankeo rankeo);
	public void dejarUnComentarioAlPropietario(Reserva reserva, String comentario) throws Exception;
	public List<Reserva> getReservasFuturas(LocalDate fechaActual);
	public List<Reserva> getReservasEnCiudad(String ciudad);
	public List<String> getCiudadesReservadas();
}
