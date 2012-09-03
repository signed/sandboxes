package localvariabletofield;

public class ChangesBehaviour {

    private final DomainModel originalModel;
    private final DomainModel model;


    public ChangesBehaviour(DomainModel model, DomainModel filteredDomainModel) {
        this.originalModel = model;
        this.model = filteredDomainModel;
    }

    public void initPresentation() {
        AnotherDomainModel filterModel = new AnotherDomainModel(model);
    }
}
