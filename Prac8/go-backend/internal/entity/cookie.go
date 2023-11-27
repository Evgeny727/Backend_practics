package entity

import "encoding/json"

type CookieResponse struct {
	Id          string `json:"id" db:"id"`
	Name        string `json:"name" db:"name"`
	Description string `json:"description" db:"description"`
	CreateDate  string `json:"create_date" db:"create_date"`
}

type CookieRequest struct {
	Name        string `json:"name" validate:"required"`
	Description string `json:"description" validate:"required"`
}

func NewCookieResponse(name string, description string) *CookieResponse {
	return &CookieResponse{
		Name:        name,
		Description: description,
	}
}

// MarshalJSON скрывает Id и CreateDate при сериализации в JSON.
func (c *CookieResponse) MarshalJSON() ([]byte, error) {
	type Alias CookieResponse // Создаем псевдоним для структуры, чтобы исключить поля Id и CreateDate.
	return json.Marshal(&struct {
		*Alias
	}{Alias: (*Alias)(c)})
}

// UnmarshalJSON метод оставляет Id и CreateDate доступными при десериализации из JSON.
func (c *CookieResponse) UnmarshalJSON(data []byte) error {
	type Alias CookieResponse
	temp := &struct {
		*Alias
	}{Alias: (*Alias)(c)}
	if err := json.Unmarshal(data, temp); err != nil {
		return err
	}
	*c = CookieResponse(*temp.Alias)
	return nil
}
