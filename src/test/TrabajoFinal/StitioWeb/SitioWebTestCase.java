package test.TrabajoFinal.StitioWeb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import trabajoFinal.SitioWeb.SitioWeb;
import trabajoFinal.SitioWeb.Usuario;

public class SitioWebTestCase {
	
	private SitioWeb unSitioWeb;
	private Usuario usuario;
	
	@BeforeEach
	public void setUp() {
		
		unSitioWeb = new SitioWeb();
		usuario = new Usuario("Cristian Pablo Sanabria", "crissanabria1995@gmail.com", 1160583214);
		
	}
	
	@Test
	public void testUnUsuarioSeRegistraEnUnSitioWeb() {
		
		//verifico que la lista de usuarios del sitio web se encuentra vacia
		assertEquals(0,unSitioWeb.getUsuariosRegistrados().size());
		
		//registro un usuario al sitio web
		unSitioWeb.registrarUsuario(usuario);
		
		//verifico si la lista de usuarios del sitio web se encuentra un usuario registrado
		assertEquals(1,unSitioWeb.getUsuariosRegistrados().size());
		
		//verifico si el ususario se encuentra registrado en el sitio web
		assertTrue(unSitioWeb.usuarioEstaRegistrado(usuario));
		
		//verifico la fecha de registro del ususario
		assertTrue(usuario.getFechaDeRegistro().isEqual(LocalDate.now()));
	}
	
}
