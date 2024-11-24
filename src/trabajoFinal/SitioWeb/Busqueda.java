package trabajoFinal.SitioWeb;

import java.util.ArrayList; 
import java.util.List;

public class Busqueda {

    private List<Filtro> filtros;

    public Busqueda(FiltroCiudad filtroCiudad, FiltroFechas filtroFechas) {
        this.filtros = new ArrayList<>();
        this.filtros.add(filtroCiudad);
        this.filtros.add(filtroFechas);
    }

    public void agregarFiltro(Filtro filtro) {
        this.filtros.add(filtro);
    }

    public List<Inmueble> aplicarFiltros(List<Inmueble> inmuebles) {
        List<Inmueble> resultado = inmuebles;
        for (Filtro filtro : filtros) {
            resultado = filtro.filtrar(resultado);
        }
        return resultado;
    }
    
    public List<Filtro> getFiltros(){
    	return this.filtros;
    }
}
