# Dropwizard authentication, authorization, and multitenancy example
Sample code for my blog post: [part 1](), [part 2](), [part 3]()

## This is the code for only Part 1 and 2 of the post

# Running the app

 1. Run `mvn package`
 1. Create and migrate the DB `java -jar common/target/common-1.0.jar db migrate common/config.yml`
 1. Run the auth API `java -jar authentication-api/target/authentication-api-1.0.jar server authentication-api/config.yml`
 1. Run the main app `java -jar business-api/target/business-api-1.0.jar server business-api/config.yml`
 1. Go to [http://localhost:8888](http://localhost:8888)
