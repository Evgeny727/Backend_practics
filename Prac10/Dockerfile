FROM golang:1.21

WORKDIR /go/src/app

COPY . .

RUN go build -o main ./server
RUN go build -o main ./client

EXPOSE 4040

CMD ["./main"]
