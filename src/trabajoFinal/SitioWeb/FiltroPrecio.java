package trabajoFinal.SitioWeb;

import java.util.List;

public class FiltroPrecio implements Filtro{
	private double precioMinimo;
    private double precioMaximo;

    public FiltroPrecio(double precioMinimo, double precioMaximo) {
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
    }

    @Override
    public List<Inmueble> filtrar(List<Inmueble> inmuebles) {
        return inmuebles.stream()
                .filter(inmueble ->
                        inmueble.getPrecioPorDia() >= precioMinimo &&
                        inmueble.getPrecioPorDia() <= precioMaximo)
                .toList();
    }
}
