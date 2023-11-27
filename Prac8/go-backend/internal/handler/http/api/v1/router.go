package v1

import (
	"github.com/gorilla/mux"
	"net/http"
)

func (h *Handler) GetVersion() string {
	return "v1"
}

func (h *Handler) GetContentType() string {
	return ""
}

func (h *Handler) AddRoutes(r *mux.Router) {
	r.HandleFunc("/cookie", h.SaveCookie).Methods(http.MethodPost)
	r.HandleFunc("/cookie", h.GetAllCookie).Methods(http.MethodGet)
}
