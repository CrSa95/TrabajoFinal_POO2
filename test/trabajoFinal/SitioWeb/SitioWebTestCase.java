package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;  

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SitioWebTestCase {

    private SitioWeb sitioWeb;
    private Usuario usuarioMock;
    private Usuario usuarioMock2;
    private Usuario usuarioMock3;
    private Usuario usuarioMock4;
    private Categoria categoriaMock;
    private Categoria otraCategoriaMock;
    private TipoDeServicio gas;
    private TipoDeServicio agua;
    private TipoDeInmueble quincho;
    private TipoDeInmueble dormitorio;
    private Inmueble inmueble;
    private Inmueble otroInmueble;
    
    @BeforeEach
    public void setUp() {
    	
    	sitioWeb = new SitioWeb();
        usuarioMock = mock(Usuario.class);
        usuarioMock2 = mock(Usuario.class);
        usuarioMock3 = mock(Usuario.class);
        usuarioMock4 = mock(Usuario.class);
        categoriaMock = mock(Categoria.class);
        otraCategoriaMock = mock(Categoria.class);
        gas = mock(TipoDeServicio.class);
        agua = mock(TipoDeServicio.class);
        quincho = mock(TipoDeInmueble.class);
        dormitorio = mock(TipoDeInmueble.class);
        inmueble = mock(Inmueble.class);
        otroInmueble = mock(Inmueble.class);
    }
	
	@Test
    void testUnSitioWebPuedeRegistrarUnUsuario() { 
		
		sitioWeb.registrarUsuario(usuarioMock);
        assertTrue(sitioWeb.getUsuariosRegistrados().contains(usuarioMock));
    }
	 
	
	@Test
    void testUnSitioWebPuedeDarDeAltaYFiltrarUnaListaDeTiposDeServicios() {
		
        sitioWeb.altaTipoDeServicio(gas);
        sitioWeb.altaTipoDeServicio(agua);
        assertTrue((sitioWeb.getTiposDeServicios()).contains(gas));
        assertTrue((sitioWeb.getTiposDeServicios()).contains(agua));
    }
	
	@Test
    void testUnSitioWebPuedeDarDeAltaYVerficiarSiEstaUnTipoDeInmueble() {
		
        sitioWeb.altaTipoDeInmueble(quincho);
        sitioWeb.altaTipoDeInmueble(dormitorio);
        assertTrue((sitioWeb.getTiposDeInmuebles()).contains(dormitorio));
        assertTrue((sitioWeb.getTiposDeInmuebles()).contains(quincho));
    }
	
	@Test
    void testUnSitioWebPuedeDarDeAltaYVerificarUnaCategoriaDeInmuebleEspacifica() throws Exception {
		
		when(categoriaMock.nombreCategoria()).thenReturn("Comodo");
		when(otraCategoriaMock.nombreCategoria()).thenReturn("Iluminado");
		
        sitioWeb.altaTipoDeCategoriaInmueble(categoriaMock);
        assertDoesNotThrow(() -> sitioWeb.estaCategoriaEspecificaInmueble(categoriaMock));
        assertThrows(Exception.class, () -> {sitioWeb.estaCategoriaEspecificaInmueble(otraCategoriaMock);});
       
    }
	
	@Test
    void testUnSitioWebPuedeDarDeAltaYVerificarUnaCategoriaDeInquilinoEspacifica() {
		
		when(categoriaMock.nombreCategoria()).thenReturn("Sucio");
		when(otraCategoriaMock.nombreCategoria()).thenReturn("Limpio");
		
        sitioWeb.altaTipoDeCategoriaInquilino(categoriaMock);
        assertDoesNotThrow(() -> sitioWeb.estaCategoriaEspecificaInquilino(categoriaMock));
        assertThrows(Exception.class, () -> {sitioWeb.estaCategoriaEspecificaInquilino(otraCategoriaMock);});
    }
	
	@Test
    void testUnSitioWebPuedeDarDeAltaYVerificarUnaCategoriaDePropietarioEspacifica() {

		when(categoriaMock.nombreCategoria()).thenReturn("Amable");
		when(otraCategoriaMock.nombreCategoria()).thenReturn("Malumorado");
		
        sitioWeb.altaTipoDeCategoriaPropietario(categoriaMock);
        assertDoesNotThrow(() -> sitioWeb.estaCategoriaEspecificaPropietario(categoriaMock));
        assertThrows(Exception.class, () -> {sitioWeb.estaCategoriaEspecificaPropietario(otraCategoriaMock);});
    }
	
	@Test
    void testUnSitioWebPuedeDarDeAltaUnInmueble() {
		
        sitioWeb.altaInmueble(inmueble);
        assertTrue(sitioWeb.getTodosLosInmuebles().contains(inmueble));
        assertFalse(sitioWeb.getTodosLosInmuebles().contains(otroInmueble));
    }
	
	@Test
    void testUnSitioWebPuedeBuscarInmuebles() {
		
		Busqueda busquedaMock = mock(Busqueda.class);
		
		sitioWeb.altaInmueble(inmueble);
        sitioWeb.altaInmueble(otroInmueble);
        sitioWeb.buscarInmuebles(busquedaMock);
		
		verify(busquedaMock).aplicarFiltros(sitioWeb.getTodosLosInmuebles());
	}

	@Test
    void testUnSitioWebPuedeCalcularSuTasaDeOcupacion() {
		
		when(inmueble.estaAlquiladoActualmente()).thenReturn(true);
		when(otroInmueble.estaAlquiladoActualmente()).thenReturn(false);
		
		sitioWeb.altaInmueble(inmueble);
		sitioWeb.altaInmueble(otroInmueble);
		
		assertTrue(sitioWeb.calcularTasaOcupacion() == 0.5);
	}
	
	@Test
	void testUnSitioWebConoceSuTopTresDeInquilinosQueMasAlquilaron() {
		
	    sitioWeb.registrarUsuario(usuarioMock);
	    sitioWeb.registrarUsuario(usuarioMock2);
	    sitioWeb.registrarUsuario(usuarioMock3);
	    sitioWeb.registrarUsuario(usuarioMock4);
	    
	    EstadoFinalizada estadoFinalizadoMock = mock(EstadoFinalizada.class);
	    
	    when(estadoFinalizadoMock.esFinalizado()).thenReturn(true);
	    
	    Reserva reservaFinalizadaMock = mock(Reserva.class);
	    when(reservaFinalizadaMock.getEstadoDeReserva()).thenReturn(estadoFinalizadoMock);

	    when(usuarioMock.getReservas()).thenReturn(Collections.nCopies(10, reservaFinalizadaMock));
	    when(usuarioMock2.getReservas()).thenReturn(Collections.nCopies(5, reservaFinalizadaMock));
	    when(usuarioMock3.getReservas()).thenReturn(Collections.nCopies(11, reservaFinalizadaMock));

	    List<Usuario> topTres = sitioWeb.obtenerTopTresInquilinoQueMasAlquilaron();
	    assertTrue(topTres.contains(usuarioMock));
	    assertTrue(topTres.contains(usuarioMock2));
	    assertTrue(topTres.contains(usuarioMock3));
	}
	
	@Test
	void testUnUsuarioInquilinoPuedeVerSusReservasFuturas() {
        Reserva reservaMock = mock(Reserva.class);
        Reserva otraReservaMock = mock(Reserva.class);

        when(usuarioMock.getReservas()).thenReturn(Arrays.asList(reservaMock, otraReservaMock));
        when(reservaMock.getFechaDeIngreso()).thenReturn(LocalDate.of(2025, 1, 23));
        when(otraReservaMock.getFechaDeIngreso()).thenReturn(LocalDate.of(2024, 1, 23));

        List<Reserva> reservasFuturas = sitioWeb.getReservasFuturas(LocalDate.now(), usuarioMock);

        assertTrue(reservasFuturas.contains(reservaMock));
        assertFalse(reservasFuturas.contains(otraReservaMock));
    }
	
	@Test
    void testUnUsuarioInquilinoPuedeVerSusReservasEnCiudad() {

        Reserva reservaMock = mock(Reserva.class);
        String ciudad = "Buenos Aires";

        when(usuarioMock.getReservas()).thenReturn(Arrays.asList(reservaMock));
        when(reservaMock.getInmueble()).thenReturn(inmueble);
        when(inmueble.getCiudad()).thenReturn(ciudad);

        List<Reserva> reservasEnCiudad = sitioWeb.getReservasEnCiudad(ciudad, usuarioMock);

        assertTrue(reservasEnCiudad.contains(reservaMock));
    }

    @Test
    void testUnUsuarioInquilinoPuedeVerLasCiudadesReservadas() {
        Reserva reservaMock = mock(Reserva.class);
        String ciudad = "Buenos Aires";

        when(usuarioMock.getReservas()).thenReturn(Arrays.asList(reservaMock));
        when(reservaMock.getInmueble()).thenReturn(inmueble);
        when(reservaMock.getInquilino()).thenReturn(usuarioMock);
        when(inmueble.getCiudad()).thenReturn(ciudad);

        List<String> ciudadesReservadas = sitioWeb.getCiudadesReservadas(usuarioMock);

        assertTrue(ciudadesReservadas.contains(ciudad));
    }
    
    @Test
    void testUnUsuarioPropietarioConoceLaCantidadDeVecesQueAlquiloSusInmuebles() {

        Reserva reservaMock = mock(Reserva.class);
        Reserva otraReservaMock = mock(Reserva.class);
        EstadoFinalizada estadoFinalizada = mock(EstadoFinalizada.class);
        EstadoConfirmada estadoConfirmada = mock(EstadoConfirmada.class);

        when(reservaMock.getEstadoDeReserva()).thenReturn(estadoFinalizada);
        when(estadoFinalizada.esFinalizado()).thenReturn(true);
        when(otraReservaMock.getEstadoDeReserva()).thenReturn(estadoConfirmada);
        when(estadoConfirmada.esFinalizado()).thenReturn(false);

        when(usuarioMock.getReservas()).thenReturn(Arrays.asList(reservaMock, otraReservaMock));

        assertEquals(1, sitioWeb.cantidadDeVecesQueAlquiloUnPropietarioSusInmuebles(usuarioMock));
    }
	
	@Test
	void testUnUsuarioPropietarioConoceLaCantidadDeVecesQueAlquiloUnInmueble() {
	    Reserva reservaMock = mock(Reserva.class);
	    Reserva unaReservaMock = mock(Reserva.class);
	    EstadoFinalizada estadoFinalizada = mock(EstadoFinalizada.class);

	    when(reservaMock.getEstadoDeReserva()).thenReturn(estadoFinalizada);
	    when(estadoFinalizada.esFinalizado()).thenReturn(true);
	    when(unaReservaMock.getEstadoDeReserva()).thenReturn(estadoFinalizada);

	    when(inmueble.getReservas()).thenReturn(Arrays.asList(reservaMock, unaReservaMock));
	    when(inmueble.getPropietario()).thenReturn(usuarioMock);

	    assertEquals(2, sitioWeb.cantidadDeVecesQueUnPropietarioAlquiloUnInmueble(inmueble, usuarioMock));
	}
	
	@Test
	void testUnUsuarioInquilinoConoceLaCantidadDeVecesQueAlquilo() {

	    Reserva reservaMock = mock(Reserva.class);
	    Reserva otraReservaMock = mock(Reserva.class);
	    EstadoFinalizada estadoFinalizada = mock(EstadoFinalizada.class);
	    EstadoConfirmada estadoConfirmada = mock(EstadoConfirmada.class);

	    when(reservaMock.getEstadoDeReserva()).thenReturn(estadoFinalizada);
	    when(estadoFinalizada.esFinalizado()).thenReturn(true);
	    when(otraReservaMock.getEstadoDeReserva()).thenReturn(estadoConfirmada);
	    when(estadoConfirmada.esFinalizado()).thenReturn(false);

	    when(usuarioMock.getReservas()).thenReturn(Arrays.asList(reservaMock, otraReservaMock));

	    assertEquals(1, sitioWeb.cantidadDeVecesQueAlquiloUnInquilino(usuarioMock));
	}
}