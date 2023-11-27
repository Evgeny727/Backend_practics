package middleware

import (
	"Cookie/internal/usecase"
	"Cookie/pkg/logger"
	"bytes"
	"encoding/base64"
	"encoding/json"
	"io"
	"net/http"
)

const nameCookie = "user-request"

func CookieMiddleware(log logger.Logger, encryptionService usecase.EncryptionService) func(next http.Handler) http.Handler {
	return func(next http.Handler) http.Handler {
		return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			log.Info("CookieMiddleware")
			c, err := r.Cookie(nameCookie)
			if err == nil {
				decrypted, _ := encryptionService.DecryptCookie(*c)
				log.Info("Расшифрованные cookie")
				log.Info(string(decrypted))
			}

			// Парсим JSON-запрос, отправленный пользователем
			requestData, err := io.ReadAll(r.Body)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
				return
			}

			// Восстанавливаем оригинальное тело запроса
			r.Body = io.NopCloser(bytes.NewBuffer(requestData))

			var requestBody map[string]interface{}
			err = json.NewDecoder(bytes.NewBuffer(requestData)).Decode(&requestBody)
			if err != nil && requestBody != nil {
				http.Error(w, err.Error(), http.StatusBadRequest)
				return
			}

			// Convert the JSON data to bytes
			jsonData, err := json.Marshal(requestBody)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
				return
			}

			// Encrypt the JSON data
			ciphertext, nonce, _ := encryptionService.Encrypt(jsonData)
			cookie := http.Cookie{
				Name:  nameCookie,
				Value: base64.URLEncoding.EncodeToString(ciphertext) + "." + base64.URLEncoding.EncodeToString(nonce),
				Path:  "/",
			}
			http.SetCookie(w, &cookie)
			next.ServeHTTP(w, r)
		})
	}
}
