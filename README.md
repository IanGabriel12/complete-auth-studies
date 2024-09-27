## Introduction
This is a study project. This repository contains an application that handles user authentication. It supports E-mail confirmation, password recover, and image upload.
The purpose of this application is to improve understanding about the following features:
- ✉️ - Password recovery flow
- ☁️ - Image upload, locally and at the cloud
- 🔒 - JWT Authentication with Spring Security

## Running locally
### Requisites
Only Docker

### Running backend
You should export to your environment the following variables:

```sh
export JWT_SECRET={ some secret key }
export MAIL_USERNAME={ your google account username }
export MAIL_PASSWORD={ your google account application password }
```

Notice that `MAIL_PASSWORD` is the google application password, not your regular password. You can read about application passwords [here](https://support.google.com/accounts/answer/185833?hl=en).

Then go to `/spring-boot-auth` dir. After that, startup database with `docker compose up -d`. Finally, startup the API with `./mvnw spring-boot:run`
