FROM ghcr.io/graalvm/graalvm-community:21
	
WORKDIR /app

COPY ./target ./

CMD ["./graalvm-example"]
