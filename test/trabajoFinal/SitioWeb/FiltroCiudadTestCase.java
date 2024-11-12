package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

public class FiltroCiudadTestCase {

    private FiltroCiudad filtro;
    private Inmueble inmueble1;
    private Inmueble inmueble2;

    @BeforeEach
    public void setUp() {
        filtro = new FiltroCiudad("Buenos Aires");
        inmueble1 = mock(Inmueble.class);
        inmueble2 = mock(Inmueble.class);

        when(inmueble1.getCiudad()).thenReturn("Buenos Aires");
        when(inmueble2.getCiudad()).thenReturn("Córdoba");
    }

    @Test
    public void testFiltrarPorCiudad() {
        List<Inmueble> inmuebles = Arrays.asList(inmueble1, inmueble2);
        List<Inmueble> resultado = filtro.filtrar(inmuebles);

        assertEquals(1, resultado.size());
        assertEquals(inmueble1, resultado.get(0));
    }
}