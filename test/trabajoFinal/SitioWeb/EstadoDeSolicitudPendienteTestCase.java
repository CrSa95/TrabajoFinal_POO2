package trabajoFinal.SitioWeb;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoDeSolicitudPendienteTestCase {

	private EstadoDeSolicitudPendiente estadoDeSolicitudPendiente;
	private SolicitudDeReserva solicitudMock;
	
	@BeforeEach
    public void setUp() {
		estadoDeSolicitudPendiente = new EstadoDeSolicitudPendiente();
		solicitudMock = mock(SolicitudDeReserva.class);
	}
	
	@Test
	void testAprobar() {
		estadoDeSolicitudPendiente.aprobar(solicitudMock);
		verify(solicitudMock).crearReserva();
		verify(solicitudMock).notificarAInquilino();
		verify(solicitudMock).setEstadoDeSolicitud(any(EstadoSolicitudAprobada.class));
	}
}
