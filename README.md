Tg2SIP DEMO
===========

> Note: This demo is not yet compleated. For now you just can review code.

It's just a demo to show what you can do with Tg2SIP, and what can a `Software Engineer` that love `VoIP` do.

# What you can do

- Call and listen default music or login and reproduce other on list
- Join into a call conference
- See call logs
- Change music

## Technologies used

- Tg2SIP, Telegram gateway to SIP
- Freeswitch
- Postgres, to hold CDR and Apps configuration
- Lua, I just try use Python but Python 3 have some problems on freeswitch
- Ansible, Make deployment and configuration
- Quarkus, backend application
- ReactJS, frontend app
- Debian 10, Linux Server on all this magic happend
- Telegram, login integration