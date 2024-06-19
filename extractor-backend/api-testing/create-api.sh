# http -v GET \
#   https://telraam-api.net/v1/reports/traffic_snapshot_live \
#   X-Api-Key:uXC0McGqw53qhsCknDPaY4cZ3bkoOApp33fCIS6l

http -v POST localhost:8091/apis/create < ./create-api-command.json
