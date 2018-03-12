package example;

public class CloneConfiguration {
	public final String user;
	public final String repositoryName;
	private boolean hasWiki;


	public CloneConfiguration(String user, String repositoryName, boolean hasWiki) {
		this.user = user;
		this.repositoryName = repositoryName;
		this.hasWiki = hasWiki;
	}

	public String cloneUrl() {
		return github() + ".git";
	}

	public String localFolder() {
		return userHome() + "/dev/github/" + user + "/" + repositoryName;
	}

	public boolean hasWiki(){
		return hasWiki;
	}

	public String wikiCloneUrl() {
		return github() + ".wiki.git";
	}

	public String wikiLocalFolder() {
		return localFolder() + ".wiki";
	}

	private String github() {
		return "https://github.com/" + user + "/" + repositoryName;
	}

	public String userHome() {
		return System.getProperties().getProperty("user.home");
	}
}
