package br.com.challenge.alura.phrases.random.services;

import br.com.challenge.alura.phrases.random.exceptions.InformationProcessingException;
import br.com.challenge.alura.phrases.random.models.entities.Frase;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatGPTService {

	private final OpenAiService service;

	public ChatGPTService(final OpenAiService service) {
		this.service = service;
	}

	/**<h1>Generate Phrase by Person</h1>
	 * Gera uma frase do Personagem indicado na entidade usando a API da OpenAI.
	 *
	 * @param frase Entidade utilizada para requisição com Personagem e Titulo da série que é requisitada a frase.
	 * @throws InformationProcessingException Quando não conseguir realizar a operação de geração da frase pela API da
	 * OpenAI.
	 * @return Mensagem da frase gerada pela API da OpenAI.
	 * @since Tuesday, 5th November 2024
	 * */
	public String generatePhraseByPerson(Frase frase) {
		final var promptReq = "Me mostre uma frase dita pelo(a) personagem %s da série %s, trazendo apenas a frase em protuguês BR sem precisar explicar seu significado."
				.formatted(frase.personagem(), frase.titulo());

		try {
			return generateByOpenAI(promptReq).replace("\n", "");

		} catch (Exception e) {
			throw new InformationProcessingException(
				"Não foi gerar uma frase dita pelo(a) personagem %s".formatted(frase.personagem())
			);
		}
	}

	/**<h1>Generate Persons by Series Title</h1>
	 * Lista os personagens de uma série especificada pelo seu titulo, gerada pela API da OpenAI.
	 *
	 * @param title Titulo da série que será gerada a lista de personagens.
	 * @throws InformationProcessingException Quando não for possivel gerar a lista de personagens da série
	 * especificada.
	 * @return Lista de personagens da série especificada.
	 * @since Tuesday, 5th November 2024
	 * */
	public List<String> generatePersonsBySeriesTitle(String title, Integer limit) {
		final var prompt = "Liste os nomes completos dos %d principais personagens da série %s separados por virgula (,) em uma única linha"
				.formatted(limit, title);
		try {
			final String generated = generateByOpenAI(prompt);
			return Arrays.stream(generated.replace("\n", "").split(", ")).collect(Collectors.toList());

		} catch (Exception e) {
			throw new InformationProcessingException(
				"Não foi possivel gerar os personagens da série %s.".formatted(title)
			);
		}
	}

	/**<h1>Generate by OpenAI</h1>
	 * Gera uma resposta usando o prompt informado para o serviço da OpenAI em {@link OpenAiService}.
	 *
	 * @param prompt Entrada do prompt para a geração da resposta pela API da OpenAI.
	 * @return Resposta da geração pelo prompt informado.
	 * */
	protected String generateByOpenAI(String prompt) throws Exception {
		CompletionRequest request = CompletionRequest.builder()
				.model("gpt-3.5-turbo-instruct")
				.prompt(prompt)
				.maxTokens(1000)
				.temperature(0.7)
				.build();
		CompletionResult response = service.createCompletion(request);
		return response.getChoices().get(0).getText();
	}
}
