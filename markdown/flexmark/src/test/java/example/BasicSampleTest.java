package example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class BasicSampleTest {

	public static class LinkWithAlternative {
		public final String current = "https://github.com/realestate-com-au/pact";
		public final String alternative = "https://github.com/pact-foundation/pact-ruby";
	}

	@Test
	void name() throws IOException {
		LinkWithAlternative linkWithAlternative = new LinkWithAlternative();


		Document document = Jsoup.connect("https://github.com/realestate-com-au/pact").get();

		boolean noLongerExists = redirectsToOldRubRepository(document);
		System.out.println(document);
	}

	private boolean redirectsToOldRubRepository(Document document) {
		return document.toString().contains("The Ruby Pact implementation has now moved to be with the rest of its kin in the");
	}
}