package trabajoFinal.SitioWeb;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OtroSitioWebTestCase {

	private OtroSitioWeb otroSitioWeb;
	private HomePagePublisher homePagePublisher;
	private Inmueble inmuebleMock;
	
	@BeforeEach
	public void setUp() {
		homePagePublisher = mock(HomePagePublisher.class);
		otroSitioWeb = new OtroSitioWeb(homePagePublisher);
		inmuebleMock = mock(Inmueble.class);
	}
	
	@Test
	void testBajaDePrecio() {
		TipoDeInmueble tipoMock = mock(TipoDeInmueble.class);
		when(inmuebleMock.getPrecioBase()).thenReturn(200.0);
		when(inmuebleMock.getTipoInmueble()).thenReturn(tipoMock);
		when(tipoMock.getTipoDeInmueble()).thenReturn("departamento");
		
		otroSitioWeb.bajaDePrecio(inmuebleMock);
		
		verify(homePagePublisher).publish("No te pierdas esta oferta: Un inmueble departamento a tan sólo 200.0 pesos");
	}
	
	@Test
	void testAltaDeReserva() {
		otroSitioWeb.altaDeReserva();
		
		verifyNoInteractions(homePagePublisher);
	}

	@Test
	void testCancelacionDeReserva() {
		otroSitioWeb.cancelacionDeReserva(inmuebleMock);
		
		verifyNoInteractions(inmuebleMock);
		verifyNoInteractions(homePagePublisher);
	}
}
