// Code generated by goro;

package usecase

import (
	"Cookie/internal/entity"
	"context"
	"net/http"
)

type cookieService interface {
	SaveCookie(ctx context.Context, cookie entity.CookieResponse) error
	GetAllCookie(ctx context.Context) ([]entity.CookieResponse, error)
}

type EncryptionService interface {
	Decrypt(data []byte, nonce []byte) ([]byte, error)
	Encrypt(data []byte) ([]byte, []byte, error)
	DecryptCookie(cookie http.Cookie) ([]byte, error)
}

type UseCase struct {
	cookieService cookieService
}

func NewUseCase(cookieService cookieService) *UseCase {
	return &UseCase{
		cookieService: cookieService,
	}
}
