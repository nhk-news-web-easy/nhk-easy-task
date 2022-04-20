# nhk-easy-task

Daily task to fetch and parse news from [NEWS WEB EASY](https://www3.nhk.or.jp/news/easy/), the task runs at 10:00 AM (UTC) every day.

## Getting started
```sh
docker run -e MYSQL_HOST=ip-address-of-mysql \
  -e MYSQL_USER=your-mysql-user \
  -e MYSQL_PASSWORD=your-mysql-user-password \
  -e SENTRY_DSN=sentry-dsn \
  -d xiaodanmao/nhk-easy-task
```

## License
[MIT](LICENSE)
