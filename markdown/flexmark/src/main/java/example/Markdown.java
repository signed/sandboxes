package example;

import com.google.common.collect.Sets;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.LinkRef;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeVisitor;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.Formatter;
import com.vladsch.flexmark.formatter.internal.MarkdownWriter;
import com.vladsch.flexmark.formatter.internal.NodeFormatter;
import com.vladsch.flexmark.formatter.internal.NodeFormatterContext;
import com.vladsch.flexmark.formatter.internal.NodeFormatterFactory;
import com.vladsch.flexmark.formatter.internal.NodeFormattingHandler;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Markdown {

	private final DataHolder OPTIONS = new MutableDataSet();
	private final MutableDataSet FORMAT_OPTIONS = new MutableDataSet();
	private final MutableDataSet options = new MutableDataSet();

	public final Parser parser;
	public final HtmlRenderer html;

	public Markdown(){
		options.set(Parser.BLANK_LINES_IN_AST, Boolean.TRUE);
		FORMAT_OPTIONS.set(Parser.EXTENSIONS, OPTIONS.get(Parser.EXTENSIONS));
		parser = Parser.builder(options).build();
		html = HtmlRenderer.builder(options).build();
	}

	public List<Link> allLinks(Node node){
		List<Link> links = new ArrayList<>();
		NodeVisitor nodeVisitor = new NodeVisitor(new VisitHandler<>(Link.class, links::add));
		nodeVisitor.visit(node);
		return links;
	}

	public void allLinkReferences(){
		VisitHandler<LinkRef> linkReferences = new VisitHandler<>(LinkRef.class, new Visitor<LinkRef>() {
			@Override
			public void visit(LinkRef node) {
				BasedSequence text = node.getText();
				System.out.println(node);
			}
		});
	}

	public void allReferences(Node node){
		VisitHandler<Reference> reference = new VisitHandler<>(Reference.class, new Visitor<Reference>() {
			@Override
			public void visit(Reference node) {
				System.out.println(node);
			}
		});

	}




	public Formatter formatter(){
		return Formatter.builder(FORMAT_OPTIONS).nodeFormatterFactory(new NodeFormatterFactory() {
			@Override
			public NodeFormatter create(DataHolder options) {
				return new NodeFormatter() {
					@Override
					public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
						return Sets.newHashSet(new NodeFormattingHandler<>(Link.class, new CustomNodeFormatter<Link>() {
							@Override
							public void render(Link node, NodeFormatterContext context, MarkdownWriter markdown) {
								String linkText = node.getText().toString();
								String url = node.getUrl().toString();
								markdown.append(String.format("[%s][%s]", linkText, url));
							}
						}));
					}

					@Override
					public Set<Class<?>> getNodeClasses() {
						return Sets.newHashSet(Link.class);
					}
				};
			}
		}).build();
	}

}
