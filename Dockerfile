FROM ubuntu:latest
LABEL authors="mmtor"

ENTRYPOINT ["top", "-b"]