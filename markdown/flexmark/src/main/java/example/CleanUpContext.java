package example;

import com.vladsch.flexmark.ast.Document;

import java.nio.file.Path;

public class CleanUpContext {
	public final Path path;
	public final Document document;

	public CleanUpContext(Path path, Document document) {
		this.path = path;
		this.document = document;
	}
}
