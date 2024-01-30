FROM ghcr.io/graalvm/graalvm-community:21
	
WORKDIR /app

ENV LANG pt_BR.UTF-8
ENV LANGUAGE pt_BR:pt:en
ENV LC_ALL pt_BR.UTF-8

COPY ./target/graalvm-connector ./app

CMD ["./app"]
