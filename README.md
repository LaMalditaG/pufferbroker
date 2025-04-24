A broker for [Impulse](https://github.com/Arson-Club/Impulse) to interact with [pufferpanel](https://www.pufferpanel.com/) using the api

# Installation

Download the latest release from the releases page, and put the jar in the plugins/impulse directory. It should be the same directory as the config. Then change the config to allow the broker to stop and start your servers.

# Config

This config example include everything needed to use the broker.

```yaml
servers:
  name: "lobby"
  type: "pufferpanel"
  pufferpanel:
    serverID: "qwertyui"
    clientID: "qwertyuioasdfghjklzxcvbn"
    clientSecret: "qwertyuiopasdfghjklzxcvbnmqwert"
    pufferpanelAddress: "http://localhost:8080"
```

To get the server ID, you can check the url of the server you want to automate. You can also find it inside the **Files** section of the server in PufferPanel and check in the STFP information the number after your e-mail.

To get your clientID and secret, go to the **Account** section of PufferPanel and create a new oauth2 client, give any name you want, and copy the client ID and secret from that window, remember the secret will only show once. Not to confuse it with the oauth2 in the **Admin** section.

The pufferpanelAddress is the address of the pufferPanel api. Have in mind that if you are using pufferPanel inside a docker container, you have to use the port that is used inside the container, which is 8080 by default, even if you access the panel from a different port in your browser.

# Notes

This project was forked from [this](https://github.com/Thebestandgreatest/craftybroker) other broker for CraftyController.
