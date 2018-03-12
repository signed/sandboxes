package example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CloneConfigurationTest {

	private final CloneConfiguration configuration = new CloneConfiguration("user", "repository", true);

	@Test
	void repositoryUrl() {
		assertThat(configuration.cloneUrl()).isEqualTo("https://github.com/user/repository.git");
	}

	@Test
	void localFolder() {
		assertThat(configuration.localFolder()).isEqualTo(configuration.userHome() + "/dev/github/user/repository");
	}

	@Test
	void wikiRepositoryUrl() {
		assertThat(configuration.wikiCloneUrl()).isEqualTo("https://github.com/user/repository.wiki.git");
	}

	@Test
	void wikiLocalFolder() {
		assertThat(configuration.wikiLocalFolder()).isEqualTo(configuration.userHome() + "/dev/github/user/repository.wiki");
	}

}