package br.com.challenge.alura.phrases.random.controllers;

import br.com.challenge.alura.phrases.random.models.transferable.SerieDTO;
import br.com.challenge.alura.phrases.random.services.FraseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class FraseController {

	private final FraseService service;

	public FraseController(final FraseService service) {
		this.service = service;
	}

	@GetMapping("/frases")
	public ResponseEntity<SerieDTO> randomPhrase() {
		return ResponseEntity.ok(
			service.findRandom()
		);
	}

	@PostMapping
	public ResponseEntity<List<SerieDTO>> generatePhraseByTitleByLimitPersons(
			@RequestParam String title,
			@RequestParam(defaultValue = "1") Integer limit
	) {
		return ResponseEntity.ok(
			service.generatePhraseByTitleAndLimitedBy(title, limit)
		);
	}
}
