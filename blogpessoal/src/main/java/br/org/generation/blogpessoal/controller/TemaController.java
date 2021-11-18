package br.org.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.blogpessoal.model.Tema;
import br.org.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {
	
	//Injeção de dependência
	@Autowired 
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok(temaRepository.findAll());

	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Tema> getById(@PathVariable long id) 
	{
		return temaRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
	}
	
	//Consulta por descrição
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> getByDescricao(@PathVariable String descricao) 
	{
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	//Cria novo tema
	@PostMapping
	public ResponseEntity<Tema> postTema(@RequestBody Tema tema) 
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
	}

	@PutMapping
	public ResponseEntity<Tema> putTema(@RequestBody Tema tema) 
	{
		return temaRepository.findById(tema.getId())
		        .map(resposta -> 
		        { 
		        	return ResponseEntity.ok(temaRepository.save(tema));
		         })
		        .orElse(ResponseEntity.notFound().build());
	}
	
	//Deleta
	@DeleteMapping("/{id}")
	public ResponseEntity <?> deleteTema(@PathVariable long id) 
	{
		return temaRepository.findById(id)
		        .map(resposta -> 
		        {
		            temaRepository.deleteById(id);
		            return ResponseEntity.noContent().build();
		        })
		        .orElse(ResponseEntity.notFound().build());
	}

}
