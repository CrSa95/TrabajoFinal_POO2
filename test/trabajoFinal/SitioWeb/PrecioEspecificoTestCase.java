package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PrecioEspecificoTestCase {

	private PrecioEspecifico precioEspecifico;
	
	@Test
    void testCrearPrecioEspecifico() {
        LocalDate fechaInicial = LocalDate.of(2024, 1, 1);
        LocalDate fechaFinal = LocalDate.of(2024, 12, 31);
        double precio = 100.0;

        precioEspecifico = new PrecioEspecifico(fechaInicial, fechaFinal, precio);

        assertNotNull(precioEspecifico);
        assertEquals(100.0, precioEspecifico.getPrecioPorPeriodo());
        assertEquals(fechaInicial, precioEspecifico.getFechaInicial());
        assertEquals(fechaFinal, precioEspecifico.getFechaFinal());
    }

}
