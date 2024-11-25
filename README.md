# blue
---
The app for storing / creating / maitnaining Markdown notes.

**WARNING! Application in development, no stable version was created**
---
## App components
The app consits two main compontents:
1. server ( blue_server ) - a web app for syncing and maintaining notes in the web
2. application ( blue_app ) - a flutter desktop app for maintaining app locally and online - the app is independent, the server is only an addon for cloud functionality and syncing.
---
A web application that allows you to share notes/text clippings with one URL. Accepts markdown formatting only. Giving you the option to protect your notes with a password, enable them to be turned off, or create an account and keep track of your content. Notes can also have a public link that can generate something in the form of a user's blog.

The user can create his own page with notes to share - he can make the note public and thus it will be available to everyone after entering the url /users/<user_id>
