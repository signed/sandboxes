package localvariabletofield;

public class PassesWrongModelAfterConvertingToField {

    private final DomainModel model;
    private final AnotherDomainModel derivedDomainModel;

    public PassesWrongModelAfterConvertingToField(DomainModel model, DomainModel filteredModel) {
        this.model = filteredModel;
        derivedDomainModel = new AnotherDomainModel(model);
    }

    public void initPresentation() {
    }

    public static class DomainModel {
    }

    public static class AnotherDomainModel {
        public AnotherDomainModel(DomainModel model) {
            //To change body of created methods use File | Settings | File Templates.
        }
    }
}
