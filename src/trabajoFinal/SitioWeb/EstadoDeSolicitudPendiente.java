package trabajoFinal.SitioWeb;

public class EstadoDeSolicitudPendiente implements EstadoDeSolicitud{

	
	public EstadoDeSolicitudPendiente() {
		
	}

	@Override
	public void aprobar(SolicitudDeReserva solicitud) {
		solicitud.realizarReserva();
		solicitud.notificarAInquilino();
		solicitud.setEstadoDeSolicitus(new EstadoSolicitudAprobada());
		
	}

	@Override
	public void rechazar(SolicitudDeReserva solicitud) {
		solicitud.setEstadoDeSolicitus(new EstadoDeSolicitudRechazada());
	}
}
