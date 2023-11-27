package encryptservice

import (
	"crypto/aes"
	"crypto/cipher"
)

// Decrypt decrypts the given data using AES-GCM.
func (encryptionService *Service) Decrypt(data []byte, nonce []byte) ([]byte, error) {
	block, err := aes.NewCipher(encryptionService.key)
	if err != nil {
		return nil, err
	}
	gcm, err := cipher.NewGCM(block)
	if err != nil {
		return nil, err
	}
	open, err := gcm.Open(nil, nonce, data, nil)
	if err != nil {
		return nil, err
	}
	return open, nil
}
