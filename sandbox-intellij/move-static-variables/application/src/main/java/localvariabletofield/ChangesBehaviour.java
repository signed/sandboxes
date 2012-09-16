package localvariabletofield;

public class ChangesBehaviour {

    private final DomainModel model;

    public ChangesBehaviour(DomainModel model, DomainModel filteredModel) {
        this.model = filteredModel;
    }

    public void initPresentation() {
        AnotherDomainModel derivedDomainModel = new AnotherDomainModel(model);
    }

    public static class DomainModel {
    }

    public static class AnotherDomainModel {
        public AnotherDomainModel(DomainModel model) {
            //To change body of created methods use File | Settings | File Templates.
        }
    }
}
