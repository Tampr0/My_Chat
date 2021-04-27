# My_chat

####Ideas to implement
- implement a component, that appears when User has unread messages.
- did deployment more professional - read info from heroku docs.
####Project description

Hi, there. This is a first project where I go a bit deeper into asynchronous issues.

#### What's in it ?
Simple chat UI written with Vaadin library.


####More...
Vaadin allowed me to write the frontend entirely in Java.


####LOGS
After struggling with deployment on Heroku I'm much smarter. 
What I needed to do was simply clean the whole project using `./gradlew clean` and `./gradlew vaadinClean`,
and pass clean project clean into Heroku's git. 
Also, productionMode needed to be set true.














 




