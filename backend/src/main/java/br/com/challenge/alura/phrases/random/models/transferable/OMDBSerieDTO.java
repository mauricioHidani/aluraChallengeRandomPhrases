package br.com.challenge.alura.phrases.random.models.transferable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OMDBSerieDTO(
	@JsonAlias("Title") String title,
	@JsonAlias("Poster") String poster
) {
}
