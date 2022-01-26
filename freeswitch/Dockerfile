FROM debian:buster-slim

RUN apt-get update && apt-get install -y gnupg2 wget lsb-release
RUN wget -O - https://files.freeswitch.org/repo/deb/debian-release/fsstretch-archive-keyring.asc | apt-key add -
RUN echo "deb http://files.freeswitch.org/repo/deb/debian-release/ `lsb_release -sc` main" > /etc/apt/sources.list.d/freeswitch.list
RUN echo "deb-src http://files.freeswitch.org/repo/deb/debian-release/ `lsb_release -sc` main" >> /etc/apt/sources.list.d/freeswitch.list
RUN apt-get update && apt install -y freeswitch freeswitch-mod-lua freeswitch-lang-en freeswitch-mod-curl freeswitch-mod-cdr-csv freeswitch-mod-console freeswitch-sounds-en-us-callie freeswitch-mod-json-cdr
RUN rm -rf /etc/freeswitch/*
RUN apt-get autoremove -y
RUN rm -rf /var/lib/apt/lists/*

# apt-get clean autoclean && \
# apt-get autoremove --yes && \
# rm -rf /var/lib/{apt,dpkg,cache,log}/

COPY etc/ /etc/freeswitch/
COPY sounds/ /usr/share/freeswitch/sounds/


CMD ["/usr/bin/freeswitch", "-nonat", "-nf", "-c"]

