package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FiltroFechasTestCase {

    private FiltroFechas filtro;
    private Inmueble inmueble1;
    private Inmueble inmueble2;

    @BeforeEach
    public void setUp() {
        LocalDate fechaEntrada = LocalDate.of(2024, 11, 1);
        LocalDate fechaSalida = LocalDate.of(2024, 11, 10);
        filtro = new FiltroFechas(fechaEntrada, fechaSalida);

        inmueble1 = mock(Inmueble.class);
        inmueble2 = mock(Inmueble.class);

        when(inmueble1.getFechaInicial()).thenReturn(LocalDate.of(2024, 11, 1));
        when(inmueble1.getFechaFinal()).thenReturn(LocalDate.of(2024, 11, 10));
        when(inmueble2.getFechaInicial()).thenReturn(LocalDate.of(2024, 10, 20));
        when(inmueble2.getFechaFinal()).thenReturn(LocalDate.of(2024, 10, 25));
    }

    @Test
    public void testFiltrar() {
        List<Inmueble> inmuebles = Arrays.asList(inmueble1, inmueble2);
        List<Inmueble> resultado = filtro.filtrar(inmuebles);

        assertEquals(1, resultado.size());
        assertEquals(inmueble1, resultado.get(0));
    }
}