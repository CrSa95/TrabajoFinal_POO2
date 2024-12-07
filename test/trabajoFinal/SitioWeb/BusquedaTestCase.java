package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusquedaTestCase {

	private Busqueda busqueda;
	private FiltroCiudad filtroCiudadMock;
	private FiltroFechas filtroFechasMock;
	private FiltroCapacidad filtroCapacidadMock;
	private SitioWeb sitioWebMock;
	
	@BeforeEach
    public void setUp() {
		filtroCiudadMock = mock(FiltroCiudad.class);
		filtroFechasMock = mock(FiltroFechas.class);	
		filtroCapacidadMock = mock(FiltroCapacidad.class);
		sitioWebMock = mock(SitioWeb.class);
		busqueda = new Busqueda(filtroCiudadMock, filtroFechasMock);
    }
	
	
	@Test
	void testCuandoSeAgregaUnFiltroALaBusqueda() {
		busqueda.agregarFiltro(filtroCapacidadMock);
		
		assertTrue(busqueda.getFiltros().contains(filtroCapacidadMock));
	}
	
	@Test
    void testCuandoSeAplicanLosFiltrosDeBusqueda() {
	
		Inmueble inmubleMock1 = mock(Inmueble.class);
		Inmueble inmubleMock2 = mock(Inmueble.class);
		List<Inmueble> listaInmueble = Arrays.asList(inmubleMock1, inmubleMock2);
		List<Inmueble> listaFiltrada = Arrays.asList(inmubleMock1);
		
		when(sitioWebMock.getTodosLosInmuebles()).thenReturn(listaInmueble);
		when(filtroCiudadMock.filtrar(listaInmueble)).thenReturn(listaFiltrada);
	    when(filtroFechasMock.filtrar(listaFiltrada)).thenReturn(listaFiltrada);
		
		List<Inmueble> resultado = busqueda.aplicarFiltros(sitioWebMock.getTodosLosInmuebles());
		 
		verify(filtroCiudadMock).filtrar(listaInmueble);
		verify(filtroFechasMock).filtrar(listaFiltrada);
		assertEquals(resultado, listaFiltrada);
		assertFalse(resultado.contains(inmubleMock2));
	}
}
