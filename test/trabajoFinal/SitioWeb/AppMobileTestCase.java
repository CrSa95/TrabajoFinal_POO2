package trabajoFinal.SitioWeb;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppMobileTestCase {

	private AppMobile appMobile;
	private PopUpWindow popUpWindowMock;
	private Inmueble inmuebleMock;
	
	@BeforeEach
	public void setUp() {
		popUpWindowMock = mock(PopUpWindow.class);
		appMobile = new AppMobile(popUpWindowMock);
		inmuebleMock = mock(Inmueble.class);
	} 
	
	@Test
	void testBajaDePrecio() {
		appMobile.bajaDePrecio(inmuebleMock);
		
		verifyNoInteractions(inmuebleMock);
		verifyNoInteractions(popUpWindowMock);
	}
	
	@Test
	void testAltaDeReserva() {
		appMobile.altaDeReserva();
		
		verifyNoInteractions(popUpWindowMock);
	}

	@Test
	void testCancelacionDeReserva() {
		TipoDeInmueble tipoMock = mock(TipoDeInmueble.class);
		when(inmuebleMock.getTipoInmueble()).thenReturn(tipoMock);
		when(tipoMock.getTipoDeInmueble()).thenReturn("departamento");
		
		appMobile.cancelacionDeReserva(inmuebleMock);
		
		verify(popUpWindowMock).popUp("El/la departamento que te interesa se ha liberado! Corre a reservarlo!", "Rojo", 10);
	}
}