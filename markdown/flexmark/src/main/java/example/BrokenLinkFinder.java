package example;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BrokenLinkFinder {

	public static void main(String[] args) throws IOException {
		String pathToRepositories = args[0];
		System.out.println(pathToRepositories);

		Markdown markdown = new Markdown();
		Stream<Path> pathStream = Files.find(Paths.get(pathToRepositories), 200, new BiPredicate<Path, BasicFileAttributes>() {
			@Override
			public boolean test(Path path, BasicFileAttributes basicFileAttributes) {
				return basicFileAttributes.isRegularFile() && path.toString().endsWith(".md");
			}
		});

		pathStream.map(path -> {
			String markdownString = readUtf8File(path);
			Document document = markdown.parser.parse(markdownString);
			return new CleanUpContext(path, document);
		}).forEach((CleanUpContext cleanUpContext) -> {
			List<Link> candidatesForBrokenLinks = potentialBrokenLinkReference(markdown, cleanUpContext);


			List<Link> linksToOldPactRepository = fullLinks(markdown, cleanUpContext);

			if (candidatesForBrokenLinks.isEmpty() && linksToOldPactRepository.isEmpty()) {
				return;
			}

			System.out.println();
			System.out.println(cleanUpContext.path);
			candidatesForBrokenLinks.forEach(link -> {
				System.out.println(link);
			});

			linksToOldPactRepository.forEach(link -> {
				String brokenLink = link.getUrl().toString();
				String fixedLink = brokenLink.replace("https://github.com/realestate-com-au/pact", "https://github.com/pact-foundation/pact-ruby");
				System.out.println(link.getText());
				System.out.println(brokenLink);
				System.out.println(fixedLink);


			});
		});

		String markdownString = "[top](ref)\n[buh][ref]\n\n[ref]: catch";
	}

	private static List<Link> potentialBrokenLinkReference(Markdown markdown, CleanUpContext cleanUpContext) {
		List<Reference> references = markdown.allReferences(cleanUpContext.document);
		List<BasedSequence> referenceKeys = references.stream().map(reference -> reference.getReference()).collect(Collectors.toList());
		List<Link> links = markdown.allLinks(cleanUpContext.document);
		return links.stream().filter(link -> referenceKeys.contains(link.getUrl())).collect(Collectors.toList());
	}

	private static List<Link> fullLinks(Markdown markdown, CleanUpContext cleanUpContext) {
		List<Link> links = markdown.allLinks(cleanUpContext.document);
		List<Link> brokenLinks = links.stream()
				.filter(link -> link.getUrl().toString().toLowerCase().contains("realestate-com-au/pact"))
				.collect(Collectors.toList());
		return brokenLinks;
	}

	private static String readUtf8File(Path path) {

		try {
			byte[] bytes = Files.readAllBytes(path);
			return new String(bytes, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
