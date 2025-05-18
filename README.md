# nhk-easy-task [![Java CI with Maven](https://github.com/nhk-news-web-easy/nhk-easy-task/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/nhk-news-web-easy/nhk-easy-task/actions/workflows/build.yml) [![codecov](https://codecov.io/gh/nhk-news-web-easy/nhk-easy-task/branch/main/graph/badge.svg?token=T5951LVNJG)](https://codecov.io/gh/nhk-news-web-easy/nhk-easy-task)

Task to fetch and parse news from [NEWS WEB EASY](https://www3.nhk.or.jp/news/easy/).

## Getting started
Create a MySQL database called `nhk` and import table structures from [init.sql](https://github.com/nhk-news-web-easy/nhk-easy-entity/blob/main/db/init.sql), then run:

```sh
docker run -e MYSQL_HOST=ip-address-of-mysql \
  -e MYSQL_USER=your-mysql-user \
  -e MYSQL_PASSWORD=your-mysql-user-password \
  -p 8080:8080 \
  -d xiaodanmao/nhk-easy-task
```

Or run with Sentry enabled:

```sh
docker run -e MYSQL_HOST=ip-address-of-mysql \
  -e MYSQL_USER=your-mysql-user \
  -e MYSQL_PASSWORD=your-mysql-user-password \
  -e SENTRY_DSN=your-sentry-dsn \
  -p 8080:8080 \
  -d xiaodanmao/nhk-easy-task
```

### Cron
Schedule with `crontab`:
```sh
0 * * * * sudo docker run -e MYSQL_HOST=ip-address-of-mysql -e MYSQL_USER=your-mysql-user -e MYSQL_PASSWORD=your-mysql-user-password -e SENTRY_DSN=your-sentry-dsn xiaodanmao/nhk-easy-task >> $HOME/nhk-easy-task.log 2>&1
```

## License
[MIT](LICENSE)
