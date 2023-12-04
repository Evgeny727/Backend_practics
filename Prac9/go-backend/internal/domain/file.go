package domain

// File представляет структуру файла.
type File struct {
	Id   string `bson:"_id"`
	Name string
}

type FileInfo struct {
	Id     string `bson:"_id"`
	Name   string
	Format string
}

type FileRequest struct {
	Name   string
	Size   int64
	Format string
	Data   []byte
}

func NewFileRequest(
	name string,
	size int64,
	format string,
	data []byte,
) *FileRequest {
	return &FileRequest{
		Name:   name,
		Size:   size,
		Format: format,
		Data:   data,
	}
}

type FileDownload struct {
	Name string
	Data []byte
}

func NewFileDownload(name string, data []byte) *FileDownload {
	return &FileDownload{
		Name: name,
		Data: data,
	}
}
