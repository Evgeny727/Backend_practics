package domain

import "go.mongodb.org/mongo-driver/mongo/gridfs"

type FileRepository interface {
	Save(file *FileRequest) error
	FindByID(id string) (*gridfs.DownloadStream, error)
	FindAll() ([]File, error)
	Delete(id string) error
	UpdateByID(id string, file *FileRequest) error
}
