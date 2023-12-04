package fileservice

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/gridfs"
	"mongo-db-files-service/internal/config"
	"mongo-db-files-service/internal/domain"
	http2 "mongo-db-files-service/internal/handler/http"
	"mongo-db-files-service/internal/infrastructure"
	"mongo-db-files-service/pkg/logger"
	"net/http"
	"os"
)

func init() {
	// Загрузите конфигурацию из переменных окружения или файла конфигурации.
	cfg, err := config.Load()
	if err != nil {
		fmt.Printf("Failed to load configuration: %v\n", err)
		return
	}

	// Создайте экземпляр логгера.
	appLogger := logger.NewLogger()

	// Инициализируйте соединение с MongoDB.
	mongoClient, err := infrastructure.NewMongoClient(cfg.MongoDBURL)
	if err != nil {
		appLogger.Error("Failed to connect to MongoDB:", err)
		os.Exit(1)
	}
	defer func(mongoClient *mongo.Client, ctx context.Context) {
		err := mongoClient.Disconnect(ctx)
		if err != nil {

		}
	}(mongoClient, nil) // Закрытие соединения

	// Создайте GridFS Bucket для MongoDB.
	gridFSBucket, err := gridfs.NewBucket(
		mongoClient.Database(cfg.MongoDBDatabase),
	)
	if err != nil {
		appLogger.Error("Failed to create GridFS Bucket:", err)
		os.Exit(1)
	}

	// Создайте экземпляр MongoDBRepository с GridFS Bucket.
	fileRepository := infrastructure.NewMongoDBRepositoryWithGridFS(gridFSBucket)

	// Создайте экземпляр FileService, внедрив FileRepository и логгер.
	fileService := domain.NewFileService(fileRepository)

	// Создайте экземпляр Handlers, внедрив FileService и логгер.
	handlers := http2.NewHandlers(fileService, appLogger)

	// Настройте маршруты для HTTP-сервера.
	router := http2.ConfigureRouter(handlers)

	// Настройте HTTP-сервер с использованием настроенного маршрутизатора и порта из конфигурации.
	serverAddr := fmt.Sprintf("%s:%d", cfg.Host, cfg.Port)

	appLogger.Info("Starting the server on " + serverAddr)

	if err := http.ListenAndServe(serverAddr, router); err != nil {
		appLogger.Error("Failed to start the server:", err)
		os.Exit(1)
	}
}
