# drone-gke
[![Build Status](https://drone.lamth.info/api/badges/Tran-Hoang-Lam/drone-gke/status.svg)](https://drone.lamth.info/Tran-Hoang-Lam/drone-gke)

Drone plugin to deploy k8s application to gke cluster

docker run --rm -e PLUGIN_CLUSTER=cluster_name -e PLUGIN_ZONE=zone -e PLUGIN_TEMPLATE=path_to_deployment_template -e PLUGIN_TAG=image_tag -e PLUGIN_GCE_TOKEN=service_account_json_base64_encoded drone-gke
