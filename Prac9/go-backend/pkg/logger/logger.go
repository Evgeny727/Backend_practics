package logger

import (
	"log"
	"os"
)

// Logger представляет логгер для вашего проекта.
type Logger struct {
	logger *log.Logger
}

// NewLogger создает новый экземпляр логгера.
func NewLogger() *Logger {
	return &Logger{
		logger: log.New(os.Stdout, "[YourApp] ", log.LstdFlags),
	}
}

// Info используется для записи информационных сообщений.
func (l *Logger) Info(message string) {
	l.logger.Printf("[INFO] %s", message)
}

// Error используется для записи сообщений об ошибках.
func (l *Logger) Error(message string, err error) {
	l.logger.Printf("[ERROR] %s: %v", message, err)
}

// Warning используется для записи предупреждений.
func (l *Logger) Warning(message string) {
	l.logger.Printf("[WARNING] %s", message)
}

// Debug используется для записи отладочных сообщений.
func (l *Logger) Debug(message string) {
	l.logger.Printf("[DEBUG] %s", message)
}

// CustomLog используется для записи пользовательских сообщений с указанным уровнем.
func (l *Logger) CustomLog(level, message string) {
	l.logger.Printf("[%s] %s", level, message)
}
