package br.com.isidrocorp.projetofinal.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import br.com.isidrocorp.projetofinal.model.Agencia;
import br.com.isidrocorp.projetofinal.model.Agendamento;

public interface AgendamentoDAO extends CrudRepository<Agendamento, Integer> {
	
	public ArrayList<Agendamento> findAllByNomeClienteContaining(String nomeCliente);
	
	public ArrayList<Agendamento> findAllByAgencia(Agencia agencia);
		
	public ArrayList<Agendamento> findAllByAgenciaAndNomeClienteContaining(Agencia agencia, String nomeCliente);
	
	public ArrayList<Agendamento> findAllByDataAgendamento(LocalDate dataAgendamento);
	
	public ArrayList<Agendamento> findAllByNomeClienteContainingAndDataAgendamento(String nomeCliente, LocalDate dataAgendamento);
	
	public ArrayList<Agendamento> findAllByAgenciaAndDataAgendamento(Agencia agencia, LocalDate dataAgendamento);
	
	public ArrayList<Agendamento> findAllByAgenciaAndDataAgendamentoAndNomeClienteContaining(Agencia agencia, LocalDate dataAgendamento, String nome);
}
