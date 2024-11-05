package br.com.challenge.alura.phrases.random.configs;

import br.com.challenge.alura.phrases.random.configs.env.OpenAIEnv;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

	private final OpenAIEnv env;

	public OpenAIConfig(final OpenAIEnv env) {
		this.env = env;
	}

	@Bean
	public OpenAiService openAiService() {
		return new OpenAiService(env.getApiKey());
	}
}
