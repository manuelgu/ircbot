# ircbot
IRC Bot running on the [PircBotX Framework](https://github.com/TheLQ/pircbotx)

## General

This is the IRC bot running in `#manuelgu` on `irc.esper.net`.
It is not meant to provide super complex command handling with arguments but rather have a command that prints feedback.
Usage examples would be having shortcuts for important links that people recently joined might not know yet.

## Building

1. Clone the repository
2. Rename `example.credentials.properties` to `credentials.properties` and fill out like the following:

  ```
  username=IRCUsername
  pass=NickServPassword
  channel=#yourchannel
  version=VersionName
  ```
  
3. Build the plugin with maven, `mvn clean install`
4. Run the jar file

## How to contribute

I'm happy to see contributions to this project, however this project is mostly for private use.
