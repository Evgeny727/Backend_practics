package http

import (
	"encoding/json"
	"github.com/gorilla/mux"
	"io"
	"mime/multipart"
	"mongo-db-files-service/internal/domain"
	"mongo-db-files-service/pkg/logger"
	"net/http"
)

// Handlers представляет HTTP-обработчики для вашего API.
type Handlers struct {
	FileService *domain.FileService
	Logger      *logger.Logger
}

// NewHandlers создает новый экземпляр Handlers с внедренными зависимостями.
func NewHandlers(fileService *domain.FileService, logger *logger.Logger) *Handlers {
	return &Handlers{
		FileService: fileService,
		Logger:      logger,
	}
}

func (h Handlers) GetFileByID(writer http.ResponseWriter, request *http.Request) {
	// Извлеките идентификатор файла из параметров запроса
	vars := mux.Vars(request)
	fileID := vars["id"]

	// Получите файл из FileService
	file, err := h.FileService.GetFileByID(fileID)
	if err != nil {
		h.Logger.Error("Failed to retrieve file by Id: ", err)
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}

	// Устанавливаем заголовки HTTP
	writer.Header().Set("Content-Disposition", "attachment; filename="+file.Name)
	writer.Header().Set("Content-Type", http.DetectContentType(file.Data))
	writer.Header().Set("Content-Length", string(rune(len(file.Data))))

	// Отправляем массив байт как ответ
	_, err = writer.Write(file.Data)
	if err != nil {
		return
	}

}

func (h Handlers) CreateFile(writer http.ResponseWriter, request *http.Request) {

	// Получите файл из формы.
	file, header, err := request.FormFile("file")
	if err != nil {
		http.Error(writer, "Bad Request", http.StatusBadRequest)
		return
	}
	defer func(file multipart.File) {
		err := file.Close()
		if err != nil {
		}
	}(file)

	// Прочитайте данные файла.
	fileData, err := io.ReadAll(file)
	if err != nil {
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}

	// Создайте новый файл с FileService
	_, err = h.FileService.CreateFile(domain.NewFileRequest(
		header.Filename,
		header.Size,
		header.Header.Get("Content-Type"),
		fileData,
	))
	if err != nil {
		h.Logger.Error("Failed to create file: ", err)
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}
}

func (h Handlers) GetFileList(writer http.ResponseWriter, request *http.Request) {
	// Получите список всех файлов из FileService
	files, err := h.FileService.GetFileList()
	if err != nil {
		h.Logger.Error("Failed to retrieve file list: ", err)
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}

	// Отправьте список файлов в формате JSON в ответе
	writer.Header().Set("Content-Type", "application/json")
	err = json.NewEncoder(writer).Encode(files)
	if err != nil {
		return
	}
}

func (h Handlers) DeleteFileByID(writer http.ResponseWriter, request *http.Request) {
	// Извлеките идентификатор файла из параметров запроса
	vars := mux.Vars(request)
	fileID := vars["id"]

	// Удалите файл с указанным идентификатором с помощью FileService
	if err := h.FileService.DeleteFileByID(fileID); err != nil {
		h.Logger.Error("Failed to delete file: ", err)
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}

	writer.WriteHeader(http.StatusNoContent)
}

func (h Handlers) GetFileInfoByID(writer http.ResponseWriter, request *http.Request) {
	vars := mux.Vars(request)
	fileID := vars["id"]

	file, err := h.FileService.GetFileInfoByID(fileID)
	if err != nil {
		return
	}
	// Отправьте список файлов в формате JSON в ответе
	writer.Header().Set("Content-Type", "application/json")
	err = json.NewEncoder(writer).Encode(file)
	if err != nil {
		return
	}
}

func (h Handlers) UpdateFileByID(writer http.ResponseWriter, request *http.Request) {
	vars := mux.Vars(request)
	fileID := vars["id"]

	file, header, err := request.FormFile("file")
	if err != nil {
		http.Error(writer, "Bad Request", http.StatusBadRequest)
		return
	}
	defer func(file multipart.File) {
		err := file.Close()
		if err != nil {
		}
	}(file)

	// Прочитайте данные файла.
	fileData, err := io.ReadAll(file)
	if err != nil {
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}

	fileRequest := domain.NewFileRequest(
		header.Filename,
		header.Size,
		header.Header.Get("Content-Type"),
		fileData,
	)

	h.FileService.UpdateFileByID(fileID, fileRequest)
}
