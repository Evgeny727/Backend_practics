package cookieservice

import (
	"Cookie/internal/entity"
	"context"
)

// GetAllCookie получение всех cookie
func (s *Service) GetAllCookie(ctx context.Context) ([]entity.CookieResponse, error) {
	cookies, err := s.cookieRepo.GetAll(ctx)
	return cookies, err
}
