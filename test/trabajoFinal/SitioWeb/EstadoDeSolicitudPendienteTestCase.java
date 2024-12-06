package trabajoFinal.SitioWeb;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoDeSolicitudPendienteTestCase {

	private EstadoDeSolicitudPendiente estadoDeSolicitudPendiente;
	private SolicitudDeReserva solicitudMock;
	private Reserva reservaMock;
	
	@BeforeEach
    public void setUp() {
		estadoDeSolicitudPendiente = new EstadoDeSolicitudPendiente();
		solicitudMock = mock(SolicitudDeReserva.class);
		reservaMock = mock(Reserva.class);
	}
	
	@Test
	void testAprobar() {
		estadoDeSolicitudPendiente.aprobar(solicitudMock);
		verify(solicitudMock).crearReserva();
		verify(solicitudMock).notificarAInquilino();
		verify(solicitudMock).setEstadoDeSolicitud(any(EstadoSolicitudAprobada.class));
	}
}
