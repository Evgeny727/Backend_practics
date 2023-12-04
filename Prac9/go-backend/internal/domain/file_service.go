package domain

import (
	"bytes"
	"go.mongodb.org/mongo-driver/mongo/gridfs"
	"io"
)

// FileService представляет бизнес-логику для операций с файлами.
type FileService struct {
	Repository FileRepository
}

// NewFileService создает новый экземпляр FileService с переданным репозиторием.
func NewFileService(repository FileRepository) *FileService {
	return &FileService{Repository: repository}
}

// CreateFile создает новый файл и сохраняет его в репозитории.
func (s *FileService) CreateFile(file *FileRequest) (*FileRequest, error) {
	// Выполните проверки и логику для создания файла, затем сохраните его в репозитории.
	if err := s.Repository.Save(file); err != nil {
		return nil, err
	}
	return file, nil
}

// GetFileByID возвращает файл по его идентификатору.
func (s *FileService) GetFileByID(id string) (*FileDownload, error) {
	stream, err := s.Repository.FindByID(id)
	if err != nil {
		return nil, err
	}
	buffer := new(bytes.Buffer)
	_, err = io.Copy(buffer, stream)
	if err != nil {
		return nil, err
	}

	return NewFileDownload(
		stream.GetFile().Name,
		buffer.Bytes(),
	), nil
}

// GetFileList возвращает список всех файлов.
func (s *FileService) GetFileList() ([]File, error) {
	return s.Repository.FindAll()
}

// DeleteFileByID удаляет файл по его идентификатору.
func (s *FileService) DeleteFileByID(id string) error {
	return s.Repository.Delete(id)
}

func (s *FileService) GetFileInfoByID(id string) (*gridfs.File, error) {
	stream, err := s.Repository.FindByID(id)
	if err != nil {
		return nil, err
	}

	return stream.GetFile(), nil
}

func (s *FileService) UpdateFileByID(id string, request *FileRequest) {
	err := s.Repository.UpdateByID(id, request)
	if err != nil {
		return
	}
}
