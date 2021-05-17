# My Chat 
In progress ...

Upcoming...
- implement database for logs storing.
- separate layout which shows all users that are online on the chat at the moment.
- error handling - heroku error - code = H15, desc= Idle connection
- bug fix - Safari - when messageView is overwhelmed, input TextFields changes size or even disappears, same with sendButton.
- link handling (make them openable by clicking)

### Project description
Hi, there. This is a first project where I go a bit deeper into asynchronous issues.

### What's in it ?
Simple chat UI implement with Vaadin library.

### LOGS
17.05.2021
- expand project with new classes.
- some cleans up
- handle removing nick after user log out - ADDED

6.05.2021
- UIDetachedException HANDLED - needed to handle disposing chat messages subscription.
- logout button added
- messages which were already sent were not displayed on messageView after refreshing - FIXED.

4.05.2021
- deprecating UnicastProcessor implementation changed to Sinks.

2.05.2021
- some styling - new messages are displayed at the bottom of the view.
- application stores user nick temporarily. 
- many tabs/windows opened within one session handling - to prevent from chatting as a multi-tab user.
- SignedInBroadcast - after user signed in, all opened windows/tabs within same session are updating asynchronously, by registered broadcast listeners.

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













 




