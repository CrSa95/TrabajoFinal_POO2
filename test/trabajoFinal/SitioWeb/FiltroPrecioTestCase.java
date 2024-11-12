package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FiltroPrecioTestCase {

    private FiltroPrecio filtro;
    private Inmueble inmueble1;
    private Inmueble inmueble2;
    private Inmueble inmueble3;

    @BeforeEach
    public void setUp() {
        inmueble1 = mock(Inmueble.class);
        inmueble2 = mock(Inmueble.class);
        inmueble3 = mock(Inmueble.class);

        when(inmueble1.getPrecioBase()).thenReturn(50.0);
        when(inmueble2.getPrecioBase()).thenReturn(150.0);
        when(inmueble3.getPrecioBase()).thenReturn(250.0);
    } 

    @Test
    public void testFiltrarPorPrecioMinimo() {
        filtro = new FiltroPrecio(100.0, 0);
        
        List<Inmueble> inmuebles = Arrays.asList(inmueble1, inmueble2, inmueble3);
        List<Inmueble> resultado = filtro.filtrar(inmuebles);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(inmueble2));
        assertTrue(resultado.contains(inmueble3));
    }

    @Test
    public void testFiltrarPorPrecioMaximo() {
        filtro = new FiltroPrecio(0, 200.0);
        
        List<Inmueble> inmuebles = Arrays.asList(inmueble1, inmueble2, inmueble3);
        List<Inmueble> resultado = filtro.filtrar(inmuebles);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(inmueble1));
        assertTrue(resultado.contains(inmueble2));
    }

    @Test
    public void testFiltrarPorPrecioMinimoYMaximo() {
        filtro = new FiltroPrecio(100.0, 200.0);
        
        List<Inmueble> inmuebles = Arrays.asList(inmueble1, inmueble2, inmueble3);
        List<Inmueble> resultado = filtro.filtrar(inmuebles);

        assertEquals(1, resultado.size());
        assertEquals(inmueble2, resultado.get(0));
    }
}
