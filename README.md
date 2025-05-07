# 🔐 SentiPass

SentiPass est une application Android de gestion de mots de passe, développée en **Kotlin (Jetpack Compose)**, connectée à une **API REST** construite avec **Node.js / Express.js** et **MariaDB**.

## ✨ Fonctionnalités prévues

- 🔑 Authentification sécurisée avec JWT
- 📁 Stockage de mots de passe via une API distante
- 🔍 Recherche et filtrage de mots de passe
- ➕ Ajout et ❌ suppression de mots de passe
- 🧠 Vérification de la sécurité d’un mot de passe
- 🔐 Stockage local sécurisé du token JWT
- 📤 Export JSON/CSV (à venir)

## 🛠️ Stack technique

### Android
- Kotlin + Jetpack Compose
- Retrofit + Gson
- ViewModel + LiveData
- EncryptedSharedPreferences
- Navigation Compose

### Backend
- Node.js + Express.js
- JWT pour l'auth
- MariaDB via `mariadb` npm package
- Déploiement via PM2 + `deploy.sh`
- Reverse Proxy Apache + HTTPS (Certbot)
- Hébergement : VPS KVM chez Hostinger

## 🔧 Installation (Android)

```bash
git clone https://github.com/<ton-user>/sentipass.git
cd sentipass
```

Ouvre le projet avec Android Studio Arctic Fox ou supérieur.

---

## 📦 API REST

L'API est documentée dans le dépôt [SentiPass API](https://github.com//sentipass-api) (privé/public selon le cas).

Endpoints principaux :

``````bash
POST    /login
POST    /register
GET     /passwords
POST    /passwords
DELETE  /passwords/:id
PUT     /passwords/:id
``````

Toutes les routes sont protégées via le header :

``````http
Authorization: Bearer <token>
``````

---

## 🧠 À propos

SentiPass est un projet personnel développé par [@TheObtey](https://github.com/TheObtey) dans le cadre de son **BTS SIO SLAM** et de sa montée en compétences en développement mobile et backend.