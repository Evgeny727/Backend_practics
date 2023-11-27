package encryptservice

type Service struct {
	key []byte
}

// NewEncryptionService creates a new instance of EncryptionService with the given key.
func NewEncryptionService(key string) *Service {
	return &Service{
		key: []byte(key),
	}
}
