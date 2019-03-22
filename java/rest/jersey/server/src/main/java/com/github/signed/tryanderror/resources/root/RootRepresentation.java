package com.github.signed.tryanderror.resources.root;

import com.github.signed.tryanderror.resources.another.KnowAtCompileTime;
import com.sun.jersey.api.view.ImplicitProduces;
import com.sun.jersey.server.linking.Link;
import com.sun.jersey.server.linking.Links;
import com.sun.jersey.server.linking.Ref;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

@ImplicitProduces("text/html;qs=5")
@Links(value = {
        @Link(
                value = @Ref(resource = RootResource.class, style = Ref.Style.ABSOLUTE),
                rel = "self"
        ),
        @Link(
                value = @Ref(resource = KnowAtCompileTime.class, style = Ref.Style.ABSOLUTE),
                rel = "known"
        )
}
)

@XmlRootElement
public class RootRepresentation {
    @XmlElement
    @Ref(resource = RootResource.class, style = Ref.Style.ABSOLUTE)
    URI self;

    @XmlElement
    @Ref(resource = KnowAtCompileTime.class, style = Ref.Style.ABSOLUTE)
    URI known;
    
    public String getSelf() {
        return self.toASCIIString();
    }
    
    public String getKnown() {
        return self.toASCIIString();
    }
}