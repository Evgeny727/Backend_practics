package v1

import (
	"Cookie/internal/entity"
	"Cookie/internal/handler/http/resp"
	"encoding/json"
	"github.com/go-playground/validator/v10"
	"github.com/unrolled/render"
	"go.uber.org/zap"
	"net/http"
	"strings"
)

// SaveCookie endpoint для обработки переданные cookie от пользователя
// @Summary Добавление нового cookie
// @Tag cookie
// @Accept json
// @Produce json
// @Param request body entity.CookieRequest true "cookie"
// @Success 201
// @Success 400 {object} resp.Response
// @Router /cookie [post]
func (h *Handler) SaveCookie(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var cookieRequest entity.CookieRequest
	err := json.NewDecoder(r.Body).Decode(&cookieRequest)
	if err != nil {
		err := render.New().JSON(
			w,
			http.StatusBadRequest,
			resp.Error(err.Error()),
		)
		if err != nil {
			return
		}
		return
	}

	if err := validator.New().Struct(cookieRequest); err != nil {
		err := render.New().JSON(
			w,
			http.StatusBadRequest,
			resp.Error(strings.Split(err.Error(), "\n")),
		)

		if err != nil {
			return
		}
		return
	}

	ctx := r.Context()

	cookie := *entity.NewCookieResponse(
		cookieRequest.Name,
		cookieRequest.Description,
	)
	_, err = h.uc.SaveCookie(ctx, cookie)
	if err != nil {
		h.logger.Error("failed to encode json", zap.Error(err))
		err := render.New().JSON(
			w,
			http.StatusBadRequest,
			resp.Error(err),
		)

		if err != nil {
			return
		}
	}
	w.WriteHeader(http.StatusCreated)
}

// GetAllCookie
// @Summary Получение всех cookie
// @Tag cookie
// @Accept json
// @Produce json
// @Success 200 {array} entity.CookieResponse
// @Success 400 {object} resp.Response
// @Router /cookie [get]
func (h *Handler) GetAllCookie(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	ctx := r.Context()
	res, err := h.uc.GetAllCookie(ctx)
	err = json.NewEncoder(w).Encode(res)
	if err != nil {
		h.logger.Error("failed to encode json", zap.Error(err))
	}
}
