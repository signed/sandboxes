package strip.copyright;

import com.github.javaparser.Range;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.util.ArrayList;
import java.util.List;

public class CopyrightNoticeScanner extends ModifierVisitor<Void> {
    public final List<Range> copyrightNoticesLocations = new ArrayList<>();

    @Override
    public Node visit(BlockComment n, Void arg) {
        if (n.getContent().toLowerCase().contains("Copyright".toLowerCase())) {
            copyrightNoticesLocations.add(n.getRange().orElseThrow());
            return null;
        }
        return n;
    }
}
