SwaggerTrim:
- OperationMother to ensure all the necessary fields are set for validation

Validate the inputs generated for test
- goal is to avoid unnecessary null and empty checks
  - right now I do it manually with swagger-ui, not ideal.
- the java project only performs schema validation. Better than nothing?

- there is a java script tool out there that performs some semantic checks on swagger files 
  https://github.com/apigee-127/swagger-tools/blob/master/docs/Swagger_Validation.md
  
  
# interesting reads #
- json ref   http://azimi.me/2015/07/16/split-swagger-into-smaller-files.html