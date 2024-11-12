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
        		(precioMinimo == 0 && precioMaximo == 0) ||
                inmueble.getPrecioBase() >= precioMinimo && 
                (precioMaximo == 0 || inmueble.getPrecioBase() <= precioMaximo))
        .toList();
    }
}
 