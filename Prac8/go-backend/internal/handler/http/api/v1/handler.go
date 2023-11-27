package v1

import (
	"Cookie/internal/entity"
	"Cookie/pkg/logger"
	"context"
)

type UseCase interface {
	SaveCookie(ctx context.Context, cookie entity.CookieResponse) (interface{}, error)
	GetAllCookie(ctx context.Context) (interface{}, error)
}

type Handler struct {
	uc     UseCase
	logger logger.Logger
}

func NewHandler(uc UseCase, logs logger.Logger) *Handler {
	return &Handler{uc: uc, logger: logs}
}
