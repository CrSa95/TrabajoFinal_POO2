package trabajoFinal.SitioWeb;

//import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoSolicitudAprobadaTestCase {

	private EstadoSolicitudAprobada estadoSolicitudAprobada;
	private SolicitudDeReserva solicitudMock;
	
	@BeforeEach
    public void setUp() {
		estadoSolicitudAprobada = new EstadoSolicitudAprobada();
		solicitudMock = mock(SolicitudDeReserva.class);
	}
	
	@Test
	void testAprobar() {
		estadoSolicitudAprobada.aprobar(solicitudMock);
		
		verifyNoInteractions(solicitudMock);
	}
}
