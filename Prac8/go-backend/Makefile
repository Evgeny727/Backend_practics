swagger:
	swag init -o .\api\swagger\

run:
	go run . http --config=./config/app.conf.yaml

build-dev:
	docker-compose -f .\build\docker-compose.yaml  -f .\build\docker-compose-override.yaml up -d
