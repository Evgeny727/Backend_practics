package usecase

import (
	"Cookie/internal/entity"
	"context"
)

func (u *UseCase) SaveCookie(ctx context.Context, cookie entity.CookieResponse) (interface{}, error) {
	err := u.cookieService.SaveCookie(ctx, cookie)
	if err != nil {
		return nil, err
	}
	return nil, nil
}
