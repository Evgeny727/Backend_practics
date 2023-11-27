package usecase

import "context"

func (u *UseCase) GetAllCookie(ctx context.Context) (interface{}, error) {
	cookies, err := u.cookieService.GetAllCookie(ctx)
	return cookies, err
}
