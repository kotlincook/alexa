## Programming Alexa with Kotlin

## Build
gradle shadowJar

## AWS
* Goto https://developer.amazon.com/edw/home.html#/ and select "Alexa Skill Kit"
* Create Skill -> KotlinBlog
* Custom, follow the instructions...
* Take the Skill-ID: amzn1.ask.skill.50b9f4ef-1c02-4e72-bd4c-1bbdf1df61f5 and put it into the code
* Take the Intent name and put it into the code
* EndPoint: AWS Lambda ARN: (s. "Take the ARN")
* AWS-Lamda: Create function:
  * Name: KotlinBlog
  * Runtime: Java 8
  * Role: existing role
  * kotlincookrole
* Trigger: Alexa Skills Kit (Configuratioin required)
  * enter skill Id (see above) at the bottom
  * Click on KotlinBlog an upload Function code
  * Change "example.Hello::myHandler" to "de.kotlincook.KotlinSpeechletRequestHandler"
  * Upload kotlinblog-1.0-SNAPSHOT-all.jar
* Take the ARN:  arn:aws:lambda:us-east-1:911903515210:function:KotlinBlog
  * and put it into the field "EndPoint" (s.o.)

## Testing
FirstTest -> test.json

## Troubleshoting
kotlin/reflect/KotlinReflectionInternalError: java.lang.NoClassDefFoundError
java.lang.NoClassDefFoundError: kotlin/reflect/KotlinReflectionInternalError
* Do not use 'compile "org.jetbrains.kotlin:kotlin-reflect:1.2.30"' even if is it
logged to be recommended in the gradle output
* Or use "com.fasterxml.jackson.module:jackson-module-kotlin:2.9.2"
