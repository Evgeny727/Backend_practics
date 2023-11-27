package cookierepo

import (
	"Cookie/internal/entity"
	"context"
)

// GetAll получение всех cookie
func (r *Repository) GetAll(ctx context.Context) ([]entity.CookieResponse, error) {
	conn := r.GetConn(ctx)

	var cookies []entity.CookieResponse
	err := conn.SelectContext(
		ctx,
		&cookies,
		"SELECT id, name, description, create_date FROM public.cookie",
	)

	if err != nil {
		return nil, err
	}

	return cookies, nil
}
