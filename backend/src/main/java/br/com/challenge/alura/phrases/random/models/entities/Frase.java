package br.com.challenge.alura.phrases.random.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "frases")
public class Frase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final Long id;

	@Column(length = 128)
	private final String titulo;

	@Column(columnDefinition = "TEXT")
	private final String frase;

	@Column(length = 128)
	private final String personagem;

	@Column
	private final String poster;

	protected Frase() {
		this.id = 0L;
		this.titulo = "";
		this.frase = "";
		this.personagem = "";
		this.poster = "";
	}

	public Frase(final Long id,
				 final String titulo,
				 final String frase,
				 final String personagem,
				 final String poster) {
		this.poster = poster;
		this.personagem = personagem;
		this.frase = frase;
		this.titulo = titulo;
		this.id = id;
	}

	public Long id() {
		return id;
	}

	public String titulo() {
		return titulo;
	}

	public String frase() {
		return frase;
	}

	public String personagem() {
		return personagem;
	}

	public String poster() {
		return poster;
	}
}
