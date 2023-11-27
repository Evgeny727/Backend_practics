package encryptservice

import (
	"crypto/aes"
	"crypto/cipher"
	"crypto/rand"
	"io"
)

func (encryptionService *Service) Encrypt(data []byte) ([]byte, []byte, error) {
	block, err := aes.NewCipher(encryptionService.key)
	if err != nil {
		return nil, nil, err
	}

	gcm, err := cipher.NewGCM(block)
	if err != nil {
		return nil, nil, err
	}

	nonce := make([]byte, gcm.NonceSize())
	_, err = io.ReadFull(rand.Reader, nonce)
	if err != nil {
		return nil, nil, err
	}
	return gcm.Seal(nil, nonce, data, nil), nonce, nil
}
