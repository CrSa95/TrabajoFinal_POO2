package trabajoFinal.SitioWeb;

import java.util.ArrayList;
import java.util.List;

public class Inmueble {
	
	private List<String> comentarios = new ArrayList<String>();
	
	public void setComentario(String comentario){
		comentarios.add(comentario);
	}
	
	public List<String> getComentarios(){
		return comentarios;
	}
	
}
