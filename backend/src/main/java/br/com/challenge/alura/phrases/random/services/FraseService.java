package br.com.challenge.alura.phrases.random.services;

import br.com.challenge.alura.phrases.random.exceptions.InformationProcessingException;
import br.com.challenge.alura.phrases.random.exceptions.NotFoundException;
import br.com.challenge.alura.phrases.random.models.entities.Frase;
import br.com.challenge.alura.phrases.random.models.transferable.OMDBSerieDTO;
import br.com.challenge.alura.phrases.random.models.transferable.SerieDTO;
import br.com.challenge.alura.phrases.random.repositories.FraseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FraseService {

	private final FraseRepository repository;
	private final ChatGPTService gptService;
	private final OMDBService omdbService;

	public FraseService(final FraseRepository repository,
						final ChatGPTService gptService,
						final OMDBService omdbService) {
		this.repository = repository;
		this.gptService = gptService;
		this.omdbService = omdbService;
	}

	/**<h1>Find by Title</h1>
	 * Encontra série por titulo na base de dados.
	 *
	 * @param title Titulo usado para encontrar série na base de dados.
	 * @return Série encontrada.
	 * */
	public Optional<SerieDTO> findByTitle(String title) {
		final var phraseFound = repository.findByTituloContainingIgnoreCase(title);
		if (!phraseFound.isEmpty()) {
			return Optional.of( converterToSerieDTO(phraseFound.get(0)) );
		}
		return Optional.empty();
	}

	/**<h1>Generate Phrase by Title And Limited By</h1>
	 * Gera frases dos personagens limitados da série indicada usando a API da OpenAI.
	 *
	 * @param title Representa o titulo da série para encontrar os personagens.
	 * @param limit Limite de geração de frases por personagem.
	 * @return Lista de frases dos personagens indicados pelo titulo da série informada.
	 * @since Tuesday, 5th November 2024
	 * */
	public List<SerieDTO> generatePhraseByTitleAndLimitedBy(String title, Integer limit) {
		final var founded = findByTitle(title);
		Optional<OMDBSerieDTO> serie = null;

		if (founded.isEmpty()) {
			serie = omdbService.findByTitle(title);

			if (serie.isEmpty()) {
				throw new NotFoundException(
					"Não foi possivel encontrar a série com o titulo (%s) informado.".formatted(title)
				);
			}
		} else {
			final var serieFounded = founded.get();
			serie = Optional.of( new OMDBSerieDTO(serieFounded.titulo(), serieFounded.poster()) );
		}

		final var persons = gptService.generatePersonsBySeriesTitle(title, limit);
		final var result = generatePhrasesByPersons(persons, serie);

		result.forEach(s -> repository.save(new Frase(s.titulo(), s.frase(), s.personagem(), s.poster())));
		return result;
	}

	/**<h1>Find Random</h1>
	 * Encontra a frase de forma randomica presente na base de dados.
	 *
	 * @throws NotFoundException Quando não for encontrado nehuma frase na base de dados.
	 * @return Frase randomica encontrada na base de dados convertida para uma {@link SerieDTO}.
	 * @since Tuesday, 5th November 2024
	 * */
	public SerieDTO findRandom() {
		final var founded = repository.findRandom();

		if (founded.isPresent()) {
			return converterToSerieDTO(
				assignPhraseWhenNonexistent(founded.get())
			);
		}
		throw new NotFoundException("Frase aleatória não encontrada");
	}

	/**<h1>Generate Phrases by Persons</h1>
	 * Gerar as frase dos personagens listados usando a API da OpenAI.
	 *
	 * @param persons Lista de personagens da série.
	 * @param omdb Série indicada para consultar as frases dos personagens.
	 * @return Lista das frases dos personagens de uma série especifica.
	 * @since Tuesday, 5th November 2024
	 * */
	private List<SerieDTO> generatePhrasesByPersons(List<String> persons, Optional<OMDBSerieDTO> omdb) {
		return persons.stream().map(p -> {
			final var serie = omdb.get();
			final var phrase = gptService.generatePhraseByPerson(new Frase(serie.title(), p, serie.poster()));
			return new SerieDTO(serie.title(), phrase, p, serie.poster());
		}).collect(Collectors.toList());
	}

	/** <h1>Converter to Serie Data Transfer Object</h1>
	 * Converte a entidade {@link Frase} para o DTO {@link SerieDTO}.
	 *
	 * @param frase Entidade a ser convertida.
	 * @throws InformationProcessingException Quando for informado uma entidade NULL.
	 * @return Conversão de entidade para data transfer object.
	 * @since Tuesday, 5th November 2024
	 * */
	private SerieDTO converterToSerieDTO(Frase frase) {
		if (frase == null) {
			throw new InformationProcessingException(
				"Não é possível realizar a operação com informações inconsistentes"
			);
		}
		return new SerieDTO(frase.titulo(), frase.frase(), frase.personagem(), frase.poster());
	}

	/** <h1>Assign Phrase When Nonexistent</h1>
	 * Realiza a atribuição de frase dita pelo personagem indicada no titulo da entidade informada quando não houver
	 * frase.
	 *
	 * @param frase Entidade utilizada para confirmação da existência da frase.
	 * @throws NotFoundException Quando não for possivel a geração da frase pelo {@link ChatGPTService}.
	 * @return Quando não houver frase presente será gerada pelo serviço da {@link ChatGPTService}, caso houver, será
	 * retornado a frase existente.
	 * @since Tuesday, 5th November 2024
	 * */
	private Frase assignPhraseWhenNonexistent(final Frase frase) {
		if (frase.frase().isBlank() || frase.frase() == null) {
			final var phrase = gptService.generatePhraseByPerson(frase);
			final Frase newFrase = new Frase(
				frase.id(), frase.titulo(), phrase, frase.personagem(), frase.poster()
			);
			return newFrase;
		}
		return frase;
	}
}
