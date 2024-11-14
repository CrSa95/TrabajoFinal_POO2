package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioTestCase {
	
	private Usuario usuario;
	private Usuario otroUsuarioMock;
	private SitioWeb sitioWebMock;
	private Reserva reservaMock;
    private Inmueble inmuebleMock;
    private String ciudad;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private int capacidad;
    private double precioMinimo;
	private double precioMaximo;
	private Categoria categoriaMock;
	private Categoria otraCategoriaMock;
	private TipoDeInmueble tipoDeInmuebleMock;
	private TipoDeServicio gas;
    private Foto foto;
    private PoliticaDeCancelacion politicaMock;
    private SolicitudDeReserva solicitudMock;
	
	
	@BeforeEach
    public void setUp() {
    	
		usuario = new Usuario("Cristian Sanabria", "crissanabria@gmail.com", 1160583214);
    	sitioWebMock = mock(SitioWeb.class);
    	otroUsuarioMock = mock(Usuario.class);
    	reservaMock = mock(Reserva.class);
    	inmuebleMock = mock(Inmueble.class);
    	ciudad = "Buenos Aires";
        fechaEntrada = LocalDate.of(2024, 11, 1);
        fechaSalida = LocalDate.of(2024, 11, 10);
        capacidad = 3;
        precioMinimo = 30;
        precioMaximo = 50;
        usuario.registrarEnSitioWeb(sitioWebMock);
        categoriaMock = mock(Categoria.class);
        otraCategoriaMock = mock(Categoria.class);
        tipoDeInmuebleMock = mock(TipoDeInmueble.class);
        gas = mock(TipoDeServicio.class);
        foto = mock(Foto.class);
        politicaMock = mock(PoliticaDeCancelacion.class);
        solicitudMock = mock(SolicitudDeReserva.class);
        
    }
	
	@Test
    void testUnUsuarioPuedeRegistrarseEnSitioWeb() {
		
        assertTrue(usuario.getSitioWeb().equals(sitioWebMock));
        assertTrue(usuario.getFechaDeRegistro().isEqual(LocalDate.now()));
    }
	
	@Test
    void testUnUsuarioPuedeSaberLaCantidadDeTiempoQueEstaRegistrado() {
		
        assertTrue(usuario.cantidadTiempoRegistrado() == 0);
    }
	
	@Test
    void testUnUsuarioPuedeActualizarSuNombreCompleto() {
		
		usuario.actualizarNombreCompleto("Cristian Pablo Sanabria");
        assertTrue(usuario.getNombre().equals("Cristian Pablo Sanabria"));
    }
	 
	@Test
    void testUnUsuarioPuedeActualizarSuMail() {
		
		usuario.actualizarMail("crisss@gmail.com");
        assertTrue(usuario.getMail().equals("crisss@gmail.com"));
    }
	
	@Test
    void testUnUsuarioPuedeActualizarSuTelefono() {
		
		usuario.actualizarNumeroDeTelefono(42230149);
        assertTrue(usuario.getTelefono() == 42230149);
    }
	 
	@Test
    void testUnUsuarioPuedeEnviarYRecibirUnMail() {
		
		when(otroUsuarioMock.getContenidoMail()).thenReturn("Hello World");
		usuario.recibirMail("Hello World");
		usuario.enviarMail(usuario.getContenidoMail(),otroUsuarioMock);
		assertTrue(otroUsuarioMock.getContenidoMail().equalsIgnoreCase("Hello World"));
		assertTrue(usuario.getContenidoMail().equalsIgnoreCase("Hello World"));
    }
	
	@Test
    void testUnUsuarioPuedeRegistrarYEliminarUnaReserva() {
		when(reservaMock.getUsuario()).thenReturn(usuario);
		
		usuario.registrarReserva(reservaMock);
		assertTrue(usuario.getReservas().contains(reservaMock));
		usuario.eliminarReserva(reservaMock);
		assertFalse(usuario.getReservas().contains(reservaMock));
    }
	
	@Test
    void testUnUsuarioPuedeBuscarInmuebles() {
		
		List<Inmueble> inmuebles = new ArrayList<Inmueble>();
		inmuebles.add(inmuebleMock);
		when(sitioWebMock.buscarInmuebles(ciudad, fechaEntrada, fechaSalida, capacidad, precioMinimo, precioMaximo)).thenReturn(inmuebles);
        
        assertTrue(usuario.buscarInmuebles(ciudad, fechaEntrada, fechaSalida, capacidad, precioMinimo, precioMaximo).contains(inmuebleMock));
    }
	
	@Test
    void testUnUsuarioPuedeVisualizarUnInmueble() {
		
		when(inmuebleMock.getPropietario()).thenReturn(otroUsuarioMock);
		
		usuario.visualizarInmueble(inmuebleMock);
		
        verify(inmuebleMock).datosDelInmueble();
        verify(inmuebleMock.getPropietario()).datosDelPropietario(inmuebleMock);
    }
	
	@Test
    void testUnUsuarioPuedeRealizarUnaReserva() {
		
		usuario.realizarReservar(inmuebleMock, usuario, FormaDePago.EFECTIVO, fechaSalida, fechaEntrada);
        verify(inmuebleMock).realizarReservaDelInmueble(usuario, FormaDePago.EFECTIVO, fechaSalida, fechaEntrada);
    }

	@Test
    void testUnUsuarioPropietarioPuedeRankearUnInquilino() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(sitioWebMock.getCategoriaEspecificaInquilino(categoriaMock)).thenReturn(true);
		
		assertDoesNotThrow(() -> usuario.rankearInquilino(reservaMock, categoriaMock, 5));
		assertFalse(usuario.getRankeosInquilino().isEmpty());
	}
	
	@Test
    void testUnUsuarioPropietarioNoPuedeRankearUnInquilino() throws Exception {
		
		assertThrows(Exception.class, () -> {usuario.rankearInquilino(reservaMock, categoriaMock, 5);});
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(sitioWebMock.getCategoriaEspecificaInquilino(categoriaMock)).thenReturn(false);
		assertThrows(Exception.class, () -> {usuario.rankearInquilino(reservaMock, categoriaMock, 5);});
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeDejarleUnComentarioAUnInquilino() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuario.setComentarioInqulino(reservaMock, "Buen inquilino"));
		assertFalse(usuario.getComentariosInquilino().isEmpty());
	}
	
	@Test
    void testUnUsuarioPropietarioNoPuedeDejarleUnComentarioAUnInquilino() throws Exception {
		
		assertThrows(Exception.class, () -> {usuario.setComentarioInqulino(reservaMock, "Sucio");});
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeConocerLaInformacionDeUnInquilino() {
		
		usuario.datosDelInquilino();

	}
	
	@Test
    void testUnUsuarioPropietarioPuedeAumentarYRestarLaCantidadDeVecesQueAlquilo() {
		
		assertTrue(usuario.getCantidadDeVecesQueAlquilo() == 0);
		usuario.sumarCantidadDeVecesQueAlquilo();
		assertTrue(usuario.getCantidadDeVecesQueAlquilo() == 1);
		usuario.restarCantidadDeVecesQueAlquilo();
		assertTrue(usuario.getCantidadDeVecesQueAlquilo() == 0);
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeRecibirResarcimiento() {
		
		assertTrue(usuario.recibirResarcimiento(100) == 100);
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeModificarElPrecioBase() {
		
		usuario.actualizarPrecioBase(inmuebleMock, 100);
		verify(inmuebleMock).modificarPrecioBase(100);
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeModificarFormasDePago() {
		
		usuario.modificarFormasDePago(inmuebleMock,FormaDePago.EFECTIVO);
		verify(inmuebleMock).modificarFormasDePago(FormaDePago.EFECTIVO);
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeModificarLosPreciosEspecificos() throws Exception {
		
		usuario.modificarPreciosEspecificos(inmuebleMock, fechaEntrada, fechaSalida, 100);
		verify(inmuebleMock).modificarPreciosEspecificos(fechaEntrada, fechaSalida, 100);
		
	}
	
	
	@Test
    void testUnUsuarioPropietarioConoceSusInmueblesAlquilados() {
		
		assertTrue(usuario.inmueblesAlquilados().size() == 0);
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeRecibirUnaSolicitudDeResera() {
		
		usuario.recibirSolicitudReserva(solicitudMock);
		verify(solicitudMock).aprobarSolicitud();
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeConocerLaInformacionDeUnPropietario() {
		
		usuario.datosDelPropietario(inmuebleMock);
		
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeRankearUnPropietario() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(sitioWebMock.getCategoriaEspecificaPropietario(categoriaMock)).thenReturn(true);
		
		assertDoesNotThrow(() -> usuario.rankPropietario(reservaMock, categoriaMock, 5));
		assertFalse(usuario.getRankeosPropietario().isEmpty());
	}
	
	@Test
    void testUnUsuarioInquilinoNoPuedeRankearUnPropietario() throws Exception {
		
		assertThrows(Exception.class, () -> {usuario.rankPropietario(reservaMock, categoriaMock, 5);});
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(sitioWebMock.getCategoriaEspecificaPropietario(categoriaMock)).thenReturn(false);
		assertThrows(Exception.class, () -> {usuario.rankPropietario(reservaMock, categoriaMock, 5);});
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeDejarleUnComentarioAUnPropietario() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuario.setComentarioPropietario(reservaMock, "Hermoso"));
		assertFalse(usuario.getComentariosPropietario().isEmpty());
	}
	
	@Test
    void testUnUsuarioInquilinoNoPuedeDejarleUnComentarioAUnPropietario() throws Exception {
		
		assertThrows(Exception.class, () -> {usuario.setComentarioPropietario(reservaMock, "Hermoso");});
		
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeDejarleUnComentarioAUnInmueble() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuario.setComentarioInmueble(reservaMock, inmuebleMock ,"Hermoso inmueble"));
		verify(inmuebleMock).setComentario("Hermoso inmueble");
	}
	
	@Test
    void testUnUsuarioInquilinoNoPuedeDejarleUnComentarioAUnInmueble() throws Exception {
		
		assertThrows(Exception.class, () -> {usuario.setComentarioInmueble(reservaMock, inmuebleMock ,"Hermoso inmueble");});
		
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeRankearUnInmueble() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(sitioWebMock.getCategoriaEspecificaInmueble(categoriaMock)).thenReturn(true);
		
		assertDoesNotThrow(() -> usuario.rankInmueble(reservaMock, inmuebleMock, categoriaMock, 5));
	}
	
	@Test
    void testUnUsuarioInquilinoNoPuedeRankearUnInmueble() throws Exception {
		
		assertThrows(Exception.class, () -> {usuario.rankInmueble(reservaMock, inmuebleMock, categoriaMock, 5);});
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(sitioWebMock.getCategoriaEspecificaPropietario(categoriaMock)).thenReturn(false);
		assertThrows(Exception.class, () -> {usuario.rankInmueble(reservaMock, inmuebleMock, categoriaMock, 5);});
	}
	
	@Test
    void testUnUsuarioPuedeCalcularSuPromedioTotalDeRanking() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(sitioWebMock.getCategoriaEspecificaPropietario(categoriaMock)).thenReturn(true);
		
		assertDoesNotThrow(() -> usuario.rankPropietario(reservaMock, categoriaMock, 5));
		assertTrue(usuario.calcularPromedioTotal(usuario.getRankeosPropietario()) == 5);
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeActualizarUnValorEspecificoDeSuRanking() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(sitioWebMock.getCategoriaEspecificaPropietario(categoriaMock)).thenReturn(true);
		when(sitioWebMock.getCategoriaEspecificaPropietario(otraCategoriaMock)).thenReturn(true);
		when(categoriaMock.nombreCategoria()).thenReturn("Amable");
		when(otraCategoriaMock.nombreCategoria()).thenReturn("Amable");
		
		assertDoesNotThrow(() -> usuario.rankPropietario(reservaMock, categoriaMock, 5));
		assertDoesNotThrow(() -> usuario.rankPropietario(reservaMock, otraCategoriaMock, 3));
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeDarDeAltaUnInmueble() throws Exception {
		List<TipoDeServicio> listaServicios = new ArrayList<TipoDeServicio>();
		listaServicios.add(gas);
		
		List<Foto> listaFotos = new ArrayList<Foto>();
		listaFotos.add(foto);
		
		when(sitioWebMock.seleccionarTipoDeInmueble(tipoDeInmuebleMock)).thenReturn(java.util.Optional.of(tipoDeInmuebleMock));
		
		usuario.altaInmueble(tipoDeInmuebleMock, 2332, "Argentina", ciudad, "calle 43 N° 898", 
							 listaServicios, capacidad, listaFotos, LocalTime.now(), LocalTime.now(), 
							 politicaMock, precioMaximo, fechaEntrada, fechaSalida);
		assertFalse(usuario.getInmueblesDadosDeAlta().isEmpty());
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeActualizarUnValorEspecificoDeSuRanking() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(sitioWebMock.getCategoriaEspecificaInquilino(categoriaMock)).thenReturn(true);
		when(sitioWebMock.getCategoriaEspecificaInquilino(otraCategoriaMock)).thenReturn(true);
		when(categoriaMock.nombreCategoria()).thenReturn("Amable");
		when(otraCategoriaMock.nombreCategoria()).thenReturn("Amable");
		
		assertDoesNotThrow(() -> usuario.rankearInquilino(reservaMock, categoriaMock, 5));
		assertDoesNotThrow(() -> usuario.rankearInquilino(reservaMock, otraCategoriaMock, 3));
		
	}
	
	@Test
    void testUnUsuarioPuedeVerSusReservasFuturas() {
		when(reservaMock.getUsuario()).thenReturn(usuario);
		when(reservaMock.getFechaDeIngreso()).thenReturn(LocalDate.of(2025, 1, 23));
		
		usuario.registrarReserva(reservaMock);
		assertTrue(usuario.getReservasFuturas(LocalDate.now()).contains(reservaMock));
    }
	
	@Test
    void testUnUsuarioPuedeVerSusReservasEnCiudad() {
		when(reservaMock.getUsuario()).thenReturn(usuario);
		when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
		when(inmuebleMock.getCiudad()).thenReturn(ciudad);
		
		usuario.registrarReserva(reservaMock);
		assertTrue(usuario.getReservasEnCiudad(ciudad).contains(reservaMock));
    }
	
	@Test
    void testUnUsuarioPuedeVerLasCiudadesReservadas() {
		when(reservaMock.getUsuario()).thenReturn(usuario);
		when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
		when(inmuebleMock.getCiudad()).thenReturn(ciudad);
		
		usuario.registrarReserva(reservaMock);
		assertTrue(usuario.getCiudadesReservadas().contains(ciudad));
    }
	
	@Test
    void testUnUsuarioPuedeCancelarUnaReserva() {
		
		when(reservaMock.getUsuario()).thenReturn(usuario);
		
		usuario.registrarReserva(reservaMock);
		assertTrue(usuario.getReservas().contains(reservaMock));
		usuario.cancelarReserva(reservaMock);
		assertFalse(usuario.getReservas().contains(reservaMock));
    }
	
	@Test
    void testUnUsuarioConoceLaCantidadDeVecesQueAlquilo() {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		usuario.registrarReserva(reservaMock);
		
		assertTrue(usuario.cantidadDeVecesQueAlquiloUnInquilino() == 1);
    }
	
}
