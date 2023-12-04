FROM golang:1.21-alpine3.17

WORKDIR /app

COPY go.mod go.sum ./
RUN go mod download && go mod verify

COPY . /app
RUN go build -o app .

CMD ["./app"]