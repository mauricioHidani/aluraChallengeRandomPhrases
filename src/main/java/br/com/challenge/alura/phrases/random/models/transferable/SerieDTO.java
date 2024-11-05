package br.com.challenge.alura.phrases.random.models.transferable;

public record SerieDTO(
	Long id,
	String titulo,
    String frase,
    String personagem,
    String poster
) {
}
