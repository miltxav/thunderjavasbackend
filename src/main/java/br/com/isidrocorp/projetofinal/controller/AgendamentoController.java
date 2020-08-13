package br.com.isidrocorp.projetofinal.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.isidrocorp.projetofinal.dao.AgendamentoDAO;
import br.com.isidrocorp.projetofinal.model.Agencia;
import br.com.isidrocorp.projetofinal.model.Agendamento;

@RestController
@CrossOrigin("*")
public class AgendamentoController {
    
	@Autowired
	AgendamentoDAO dao;
	
	@PostMapping("/agendamentos/novo")
	public ResponseEntity<Agendamento> inserirNovoAgendamento(@RequestBody Agendamento novo){
		
//		System.out.println("DEBUG - HORA - "+novo.getHoraAgendamento().toString());
//		System.out.println("DEBUG - DATA - "+novo.getDataAgendamento().toString());
		
		try {
			dao.save(novo);
			return ResponseEntity.status(201).body(novo);
		}
		catch(Exception ex) {
			return ResponseEntity.status(400).build(); // requisicao errada
		}
	}
	
	@GetMapping("/agendamentos/todos")
	public ArrayList<Agendamento> recuperarTodos(){
		ArrayList<Agendamento> lista;
		lista = (ArrayList<Agendamento>)dao.findAll();
		return lista;
	}
	
	@GetMapping("/agendamentos/filtrarporcliente")
	public ArrayList<Agendamento> filtrarPorCliente(@RequestParam(name="nomecli") String nome){
		System.out.println("nome do cliente = "+nome);
		return dao.findAllByNomeClienteContaining(nome);
	}
	
	@GetMapping("/agendamentos/filtrarporagencia")
	public ArrayList<Agendamento> filtrarPorAgencia(@RequestParam(name="agencia") int agencia){
		System.out.println("agencia = "+agencia);
		Agencia ag = new Agencia();
		ag.setId(agencia);
		return dao.findAllByAgencia(ag);
	}
	
	@GetMapping("/agendamentos/filtrarpordataagendamento")
	public ArrayList<Agendamento> filtrarPorDataAgendamento(@RequestParam(name="dataAgendamento") String dataAgendamento) {
		LocalDate localDate = LocalDate.parse(dataAgendamento);
		System.out.println("dataAgendamento = " + localDate);
		return dao.findAllByDataAgendamento(localDate);
	}
	
	@GetMapping("/agendamentos/filtrarporclienteeagencia")
	public ArrayList<Agendamento> filtrarPorClienteEAgencia(@RequestParam(name="nomecli") String nome, @RequestParam(name="agencia") int agencia) {
		System.out.println("nome = " + nome + " agencia = " + agencia);
		Agencia ag1 = new Agencia();
		ag1.setId(agencia);
		return dao.findAllByAgenciaAndNomeClienteContaining(ag1, nome);
	}
	
	@GetMapping("/agendamentos/filtrarporclienteedata")
	public ArrayList<Agendamento> filtrarPorClienteEData(@RequestParam(name="nomecli") String nome, @RequestParam(name="dataAgendamento") String dataAgendamento) {
		System.out.println("nome = " + nome + " data = " + dataAgendamento);
		LocalDate localDate = LocalDate.parse(dataAgendamento);
		return dao.findAllByNomeClienteContainingAndDataAgendamento(nome, localDate);
	}
	
	@GetMapping("/agendamentos/filtrarporagenciaedata")
	public ArrayList<Agendamento> filtrarPorAgenciaEData(@RequestParam(name="agencia") int agencia, @RequestParam(name="dataAgendamento") String dataAgendamento) {
		System.out.println("agencia = " + agencia + " data = " + dataAgendamento);
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		LocalDate localDate = LocalDate.parse(dataAgendamento);
		Agencia ag2 = new Agencia();
		ag2.setId(agencia);
		return dao.findAllByAgenciaAndDataAgendamento(ag2, localDate);
	}
	
	@GetMapping("/agendamentos/filtrarporagenciaedataecliente")
	public ArrayList<Agendamento> filtrarPorAgenciaEDataECliente(@RequestParam(name="agencia") int agencia, @RequestParam(name="dataAgendamento") String dataAgendamento, @RequestParam(name="nomecli") String nome) {
		System.out.println("agencia = " + agencia + " data = " + dataAgendamento);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		LocalDate localDate = LocalDate.parse(dataAgendamento, formatter);
		Agencia ag2 = new Agencia();
		ag2.setId(agencia);
		return dao.findAllByAgenciaAndDataAgendamentoAndNomeClienteContaining(ag2, localDate, nome);
	}
}
