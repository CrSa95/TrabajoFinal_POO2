package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FiltroCapacidadTestCase {

	private FiltroCapacidad filtro;
    private Inmueble inmueble1;
    private Inmueble inmueble2;
    private Inmueble inmueble3;
    
    @BeforeEach
    public void setUp() {
        filtro = new FiltroCapacidad(4);
        inmueble1 = mock(Inmueble.class);
        inmueble2 = mock(Inmueble.class);
        inmueble3 = mock(Inmueble.class);

        when(inmueble1.getCapacidad()).thenReturn(3);
        when(inmueble2.getCapacidad()).thenReturn(4);
        when(inmueble3.getCapacidad()).thenReturn(5);
    }
    

    @Test
    public void testFiltrarPorCapacidad() {
        List<Inmueble> inmuebles = Arrays.asList(inmueble1, inmueble2, inmueble3);
        List<Inmueble> resultado = filtro.filtrar(inmuebles);

        assertEquals(2, resultado.size());
        assertEquals(inmueble2, resultado.get(0));
        assertEquals(inmueble3, resultado.get(1));
    }
}
