package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private TipoDeInmueble terraza;
    private Inmueble inmueble;
    private Inmueble otroInmueble;
    private String ciudad;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private int capacidad;
    private double precioMinimo;
	private double precioMaximo;
    
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
        terraza = mock(TipoDeInmueble.class);
        inmueble = mock(Inmueble.class);
        otroInmueble = mock(Inmueble.class);
        ciudad = "Buenos Aires";
        fechaEntrada = LocalDate.of(2024, 11, 1);
        fechaSalida = LocalDate.of(2024, 11, 10);
        capacidad = 3;
        precioMinimo = 30;
        precioMaximo = 50;
    }
	
	@Test
    void testUnSitioWebPuedeRegistrarUnUsuario() { 
		
		sitioWeb.registrarUsuario(usuarioMock);
        assertTrue(sitioWeb.getUsuariosRegistrados().contains(usuarioMock));
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
    void testUnSitioWebPuedeDarDeAltaYFiltrarUnaListaDeTiposDeServicios() {
		
		List<TipoDeServicio> listaServicios = new ArrayList<TipoDeServicio>();
		listaServicios.add(gas);
        sitioWeb.altaTipoDeServicio(gas);
        sitioWeb.altaTipoDeServicio(agua);
        assertTrue(sitioWeb.seleccionarTiposDeServicio(listaServicios).contains(gas));
        assertFalse(sitioWeb.seleccionarTiposDeServicio(listaServicios).contains(agua));
    }
	
	@Test
    void testUnSitioWebPuedeDarDeAltaYVerficiarSiEstaUnTipoDeInmueble() {
		
		when(quincho.getTipoDeInmueble()).thenReturn("Quincho");
		when(terraza.getTipoDeInmueble()).thenReturn("Terraza");
		when(dormitorio.getTipoDeInmueble()).thenReturn("Dormitorio");
        sitioWeb.altaTipoDeInmueble(quincho);
        sitioWeb.altaTipoDeInmueble(dormitorio);
        assertTrue(sitioWeb.seleccionarTipoDeInmueble(quincho).get().equals(quincho));
        assertTrue(sitioWeb.seleccionarTipoDeInmueble(terraza).isEmpty());
    }
	
	@Test
    void testUnSitioWebPuedeDarDeAltaUnInmueble() {
		
        sitioWeb.altaInmueble(inmueble);
        assertTrue(sitioWeb.getTodosLosInmuebles().contains(inmueble));
        assertFalse(sitioWeb.getTodosLosInmuebles().contains(otroInmueble));
    }
	
	@Test
    void testUnSitioWebPuedeBuscarInmuebles() {
		
		when(inmueble.getCapacidad()).thenReturn(3);
        when(otroInmueble.getCapacidad()).thenReturn(4);
        
        when(inmueble.getCiudad()).thenReturn("Buenos Aires");
        when(otroInmueble.getCiudad()).thenReturn("Córdoba");
        
        when(inmueble.getFechaInicial()).thenReturn(LocalDate.of(2024, 11, 1));
        when(inmueble.getFechaFinal()).thenReturn(LocalDate.of(2024, 11, 10));
        when(otroInmueble.getFechaInicial()).thenReturn(LocalDate.of(2024, 10, 20));
        when(otroInmueble.getFechaFinal()).thenReturn(LocalDate.of(2024, 10, 25));
        
        when(inmueble.getPrecioBase()).thenReturn(50.0);
        when(otroInmueble.getPrecioBase()).thenReturn(150.0);
        
        sitioWeb.altaInmueble(inmueble);
        sitioWeb.altaInmueble(otroInmueble);
        assertTrue(sitioWeb.buscarInmuebles(ciudad, fechaEntrada, fechaSalida, capacidad, precioMinimo, precioMaximo).contains(inmueble));
        assertTrue(sitioWeb.buscarInmuebles(ciudad, fechaEntrada, fechaSalida, 0, 0, 0).contains(inmueble));
        assertFalse(sitioWeb.buscarInmuebles(ciudad, fechaEntrada, fechaSalida, capacidad, precioMinimo, precioMaximo).contains(otroInmueble));
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
		
		
		when(usuarioMock.cantidadDeVecesQueAlquiloUnInquilino()).thenReturn((long) 10);
		when(usuarioMock2.cantidadDeVecesQueAlquiloUnInquilino()).thenReturn((long) 5);
		when(usuarioMock3.cantidadDeVecesQueAlquiloUnInquilino()).thenReturn((long) 11);
		when(usuarioMock4.cantidadDeVecesQueAlquiloUnInquilino()).thenReturn((long) 3);
		
		assertTrue(sitioWeb.obtenerTopTresInquilinoQueMasAlquilaron().contains(usuarioMock));
		assertTrue(sitioWeb.obtenerTopTresInquilinoQueMasAlquilaron().contains(usuarioMock2));	
		assertTrue(sitioWeb.obtenerTopTresInquilinoQueMasAlquilaron().contains(usuarioMock3));
		assertFalse(sitioWeb.obtenerTopTresInquilinoQueMasAlquilaron().contains(usuarioMock4));	
		
	}
	
	@Test
    void testUnSitioWebPuedeVisualizarUnInmueble() {
		
		when(inmueble.getPropietario()).thenReturn(usuarioMock);
		
		sitioWeb.visualizarInmueble(inmueble);
		
        verify(inmueble).datosDelInmueble();
        verify(inmueble.getPropietario()).datosDelPropietario(inmueble);
    }
	
}