package http

import (
	"github.com/gorilla/mux"
	"net/http"
)

// ConfigureRouter настраивает маршруты для вашего HTTP-сервера.
func ConfigureRouter(handlers *Handlers) *mux.Router {
	r := mux.NewRouter()
	r.Use(mux.CORSMethodMiddleware(r))


	r.HandleFunc("/files/{id}", handlers.GetFileByID).Methods(http.MethodGet)
	r.HandleFunc("/files/info/{id}", handlers.GetFileInfoByID).Methods(http.MethodGet)
	r.HandleFunc("/files", handlers.GetFileList).Methods(http.MethodGet)

	r.HandleFunc("/files/{id}", handlers.UpdateFileByID).Methods(http.MethodPut)

	r.HandleFunc("/files/{id}", handlers.DeleteFileByID).Methods(http.MethodDelete)
	r.HandleFunc("/files", handlers.CreateFile).Methods(http.MethodPost)

	return r
}
