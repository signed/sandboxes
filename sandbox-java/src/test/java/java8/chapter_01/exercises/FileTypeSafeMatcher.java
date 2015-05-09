package java8.chapter_01.exercises;

import java.io.File;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

class FileTypeSafeMatcher extends TypeSafeMatcher<File> {

    public static FileTypeSafeMatcher fileNamed(String name) {
        return new FileTypeSafeMatcher(Matchers.equalTo(name));
    }

    private final Matcher<String> name;

    private FileTypeSafeMatcher(Matcher<String> name) {
        this.name = name;
    }

    @Override
    protected boolean matchesSafely(File item) {
        return name.matches(item.getName());
    }

    @Override
    public void describeTo(Description description) {
        name.describeTo(description);
    }

    @Override
    protected void describeMismatchSafely(File item, Description mismatchDescription) {
        mismatchDescription.appendText("file name ");
        name.describeMismatch(item.getName(), mismatchDescription);
    }
}
