package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoCondicionalTestCase {

	private EstadoCondicional estadoCondicional;
	private Reserva reservaMock;
	private Usuario usuarioMock;
	private Inmueble inmuebleMock;
	
	@BeforeEach
    public void setUp() {
		estadoCondicional = new EstadoCondicional();
		reservaMock = mock(Reserva.class);
		usuarioMock = mock(Usuario.class);
		inmuebleMock = mock(Inmueble.class);
	}
	
	@Test
	void testCancelar() {
		estadoCondicional.cancelar(reservaMock);
		
		verifyNoInteractions(reservaMock);
	}
	
	@Test
	void testFinalizar() {
		estadoCondicional.finalizar(reservaMock);
		
		verifyNoInteractions(reservaMock);
	}
	
	@Test
	void testElEstadoEsFinalizado() throws Exception {
		assertThrows(Exception.class, () -> {estadoCondicional.finalizoLaReserva();});
		assertThrows(Exception.class,() -> estadoCondicional.finalizoLaReserva(), "Error: La Reserva aun no finalizo.");
	}
	
	@Test
	void testRankearAUnPropietarioLanzaExcepcion() {
	    Categoria categoriaMock = mock(Categoria.class);

	    assertThrows(Exception.class, () -> {estadoCondicional.rankearAUnPropietario(usuarioMock, categoriaMock, 5);});
	    assertThrows(Exception.class, () -> estadoCondicional.rankearAUnPropietario(usuarioMock, categoriaMock, 5), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRankearAUnInquilinoLanzaExcepcion() {
	    Categoria categoriaMock = mock(Categoria.class);

	    assertThrows(Exception.class, () -> {estadoCondicional.rankearAUnInquilino(usuarioMock, categoriaMock, 5);});
	    assertThrows(Exception.class, () -> estadoCondicional.rankearAUnInquilino(usuarioMock, categoriaMock, 5), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRankearUnInmuebleLanzaExcepcion() {
	    Categoria categoriaMock = mock(Categoria.class);

	    assertThrows(Exception.class, () -> {estadoCondicional.rankearUnInmueble(inmuebleMock, categoriaMock, 5);});
	    assertThrows(Exception.class, () -> estadoCondicional.rankearUnInmueble(inmuebleMock, categoriaMock, 5), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRegistrarComentarioParaElPropietarioLanzaExcepcion() {
  
	    assertThrows(Exception.class, () -> {estadoCondicional.registrarComentarioParaElPropietario(usuarioMock, "Excelente propietario");});
	    assertThrows(Exception.class, () -> estadoCondicional.registrarComentarioParaElPropietario(usuarioMock, "Excelente propietario"), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRegistrarComentarioParaElInquilinoLanzaExcepcion() {
	    
	    assertThrows(Exception.class, () -> {estadoCondicional.registrarComentarioParaElInquilino(usuarioMock, "Muy buen inquilino");});
	    assertThrows(Exception.class, () -> estadoCondicional.registrarComentarioParaElInquilino(usuarioMock, "Muy buen inquilino"), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRegistrarComentarioParaElInmuebleLanzaExcepcion() {
	    
	    assertThrows(Exception.class, () -> {estadoCondicional.registrarComentarioParaElInmueble(inmuebleMock, "Excelente ubicación");});
	    assertThrows(Exception.class, () -> estadoCondicional.registrarComentarioParaElInmueble(inmuebleMock, "Excelente ubicación"), "Error: La Reserva aun no finalizo.");
	}
}