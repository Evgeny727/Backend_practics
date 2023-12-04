package config

import (
	"github.com/spf13/viper"
)

// Config представляет структуру конфигурации.
type Config struct {
	Host            string
	Port            int
	MongoDBURL      string
	MongoDBDatabase string
}

// Load загружает конфигурацию из файла и переменных окружения.
func Load() (*Config, error) {
	// Инициализируем Viper
	viper.SetConfigName("config")
	viper.AddConfigPath(".")
	viper.SetConfigType("toml")
	viper.AutomaticEnv()

	// Прочитаем файл конфигурации
	if err := viper.ReadInConfig(); err != nil {
		return nil, err
	}

	// Создаем структуру конфигурации
	config := &Config{
		Host:            viper.GetString("host"),
		Port:            viper.GetInt("port"),
		MongoDBURL:      viper.GetString("mongodb_url"),
		MongoDBDatabase: viper.GetString("mongodb_database"),
	}

	return config, nil
}
