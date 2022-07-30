# nhk-easy-task [![Java CI with Maven](https://github.com/nhk-news-web-easy/nhk-easy-task/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/nhk-news-web-easy/nhk-easy-task/actions/workflows/build.yml)

Daily task to fetch and parse news from [NEWS WEB EASY](https://www3.nhk.or.jp/news/easy/), the task runs at 10:00 AM (UTC) every day.

## Getting started
Create a MySQL database called `nhk`, then run:

```sh
docker run -e MYSQL_HOST=ip-address-of-mysql \
  -e MYSQL_USER=your-mysql-user \
  -e MYSQL_PASSWORD=your-mysql-user-password \
  -d xiaodanmao/nhk-easy-task
```

Or run with Sentry enabled:

```sh
docker run -e MYSQL_HOST=ip-address-of-mysql \
  -e MYSQL_USER=your-mysql-user \
  -e MYSQL_PASSWORD=your-mysql-user-password \
  -e SENTRY_DSN=your-sentry-dsn \
  -d xiaodanmao/nhk-easy-task
```

## License
[MIT](LICENSE)
