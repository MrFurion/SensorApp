Запуск приложения с использованием Docker Compose

1. Зайдите в деректорию с файлам ' docker-compose.yml ' запустите терминал и введите команду.
```
    docker-compose build --no-cache
```

```
    docker-compose up -d
```
2. Запустите браузер и перейдите по адресу.
```
   http://localhost:8080/
```
3. Залогиниться можно под log:  Admin pas:  4444
4. Остановить приложение можно командой
```
   docker compose down
```