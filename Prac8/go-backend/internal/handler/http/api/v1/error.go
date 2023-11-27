package v1

type errorResponse struct {
	Message string `json:"message"`
}

func newErrorResponse(message string) *errorResponse {
	return &errorResponse{Message: message}
}
