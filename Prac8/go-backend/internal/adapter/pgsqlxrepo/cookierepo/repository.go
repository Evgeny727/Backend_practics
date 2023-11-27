package cookierepo

import (
	"Cookie/internal/adapter/pgsqlxrepo"
	"github.com/jmoiron/sqlx"
)

type Repository struct {
	pgsqlxrepo.Transactor
}

func NewRepository(conn *sqlx.DB) *Repository {
	return &Repository{
		pgsqlxrepo.NewTransactor(conn),
	}
}
