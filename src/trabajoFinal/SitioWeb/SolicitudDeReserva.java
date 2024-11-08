package trabajoFinal.SitioWeb;

public class SolicitudDeReserva {
	
	private Usuario usuario; 
		
	public SolicitudDeReserva(Usuario usuario) {
		this.usuario = usuario;
	}

	public void realizarReserva() {
		usuario.registrarReserva(new Reserva());
	}
	
	public void aceptarReserva() {
		
	}
	
}
