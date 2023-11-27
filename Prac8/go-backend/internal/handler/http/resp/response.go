package resp

type Response struct {
	Status  string      `json:"status"`
	Message interface{} `json:"message,omitempty"`
}

const (
	statusOk    = "OK"
	statusError = "ERROR"
)

func OK() Response {
	return Response{
		Status: statusOk,
	}
}

func Error(msg interface{}) Response {
	return Response{
		Status:  statusError,
		Message: msg,
	}
}
