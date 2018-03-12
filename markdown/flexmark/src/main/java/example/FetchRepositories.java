package example;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


public class FetchRepositories {

	public static void main(String[] args) throws IOException {
		GitHub gitHub = GitHub.connectAnonymously();
		String organization = "pact-foundation";
		Map<String, GHRepository> repositories = gitHub.getOrganization(organization).getRepositories();
		repositories.values().stream()
				.map(repository -> new CloneConfiguration(organization, repository.getName(), repository.hasWiki()))
				.forEach(FetchRepositories::cloneRepository);
	}

	private static void cloneRepository(CloneConfiguration configuration) {
		try {
			cloneOrUpdate(Paths.get(configuration.localFolder()), configuration.cloneUrl());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {
			if (configuration.hasWiki()) {
				cloneOrUpdate(Paths.get(configuration.wikiLocalFolder()), configuration.wikiCloneUrl());
			}
		} catch (org.eclipse.jgit.api.errors.TransportException ex) {
			//ignore this one
		} catch (IOException | GitAPIException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static void cloneOrUpdate(Path localRepositoryPath, String cloneUrl) throws IOException, GitAPIException {
		StringBuilder message = new StringBuilder();
		message.append(cloneUrl + " -> " + localRepositoryPath);
		if (Files.isDirectory(localRepositoryPath)) {
			message.append(" update");
			System.out.println(message);
			Git localRepository = Git.open(localRepositoryPath.toFile());
			localRepository.pull();
		} else {
			message.append(" clone");
			System.out.println(message);
			Git.cloneRepository()
					.setURI(cloneUrl)
					.setDirectory(localRepositoryPath.toFile())
					.call();
		}
	}
}
