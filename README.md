# nhk-easy-task [![CircleCI](https://circleci.com/gh/nhk-news-web-easy/nhk-easy-task/tree/main.svg?style=svg)](https://circleci.com/gh/nhk-news-web-easy/nhk-easy-task/tree/main) [![Build status](https://ci.appveyor.com/api/projects/status/p6gwnds9j4yfc3m5/branch/main?svg=true)](https://ci.appveyor.com/project/Frederick-S/nhk-easy-task-or30i/branch/main)

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
