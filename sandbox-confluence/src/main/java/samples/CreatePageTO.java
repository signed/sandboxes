package samples;


/*{
  "type": "page",
  "title": "new page",
  "space": {
    "key": "TST"
  },
  "body": {
    "storage": {
      "value": "<p>This is a new page</p>",
      "representation": "storage"
    }
  }
}
*/
public class CreatePageTO {

    public static class Builder {

        private String spaceKey;
        private String title;
        private String content;


        public Builder space(String spaceKey) {
            this.spaceKey = spaceKey;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String storageFormat) {
            this.content = storageFormat;
            return this;
        }

        public CreatePageTO build(){
            CreatePageTO createPage = new CreatePageTO();
            createPage.space = new Space();
            createPage.space.key = spaceKey;
            createPage.title = title;
            createPage.body = new Body();
            createPage.body.storage = new Body.Storage();
            createPage.body.storage.value = content;
            return createPage;
        }

    }

    public static class Space {
        public String key;
    }

    public static class Body {

        public static class Storage {
            public String value;
            public String representation = "storage";
        }

        public Storage storage;
    }

    public String type = "page";
    public String title;
    public Space space;
    public Body body;

}
