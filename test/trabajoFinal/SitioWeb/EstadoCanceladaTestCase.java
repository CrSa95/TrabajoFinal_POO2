package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoCanceladaTestCase {

	private EstadoCancelada estadoCancelada;
	private Reserva reservaMock;
	private Inmueble inmuebleMock;
    private Usuario usuarioMock;
	
	@BeforeEach
    public void setUp() {
		estadoCancelada = new EstadoCancelada();
		reservaMock = mock(Reserva.class);
		inmuebleMock = mock(Inmueble.class);
        usuarioMock = mock(Usuario.class);
	}
	
	@Test
	void testCancelar() {
		estadoCancelada.cancelar(reservaMock);
		
		verifyNoInteractions(reservaMock);
	}
	
	@Test
	void testFinalizar() {
		estadoCancelada.finalizar(reservaMock);
		
		verifyNoInteractions(reservaMock);
	}
	
	@Test
	void testElEstadoEsFinalizado() throws Exception {
		assertFalse(estadoCancelada.esFinalizado());
	}
	
	@Test
	void testRankearAUnPropietarioLanzaExcepcion() {
	    Categoria categoriaMock = mock(Categoria.class);

	    assertThrows(Exception.class, () -> {estadoCancelada.rankearAUnPropietario(usuarioMock, categoriaMock, 5);});
	    assertThrows(Exception.class, () -> estadoCancelada.rankearAUnPropietario(usuarioMock, categoriaMock, 5), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRankearAUnInquilinoLanzaExcepcion() {
	    Categoria categoriaMock = mock(Categoria.class);

	    assertThrows(Exception.class, () -> {estadoCancelada.rankearAUnInquilino(usuarioMock, categoriaMock, 5);});
	    assertThrows(Exception.class, () -> estadoCancelada.rankearAUnInquilino(usuarioMock, categoriaMock, 5), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRankearUnInmuebleLanzaExcepcion() {
	    Categoria categoriaMock = mock(Categoria.class);

	    assertThrows(Exception.class, () -> {estadoCancelada.rankearUnInmueble(inmuebleMock, categoriaMock, 5);});
	    assertThrows(Exception.class, () -> estadoCancelada.rankearUnInmueble(inmuebleMock, categoriaMock, 5), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRegistrarComentarioParaElPropietarioLanzaExcepcion() {
  
	    assertThrows(Exception.class, () -> {estadoCancelada.registrarComentarioParaElPropietario(usuarioMock, "Excelente propietario");});
	    assertThrows(Exception.class, () -> estadoCancelada.registrarComentarioParaElPropietario(usuarioMock, "Excelente propietario"), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRegistrarComentarioParaElInquilinoLanzaExcepcion() {
	    
	    assertThrows(Exception.class, () -> {estadoCancelada.registrarComentarioParaElInquilino(usuarioMock, "Muy buen inquilino");});
	    assertThrows(Exception.class, () -> estadoCancelada.registrarComentarioParaElInquilino(usuarioMock, "Muy buen inquilino"), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRegistrarComentarioParaElInmuebleLanzaExcepcion() {
	    
	    assertThrows(Exception.class, () -> {estadoCancelada.registrarComentarioParaElInmueble(inmuebleMock, "Excelente ubicación");});
	    assertThrows(Exception.class, () -> estadoCancelada.registrarComentarioParaElInmueble(inmuebleMock, "Excelente ubicación"), "Error: La Reserva aun no finalizo.");
	}
} 
