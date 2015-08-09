# the spec #

http://beanvalidation.org/1.1/spec/ aka JSR 349: Bean Validation 1.1

# spring documentation #
http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html

# blog posts #
http://www.codesandnotes.be/2014/12/18/validating-spring-rest-controllers-beans-using-the-bean-validation-api-and-writing-the-tests-for-it/

http://blog.trifork.com/2009/08/04/bean-validation-integrating-jsr-303-with-spring/

1. outdated (http://stackoverflow.com/questions/28819299/is-javax-validation-constraintpayload-deprecated-in-hibernate-validator-5-1-3-fi)
1. gives a gentle introduction to Bean Validation JSR 303


# produce custom json format for bean validation errors #
http://springinpractice.com/2013/10/09/generating-json-error-object-responses-with-spring-web-mvc

# Exception Handling #
http://stackoverflow.com/questions/19498378/setting-precedence-of-multiple-controlleradvice-exceptionhandlers

# search terms #
Spring 4 @ControllerAdvice

# use different TO objects for different operations #
http://blabadi.blogspot.de/2014/11/java-validation-standard-jsr-303.html


# write your own validators #
https://docs.jboss.org/hibernate/validator/5.2/reference/en-US/html/chapter-method-constraints.html

http://musingsofaprogrammingaddict.blogspot.de/2009/02/getting-started-with-jsr-303-bean.html


# open points #
## Provide a custom json error format ##
The default json error body does not look that nice

    {
      "timestamp" : 1438203098844,
      "status" : 400,
      "error" : "Bad Request",
      "exception" : "org.springframework.web.bind.MethodArgumentNotValidException",
      "message" : "Validation failed for argument at index 0 in method: public org.springframework.http.ResponseEntity<?> com.github.signed.sandboxes.spring.beanvalidation.ValidatingController.putSpecialValidator(com.github.signed.sandboxes.spring.beanvalidation.AddressBookEntryTO), with 2 error(s): [Field error in object 'addressBookEntryTO' on field 'phoneNumber': rejected value [com.github.signed.sandboxes.spring.beanvalidation.PhoneNumberTO@bf30397]; codes [E164Number.addressBookEntryTO.phoneNumber,E164Number.phoneNumber,E164Number.com.github.signed.sandboxes.spring.beanvalidation.PhoneNumberTO,E164Number]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [addressBookEntryTO.phoneNumber,phoneNumber]; arguments []; default message [phoneNumber]]; default message [{com.github.signed.spring.beanvalidation.E164NumberValidator.message}]] [Field error in object 'addressBookEntryTO' on field 'name': rejected value [null]; codes [NotEmpty.addressBookEntryTO.name,NotEmpty.name,NotEmpty]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [addressBookEntryTO.name,name]; arguments []; default message [name]]; default message [may not be empty]] ",
      "path" : "/specialvalidator"
    }


I have no idea how to hook into the json generation if there are validation errors.
There is a workaround to configure spring to throw a validation exception that can be caught via @ControllerAdvice and than be transformed.
I do not know how to configure this and it feels wrong.
I figured out how to do this. Look at ExceptionsToResponseTranslator


## How to validate optional fields ##

http://stackoverflow.com/questions/28546046/java-bean-validation-optional-fields-annotation

http://stackoverflow.com/questions/27382953/java-bean-validation-enforce-a-pattern-only-when-the-property-is-not-blank

Treat null as valid seems to be the documented way to handle this
http://stackoverflow.com/questions/30426077/spring-bean-validation-handle-optional-field-validation

## Validating enumerations ##
From reading stackoverflow there is no out of the box validator for enumerations.
I have not tried to validate enumerations until now.

## Deep nested TOs need a getter to be properly validated ##

    "JSR-303 validated property 'phoneNumber' does not have a corresponding accessor for Spring data binding - check your DataBinder's configuration (bean property versus direct field access)",

The error message indicates that this can be configured but I do not know where
