package com.github.signed.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Gegebensei;
import cucumber.api.java.de.Wenn;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MondSteps {

    @Gegebensei("^der Mond$")
    @Given("^the moon$")
    public void der_Mond() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Wenn("^der Mond aufgeht$")
    @When("^the moon is rising$")
    public void der_Mond_aufgeht() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Dann("^dann ist der Mann im Mond bei der Arbeit$")
    @Then("^the man in the moon is at work$")
    public void dann_ist_der_Mann_im_Mond_bei_der_Arbeit() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }
}
