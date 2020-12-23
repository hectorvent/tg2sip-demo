Tg2SIP DEMO
===========

![console-backend](https://github.com/hectorvent/tg2sip-demo/workflows/Build%20Tg2SIP%20Demo%20WebConsole/badge.svg)

> Note: This demo from the moment it is finished will be hosted and in this way you can test it.

> Note: This demo is not yet completed. For now, you just can review the code.

It is just a demo to show what you can do with [Tg2SIP Gateway](https://github.com/hectorvent/tg2sip) and other cool technologies.

## Things that you can do

- Call and listen a music and hold
- See call logs
- Change media (Music/Music on Hold)

## Used Technologies

* **Tg2SIP**: Telegram to SIP gateway
* **Freeswitch**: VoIP Switch Platform
* **Postgresql**: Database server to store call logs and the configuration.
* **Lua**: Used internally on Freeswitch to manage the dialplan. I will create in the near future a python version.
* **Ansible**: A Red Hat tool to make deployment and provisioning.
* **Quarkus**: A Kubernetes Native Java stack.
* **Javascript**: Vanilla JavaScript, sorry.
* **Debian**: Linux Server on all this magic happend
* **Telegram Login**: way to login into the demo is using this service provide by telegram
* **Docker**: As alternative way to deploy all services.

## TODO:

* Join into a call conference
* Call forward
