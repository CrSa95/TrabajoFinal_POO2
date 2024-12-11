package trabajoFinal.SitioWeb;

public interface UsuarioInquilino {
	
	public void cancelarReserva(Reserva reserva) throws Exception;
	public void datosDelInquilino();
	public void rankearAPropietario(Reserva reserva, Categoria categoria, int puntaje) throws Exception;
	public void actualizarListaDeRankeoPropietario(Rankeo rankeo);
	public void dejarUnComentarioAlPropietario(Reserva reserva, String comentario) throws Exception;
}

