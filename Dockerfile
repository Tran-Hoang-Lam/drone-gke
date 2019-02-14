FROM groovy:jdk8-alpine

ARG CLOUD_SDK_VERSION=229.0.0

ENV CLOUD_SDK_VERSION=$CLOUD_SDK_VERSION

ENV PATH /google-cloud-sdk/bin:$PATH

ENV CLOUDSDK_CONTAINER_USE_CLIENT_CERTIFICATE False

#
#--------------------------------------------------------------------------
# Install gcloud sdk
#--------------------------------------------------------------------------
#

USER root

RUN apk --no-cache add \
        curl \
        python \
        py-crcmod \
        bash \
        libc6-compat \
        openssh-client \
        git \
        gnupg \
    && curl -O https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-${CLOUD_SDK_VERSION}-linux-x86_64.tar.gz && \
    tar xzf google-cloud-sdk-${CLOUD_SDK_VERSION}-linux-x86_64.tar.gz -C / && \
    rm google-cloud-sdk-${CLOUD_SDK_VERSION}-linux-x86_64.tar.gz && \
    gcloud config set core/disable_usage_reporting true && \
    gcloud config set component_manager/disable_update_check true && \
    gcloud config set metrics/environment github_docker_image && \
    gcloud config unset container/use_client_certificate && \
    # Basic check it works.
    gcloud --version

#
#--------------------------------------------------------------------------
# Install kubectl
#--------------------------------------------------------------------------
#

RUN gcloud components update kubectl

USER groovy
