package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoFinalizadaTestCase {

	private EstadoFinalizada estadoFinalizada;
	private Reserva reservaMock;
	private Usuario propietarioMock;
	private Usuario inquilinoMock;
	private SitioWeb sitioWebMock;
	private Categoria categoriaMock;
	
	@BeforeEach
    public void setUp() {
		estadoFinalizada = new EstadoFinalizada();
		reservaMock = mock(Reserva.class);
		propietarioMock = mock(Usuario.class);
		inquilinoMock = mock(Usuario.class);
	    sitioWebMock = mock(SitioWeb.class);
	    categoriaMock = mock(Categoria.class);
	}
	
	@Test
	void testCancelar() throws Exception {
		Exception exception = assertThrows(Exception.class, () -> estadoFinalizada.cancelar(reservaMock));
	    assertEquals("Error: La Reserva esta finalizada.", exception.getMessage());
	}
	
	@Test
	void testFinalizar() throws Exception {
		Exception exception = assertThrows(Exception.class, () -> estadoFinalizada.finalizar(reservaMock));
	    assertEquals("Error: La Reserva esta finalizada.", exception.getMessage());
	}
	
	@Test
	void testElEstadoEsFinalizado() throws Exception { 
		assertTrue(estadoFinalizada.esFinalizado());
	}
	
	@Test
	void testRankearAUnPropietario() throws Exception {

	    when(propietarioMock.getSitioWeb()).thenReturn(sitioWebMock);

	    estadoFinalizada.rankearPropietario(propietarioMock, categoriaMock, 5);

	    verify(sitioWebMock).estaCategoriaEspecificaPropietario(categoriaMock);
	    verify(propietarioMock).actualizarListaDeRankeoPropietario(any(Rankeo.class));
	}

	@Test
	void testRankearAUnInquilino() throws Exception {

	    when(inquilinoMock.getSitioWeb()).thenReturn(sitioWebMock);

	    estadoFinalizada.rankearInquilino(inquilinoMock, categoriaMock, 4);

	    verify(sitioWebMock).estaCategoriaEspecificaInquilino(categoriaMock);
	    verify(inquilinoMock).actualizarListaDeRankeoInquilino(any(Rankeo.class));
	}

	@Test
	void testRankearUnInmueble() throws Exception {
		Inmueble inmuebleMock = mock(Inmueble.class);

	    when(inmuebleMock.getSitioWeb()).thenReturn(sitioWebMock);

	    estadoFinalizada.rankearInmueble(inmuebleMock, categoriaMock, 3);

	    verify(sitioWebMock).estaCategoriaEspecificaInmueble(categoriaMock);
	    verify(inmuebleMock).actualizarListaDeRankeoInmueble(any(Rankeo.class));
	}
	
	
	
	@Test
	void testRegistrarComentarioParaElPropietario() {
	    Usuario propietarioMock = mock(Usuario.class);
	    List<String> comentarios = mock(List.class);
	    when(propietarioMock.getComentariosPropietario()).thenReturn(comentarios);

	    estadoFinalizada.registrarComentarioPropietario(propietarioMock, "Excelente propietario");

	    verify(comentarios).add("Excelente propietario");
	}

	@Test
	void testRegistrarComentarioParaElInquilino() {
	    Usuario inquilinoMock = mock(Usuario.class);
	    List<String> comentarios = mock(List.class);
	    when(inquilinoMock.getComentariosInquilino()).thenReturn(comentarios);

	    estadoFinalizada.registrarComentarioInquilino(inquilinoMock, "Muy buen inquilino");

	    verify(comentarios).add("Muy buen inquilino");
	}

	@Test
	void testRegistrarComentarioParaElInmueble() throws Exception {
	    Inmueble inmuebleMock = mock(Inmueble.class);
	    List<String> comentarios = mock(List.class);
	    when(inmuebleMock.getComentarios()).thenReturn(comentarios);

	    estadoFinalizada.registrarComentarioInmueble(inmuebleMock, "Lugar acogedor");

	    verify(comentarios).add("Lugar acogedor");
	}

}
