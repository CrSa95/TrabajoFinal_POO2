package trabajoFinal.SitioWeb;

import java.time.LocalDateTime;
import java.util.List;

public class FiltroFechas implements Filtro{

    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;

    public FiltroFechas(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    @Override
    public List<Inmueble> filtrar(List<Inmueble> inmuebles) {
        return inmuebles.stream()
                .filter(inmueble -> 
                        !inmueble.getCheckIn().isAfter(fechaEntrada) &&
                        !inmueble.getCheckOut().isBefore(fechaSalida))
                .toList();
    }
}
