# drone-gke
[![Build Status](https://drone.lamth.info/api/badges/Tran-Hoang-Lam/drone-gke/status.svg)](https://drone.lamth.info/Tran-Hoang-Lam/drone-gke)

Drone plugin to deploy k8s application to gke cluster

docker run --rm -e PLUGIN_CLUSTER=< cluster name > -e PLUGIN_ZONE=< zone > -e PLUGIN_TEMPLATE=< path to deployment template > -e PLUGIN_TAG=< image tag > -e PLUGIN_GCE_TOKEN=< service account json encoded base 64 > drone-gke
