package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface UsuarioPropietario {
	
	public void altaInmueble(TipoDeInmueble tipoDeInmueble, int superficie, String pais, String ciudad,
			 String direccion, List<TipoDeServicio> servicios, int capacidad,
			 List<Foto> fotos, LocalTime checkIn, LocalTime checkOut,
			 PoliticaDeCancelacion politicaDeCancelacion, double precioBase,
			 LocalDate fechaInicial, LocalDate fechaFinal) throws Exception;
	public void actualizarPrecioBase(Inmueble inmueble, int precioNuevo);
	public void modificarPreciosEspecificos(Inmueble inmueble, LocalDate fechaInicial, LocalDate fechaFinal, int precio) throws Exception;
	public void modificarFormasDePago(Inmueble inmueble,FormaDePago formaDePago);
	public List<Inmueble> inmueblesAlquilados();
	public void recibirSolicitudReserva(SolicitudDeReserva solicitudDeReserva);
	public void rankearUnInquilino(Reserva reserva, Categoria categoria, int puntaje) throws Exception;
	public void actualizarListaDeRankeoInquilino(Rankeo rankeo);
	public void dejarUnComentarioAlInqulino(Reserva reserva, String comentario) throws Exception;
	public double recibirResarcimiento(double dineroResarcido);
	public long cantidadDeVecesQueAlquiloUnPropietarioSusInmuebles();
	public long cantidadDeVecesQueUnPropietarioAlquiloUnInmueble(Inmueble inmueble);
	public void datosDelPropietario(Inmueble inmueble);
	
}
