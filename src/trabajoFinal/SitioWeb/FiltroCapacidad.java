package trabajoFinal.SitioWeb;

import java.util.List;

public class FiltroCapacidad implements Filtro {
	public int capacidad;

	public FiltroCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public List<Inmueble> filtrar(List<Inmueble> inmuebles) {
        return inmuebles.stream()
                .filter(inmueble -> inmueble.getCapacidad() >= capacidad)
                .toList();
    }
}