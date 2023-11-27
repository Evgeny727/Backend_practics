package cookieservice

import (
	"Cookie/internal/entity"
	"context"
)

// SaveCookie логика сохранения и шифрования cookie
func (s *Service) SaveCookie(ctx context.Context, cookie entity.CookieResponse) error {
	err := s.cookieRepo.Save(ctx, cookie)
	if err != nil {
		return err
	}

	return nil
}
