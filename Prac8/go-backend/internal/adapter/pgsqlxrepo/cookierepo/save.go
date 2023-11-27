package cookierepo

import (
	"Cookie/internal/entity"
	"context"
)

// Save сохраниения cookie в postgresql
func (r *Repository) Save(ctx context.Context, cookie entity.CookieResponse) error {
	conn := r.GetConn(ctx)

	err := conn.Ping()
	if err != nil {
		return err
	}

	_, err = conn.NamedExecContext(
		ctx,
		`
		INSERT INTO cookie (name, description)
		VALUES (:name, :description)
			    `,
		&cookie,
	)
	if err != nil {
		return err
	}

	return nil
}
