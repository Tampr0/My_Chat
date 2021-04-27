# My_chat

####Ideas to implement
- implement a component, that appears when User has unread messages.
- did deployment more professional - read info from heroku docs.
####Project description

Hi, there. This is a first project where I go a bit deeper into asynchronous issues.

#### What's in it ?
Simple chat UI written with Vaadin library.


####More...
Vaadin allowed me to write frontend entirely in Java.


####LOGS
25 april
I did extra mess inside the project, trying to deploy app on Heroku. 
A extra folders appeared after trying to bulid .jar under production mode on. 
Because I couldn't figure why app is crashing, I started new project, 
just for the tests.
Finally, it worked with git repository sending approach. I needed to set  

24 april.

Started to work.
A bit of challenge is to create a bidirectional, asynchronous way of 
communication. I choose a websocket protocol, with a simple message broker.
In next steps I would definitely implement more advanced brokers.

After yesterday's tests and playing with websocket, figuring out mostly, 
how to pass the payload (message) from a separate thread into CLIENT view, 
I finally started to write User UI
The first view wants a Nick from a User, and it's going to pass it into Chat View.
Nick will be stored in a String field. In time, I would probably need to figure out how to 
pass it with a header to the server.
I need to create a custom component to view the messages specifically.
After Chat view, as simple as that, is prepared, I need to implement some backend.
In the tutorial, reactive approach is used, with Project Reactor library.
I choose to use simple websocket messaging, so now the true adventure begins.
While thinking about some simplier options, these comes into my mind
- move a ChatDiv component into field, so the SEND button has access to it.
Unfortunately I can't pass messages cross the sessions.
The same with a binder and event's approaches. All those options allows me to 
pass messages within one particular session. Maybe a way is to persist messages and then update 
from other session. It's not gonna by effective for sure. 
OK, I'll try to deploy my ChatView on Heroku.... 

Here's plan : create some shell util scripts for deployment.
then deploy.
After logging to Heroku, I created new virtual server, additionally Heroku, by itself, 
bind my local git with its remotes.
I get a nice name for my server : serene-island-00229
Path to it : https://serene-island-00229.herokuapp.com
Path to heroku git: https://git.heroku.com/serene-island-00229.git
Ok, so deploing is on his way... I should definitely build an app before. This might take a while, I'm curious if
any issues occur in case I'm using vaaind.
As I expected , page tells me that application error occured... 
Need to type heroku logs --tail.
I'm gonna check what's up on vaaind page, there must be some tutorial for this...

Lol, it's not gonnna be simple and I'll get no fun.

First of all I created new app within heroku page. I could customize its name. Now tutorial says I need to deploy 
my .jar file, first setting some things up.
I installed java plugin simply by typing : heroku plugins:install java
- check if my .jar is properly build... gradle site, spring guides, heroku guide and vaadin guide to check











Sync

Executing settings.gradl
> Task :prepareKotlinBuildScriptModel UP-TO-DATE

BUILD SUCCESSFUL in 3s

Build Output.
18:39:42: Executing tasks ':classes :testClasses'...

Executing settings.gradle
> Task :compileJava
> Task :vaadinPrepareFrontend
> Task :processResources UP-TO-DATE
> Task :classes
> Task :compileTestJava
> Task :processTestResources NO-SOURCE
> Task :testClasses

BUILD SUCCESSFUL in 6s
4 actionable tasks: 3 executed, 1 up-to-date
18:39:49: Tasks execution finished ':classes :testClasses'.

    PODSUMOWANIE:

    Zmieniając cokolwiek w pliku gradle.build, po przyciśnięciu przycisku Load Gradle Changes
    wywołane zostaje 
> Task ::prepareKotlinBuildScriptModel UP-TO-DATE









 




