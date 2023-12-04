package infrastructure

import (
	"context"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/gridfs"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
	"mongo-db-files-service/internal/domain"
)

// MongoDBRepository представляет реализацию FileRepository для MongoDB.
type MongoDBRepository struct {
	GridFSBucket *gridfs.Bucket
}

// NewMongoDBRepositoryWithGridFS создает новый экземпляр MongoDBRepository с переданным GridFS.
func NewMongoDBRepositoryWithGridFS(bucket *gridfs.Bucket) *MongoDBRepository {
	return &MongoDBRepository{GridFSBucket: bucket}
}

// NewMongoClient создает и возвращает клиент MongoDB.
func NewMongoClient(connectionString string) (*mongo.Client, error) {
	// Создайте настройки для клиента MongoDB на основе вашей строки подключения.
	clientOptions := options.Client().ApplyURI(connectionString)

	// Создайте клиент MongoDB с настройками.
	client, err := mongo.Connect(context.Background(), clientOptions)
	if err != nil {
		return nil, err
	}

	// Проверьте, что клиент подключен к MongoDB.
	err = client.Ping(context.Background(), nil)
	if err != nil {
		return nil, err
	}

	return client, nil
}

// Save сохраняет файл в MongoDB с использованием GridFS.
func (r *MongoDBRepository) Save(file *domain.FileRequest) error {
	// Создайте новый файл в GridFS.
	uploadStream, err := r.GridFSBucket.OpenUploadStream(file.Name)
	if err != nil {
		return err
	}
	defer func(uploadStream *gridfs.UploadStream) {
		err := uploadStream.Close()
		if err != nil {

		}
	}(uploadStream)

	// Запишите содержимое файла в GridFS.
	_, err = uploadStream.Write(file.Data)
	if err != nil {
		return err
	}

	return nil
}

func (r *MongoDBRepository) FindByID(id string) (*gridfs.DownloadStream, error) {
	fieldId, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		return nil, err
	}

	stream, err := r.GridFSBucket.OpenDownloadStream(fieldId)
	if err != nil {
		return nil, err
	}

	return stream, nil
}

func (r *MongoDBRepository) FindAll() ([]domain.File, error) {
	// Создайте слайс для хранения информации о файлах.
	var files []domain.File

	// Откройте курсор для всех записей в коллекции GridFS.
	cursor, err := r.GridFSBucket.Find(bson.M{})
	if err != nil {
		return nil, err
	}
	defer func(cursor *mongo.Cursor, ctx context.Context) {
		err := cursor.Close(ctx)
		if err != nil {
		}
	}(cursor, context.Background())

	// Переберите все записи и извлеките их метаданные.
	for cursor.Next(context.Background()) {
		var fileInfo gridfs.File
		err := cursor.Decode(&fileInfo)
		if err != nil {
			return nil, err
		}

		log.Println(fileInfo.ID)

		file := domain.File{
			Id:   fileInfo.ID.(primitive.ObjectID).Hex(),
			Name: fileInfo.Name,
		}

		files = append(files, file)
	}

	if err := cursor.Err(); err != nil {
		return nil, err
	}

	return files, nil
}

func (r *MongoDBRepository) Delete(id string) error {
	fileID, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		return err
	}

	// Удалите файл из GridFS по его _id.
	err = r.GridFSBucket.Delete(fileID)
	if err != nil {
		return err
	}

	return nil
}

func (r *MongoDBRepository) UpdateByID(id string, file *domain.FileRequest) error {
	fileID, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		return err
	}

	// Создайте новый поток для записи новой версии файла.
	uploadStream, err := r.GridFSBucket.OpenUploadStreamWithID(fileID, file.Name)
	if err != nil {
		return err
	}
	defer func(uploadStream *gridfs.UploadStream) {
		err := uploadStream.Close()
		if err != nil {

		}
	}(uploadStream)

	// Запишите новое содержимое файла в новую версию.
	_, err = uploadStream.Write(file.Data)
	if err != nil {
		return err
	}

	// Если необходимо, удалите старую версию файла.
	err = r.GridFSBucket.Delete(fileID)
	if err != nil {
		return err
	}

	return nil
}
