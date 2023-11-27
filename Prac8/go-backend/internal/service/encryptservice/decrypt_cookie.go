package encryptservice

import (
	"encoding/base64"
	"net/http"
	"strings"
)

func (encryptionService *Service) DecryptCookie(cookie http.Cookie) ([]byte, error) {
	// Разбиваем значение куки на `ciphertext` и `nonce`
	cookieValue := strings.Split(cookie.Value, ".")
	if len(cookieValue) != 2 {
		return nil, nil
	}

	ciphertext, err := base64.URLEncoding.DecodeString(cookieValue[0])
	if err != nil {
		return nil, err
	}

	nonce, err := base64.URLEncoding.DecodeString(cookieValue[1])
	if err != nil {
		return nil, err
	}

	// Используем nonce и секретный ключ для дешифровки данных
	decrypted, err := encryptionService.Decrypt(ciphertext, nonce)
	if err != nil {
		return nil, err
	}

	return decrypted, nil
}
