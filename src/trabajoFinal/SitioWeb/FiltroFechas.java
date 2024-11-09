package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.util.List;

public class FiltroFechas implements Filtro{

    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;

    public FiltroFechas(LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    @Override
    public List<Inmueble> filtrar(List<Inmueble> inmuebles) {
        return inmuebles.stream()
                .filter(inmueble -> 
                        !inmueble.getFechaInicial().isAfter(fechaEntrada) &&
                        !inmueble.getFechaFinal().isBefore(fechaSalida))
                .toList();
    }
}
