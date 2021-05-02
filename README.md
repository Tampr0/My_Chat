# My Chat 
In progress ...

Upcoming...
- after refreshing, already send messages should be displayed on the view.
- handle logout / change nick.
- handle UnicastProcessor deprecating.

### Project description
Hi, there. This is a first project where I go a bit deeper into asynchronous issues.

### What's in it ?
Simple chat UI implement with Vaadin library.

### LOGS
2.05.2021
- some styling - new messages are displayed at the bottom of the view.
- SignedInBroadcast - after user signed in, all opened windows/tabs within same session are updating asynchronously, by registered broadcast listeners.
- many tabs/windows opened within one session handling - to prevent from chatting as a multi-tab user.

earlier ...
- added simple validation of user name. 

- Deploying on Heroku.

updated: After all, all I needed was to exclude build-files from putting them into Git.

After struggling with deployment on Heroku I'm much smarter. 
What I needed to do was simply clean the whole project using `./gradlew clean` and `./gradlew vaadinClean`,
and pass clean project clean into Heroku's git. 
Also, productionMode needed to be set true.

### Ideas to implement
- implement a component, that appears when User has unread messages.
- (deployment) Done deployment more professional - read info from Heroku docs.
- when implement a websocket-approach with outer server, figure a simple request
that is send before user can start using a chat and after receiving respond, enables chat view. 













 




