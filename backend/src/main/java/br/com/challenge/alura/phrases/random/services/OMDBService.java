package br.com.challenge.alura.phrases.random.services;

import br.com.challenge.alura.phrases.random.configs.env.OMDBEnv;
import br.com.challenge.alura.phrases.random.exceptions.InformationProcessingException;
import br.com.challenge.alura.phrases.random.exceptions.InternalSeverErrorException;
import br.com.challenge.alura.phrases.random.models.transferable.OMDBSerieDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class OMDBService {

	private final OMDBEnv env;
	private final ObjectMapper mapper;

	public OMDBService(final OMDBEnv env, final ObjectMapper mapper) {
		this.env = env;
		this.mapper = mapper;
	}

	/**<h1>Find by Title</h1>
	 * Realiza a consulta na API da OMDB para encontrar a série indicada pelo titulo.
	 *
	 * @param title Titulo usado para realizar a consulta da API da OMDB.
	 * @return Resultado da série encontrada na API da OMDB como {@link Optional<OMDBSerieDTO>}.
	 * @throws InternalSeverErrorException Quando não conseguir realizar a consulta a API da OMDB.
	 * */
	public Optional<OMDBSerieDTO> findByTitle(String title) {
		try {
			final var newTitle = title.replace(" ", "+").toLowerCase();
			final var path = "%s/?apiKey=%s&type=series&t=%s".formatted(env.getUri(), env.getApiKey(), newTitle);
			final var client = HttpClient.newHttpClient();
			final var request = HttpRequest.newBuilder().uri(URI.create(path)).build();
			final var responseBody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

			return Optional.of( converterToDTO(responseBody) );

		} catch (UncheckedIOException | IOException e) {
			throw new InternalSeverErrorException(
				"Falha ao realizar a requisição para encontrar a série indicada."
			);
		} catch (InterruptedException e) {
			throw new InternalSeverErrorException(
				"A operação foi interrompida antes que pudesse prosseguir para encontrar a série."
			);
		}
	}

	/**<h1>Converter to Data Transfer Object</h1>
	 * Realiza a conversão da entrada JSON em objeto {@link OMDBSerieDTO}.
	 *
	 * @param json Informação em JavaScript Object Notation que deverá ser convertida para objeto.
	 * @return Objeto convertido de JSON.
	 * @throws InformationProcessingException Quando não for possivel a conversão para o objeto.
	 * */
	private OMDBSerieDTO converterToDTO(String json) {
		try {
			return mapper.readValue(json, OMDBSerieDTO.class);
		}  catch (JsonProcessingException e) {
			throw new InformationProcessingException(
				"A resposta não pode ser processada."
			);
		}
	}
}
