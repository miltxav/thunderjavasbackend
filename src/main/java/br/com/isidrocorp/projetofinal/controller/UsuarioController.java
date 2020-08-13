package br.com.isidrocorp.projetofinal.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.isidrocorp.projetofinal.dao.UsuarioDAO;
import br.com.isidrocorp.projetofinal.model.Usuario;

/* criei minha classe RESTController para que ela possa atender URL
 * 
 * Só que essa classe vai acessar bancos de dados
 */
@RestController
@CrossOrigin("*")
public class UsuarioController {
	
	/* injecao de dependência
	 * 
	 * definicao: na prática não sou eu (programador) que faço a instanciação do objeto (eu não dou NEW)
	 * mas delego esta tarefa para a máquina virtual Java
	 * 
	 * Porém eu tenho um agravante aqui: eu não defini nenhuma classe que implementa a interface
	 * UsuarioDAO. A "mágica" é que o próprio SpringBoot cria uma implementação em tempo real
	 * desta interface (na prática o JPA pega a definição do objeto Usuário e cria em tempo
	 * de execução as string SQL que irão inserir/recuperar/alterar etc a tabel)
	 */
	@Autowired
	private UsuarioDAO dao;
	
	@GetMapping("/usuarios")
	public ArrayList<Usuario> getAll(){
		ArrayList<Usuario> lista;
		// consultei do banco (único detalhe: o findAll não retorna ArrayList, mas retorna uma interface compatível,
		//                     bastando apenas converter)
		lista = (ArrayList<Usuario>)dao.findAll();
		// para não mostrar as senhas
		for (Usuario u: lista) {
			u.setSenha("*********");
		}
		return lista;
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> getUsuarioPeloId(@PathVariable int id) {
		Usuario resultado = dao.findById(id).orElse(null);
		if (resultado != null) {  // meu usuario Existe
			return ResponseEntity.ok(resultado);
		}
		else {
			return ResponseEntity.status(404).build();
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<Usuario> login(@RequestBody Usuario dadosLogin) {
		Usuario resultado = dao.findByEmail(dadosLogin.getEmail());
		// encontrei o usuário?
		if (resultado != null) { 
			// as senhas (do banco e enviadas no formulário) conferem?
			if (resultado.getSenha().equals(dadosLogin.getSenha())) {
				resultado.setSenha("************");
				return ResponseEntity.ok(resultado);
			}
			else {
				// se as senhas não conferem, significa que o usuário existe, porém a senha está errada
				return ResponseEntity.status(401).build(); // retorna o código 401 - Unauthorized
			}
		}
		else {
			// se não encontrei, retorna um código de NOT FOUND (404)
			return ResponseEntity.notFound().build();
		}
		
		
	}
}
