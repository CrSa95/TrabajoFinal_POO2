package trabajoFinal.SitioWeb;

public class EstadoDeSolicitudPendiente implements EstadoDeSolicitud{


	public EstadoDeSolicitudPendiente() {

	}

	@Override
	public void aprobar(SolicitudDeReserva solicitud) {;
		solicitud.crearReserva();
		solicitud.notificarAInquilino();
		solicitud.setEstadoDeSolicitud(new EstadoSolicitudAprobada());
	}
}
