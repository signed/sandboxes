package htmlunit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HtmlUnit_Test {
    private final WebClient webClient = new WebClient();
    private HtmlPage page;

    @Before
    public void navigateToHtmlUnitHompage() throws Exception {
        page = webClient.getPage("http://htmlunit.sourceforge.net");
    }

    @After
    public void tearDown() throws Exception {
        webClient.closeAllWindows();
    }

    @Test
    public void rightPageWasOpend() throws Exception {
        assertThat(page.getTitleText(), is("HtmlUnit - Welcome to HtmlUnit"));
        assertThat(page.asXml(), containsString(("<body class=\"composite\">")));
        assertThat(page.asText(), containsString("Support for the HTTP and HTTPS protocols"));
    }

    @Test
    public void navigateToDownloadPage() throws Exception {
        HtmlAnchor anchor = page.getAnchorByText("Download");
        HtmlPage target = anchor.click();

        assertThat(target.getTitleText(), is("HtmlUnit - Browse /htmlunit at SourceForge.net"));
    }
}
