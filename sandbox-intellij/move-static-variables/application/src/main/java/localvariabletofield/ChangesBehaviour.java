package localvariabletofield;

public class ChangesBehaviour {

    private final DomainModel model;

    public ChangesBehaviour(DomainModel model, DomainModel filteredModel) {
        this.model = filteredModel;
    }

    public void initPresentation() {
        AnotherDomainModel derivedDomainModel = new AnotherDomainModel(model);
    }
}
