package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoDeReservaTestCase {

	private EstadoCancelada estadoCancelada;
	private Reserva reservaMock;
	private Usuario propietarioMock;
	private Usuario inquilinoMock;
	private Categoria categoriaMock;
	
	@BeforeEach
    public void setUp() {
		estadoCancelada = new EstadoCancelada();
		reservaMock = mock(Reserva.class);
		propietarioMock = mock(Usuario.class);
		inquilinoMock = mock(Usuario.class);
	    categoriaMock = mock(Categoria.class);
	}
	
	@Test
    public void testCancelarLanzaException() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {estadoCancelada.cancelar(reservaMock);});
        assertEquals("Error: La Reserva se encuentra cancelada.", exception.getMessage());
    }

    @Test
    public void testFinalizarLanzaException() {
        Exception exception = assertThrows(Exception.class, () -> {estadoCancelada.finalizar(reservaMock);});
        assertEquals("Error: La Reserva se encuentra cancelada.", exception.getMessage());
    }
    
	@Test
    public void testRankearAUnPropietarioLanzaException() {
        Exception exception = assertThrows(Exception.class, () -> {estadoCancelada.rankearAUnPropietario(propietarioMock, categoriaMock, 5);});
        assertEquals("Error: La Reserva aun no finalizo.", exception.getMessage());
    }

    @Test
    public void testRankearAUnInquilinoLanzaException() {
        Exception exception = assertThrows(Exception.class, () -> {estadoCancelada.rankearAUnInquilino(inquilinoMock, categoriaMock, 5);});
        assertEquals("Error: La Reserva aun no finalizo.", exception.getMessage());
    }

    @Test
    public void testRankearUnInmuebleLanzaException() {
    	Inmueble inmuebleMock = mock(Inmueble.class);
        Exception exception = assertThrows(Exception.class, () -> {estadoCancelada.rankearUnInmueble(inmuebleMock, categoriaMock, 5);});
        assertEquals("Error: La Reserva aun no finalizo.", exception.getMessage());
    }

    @Test
    public void testRegistrarComentarioParaElPropietarioLanzaException() {
        Exception exception = assertThrows(Exception.class, () -> {estadoCancelada.registrarComentarioParaElPropietario(propietarioMock, "Comentario");});
        assertEquals("Error: La Reserva aun no finalizo.", exception.getMessage());
    }
	
    @Test
    public void testRegistrarComentarioParaElInquilinoLanzaException() {
        Exception exception = assertThrows(Exception.class, () -> {estadoCancelada.registrarComentarioParaElInquilino(inquilinoMock, "Comentario");});
        assertEquals("Error: La Reserva aun no finalizo.", exception.getMessage());
    }

    @Test
    public void testRegistrarComentarioParaElInmuebleLanzaException() {
    	Inmueble inmuebleMock = mock(Inmueble.class);
        Exception exception = assertThrows(Exception.class, () -> {estadoCancelada.registrarComentarioParaElInmueble(inmuebleMock, "Comentario");});
        assertEquals("Error: La Reserva aun no finalizo.", exception.getMessage());
    }
}
