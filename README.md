# 🔐 SentiPass

SentiPass est une application Android de gestion de mots de passe, développée en **Kotlin (Jetpack Compose)**, connectée à une **API REST** construite avec **Node.js / Express.js** et **MariaDB**.

## ✨ Fonctionnalités

### 🔐 Authentification & Sécurité
- 🔑 Authentification sécurisée avec JWT
- 📱 Stockage local sécurisé du token JWT avec EncryptedSharedPreferences
- 🔒 Chiffrement des données sensibles
- 👤 Création de compte et connexion

### 📁 Gestion des Mots de Passe
- ➕ Ajout de nouveaux mots de passe avec service, URL, email, username, mot de passe et notes
- 📝 Édition des mots de passe existants
- ❌ Suppression de mots de passe individuels
- 🔍 Recherche et filtrage de mots de passe par service, email ou username
- 📋 Copie en un clic des informations (email, mot de passe, URL, notes)
- 🌐 Ouverture directe des sites web depuis l'application
- 👁️ Affichage/masquage des mots de passe
- 📊 Analyse de la santé des mots de passe (faible, moyen, fort)

### 🛠️ Outils Intégrés
- 🎲 **Générateur de mots de passe** avec options personnalisables :
  - Longueur ajustable (8-64 caractères)
  - Lettres majuscules (A-Z)
  - Chiffres (0-9)
  - Symboles spéciaux (@!$%*)
  - Copie automatique en presse-papier
- 🔍 **Analyseur de santé des mots de passe** :
  - Détection des mots de passe faibles
  - Identification des mots de passe réutilisés
  - Indicateurs visuels de force (rouge, jaune, vert)
- 📤 **Export des mots de passe** :
  - Export au format JSON
  - Sauvegarde dans le dossier Téléchargements
  - Horodatage automatique des fichiers

### ⚙️ Paramètres & Gestion de Compte
- 🔄 Changement du mot de passe maître
- 🚪 Déconnexion sécurisée
- 🗑️ Suppression de tous les mots de passe
- 💥 Suppression complète du compte (Nuke)

### 🎨 Interface Utilisateur
- 🎯 Design moderne
- 🌙 Thème sombre élégant
- 📱 Interface intuitive
- 🔄 Navigation fluide entre les écrans
- 💬 Notifications et feedback utilisateur
- 🎨 Icônes de sites web automatiques (favicons)

## 🛠️ Stack Technique

### Android
- **Kotlin** + **Jetpack Compose** (UI moderne)
- **Retrofit** + **Gson** (API REST)
- **ViewModel** + **StateFlow** (gestion d'état)
- **Navigation Compose** (navigation)
- **EncryptedSharedPreferences** (stockage sécurisé)
- **Coil** (chargement d'images)
- **Material 3** (design system)

### Backend
- **Node.js** + **Express.js**
- **JWT** pour l'authentification
- **MariaDB** via `mariadb` npm package
- Reverse Proxy **Apache** + **HTTPS** (Certbot)
- Hébergement : VPS KVM chez Hostinger

## 🔧 Installation (Android)

```bash
git clone https://github.com/TheObtey/sentipass.git
cd sentipass
```

Ouvre le projet avec **Android Studio Arctic Fox** ou supérieur.

### Prérequis
- Android Studio Arctic Fox+
- Android SDK API 31+
- Kotlin 1.8+
- JDK 11+

## 📦 API REST

L'API est documentée dans le dépôt [SentiPass API](https://github.com/TheObtey/sentipass-api).

### Endpoints Principaux

```bash
POST    /register                 # Création de compte
POST    /login                    # Connexion utilisateur
PUT     /update-master-password   # Changement du mot de passe maître
POST    /passwords/add-password   # Ajout d'un mot de passe
GET     /passwords/get-passwords  # Récupération des mots de passe
PUT     /passwords/update-password/{id}  # Modification d'un mot de passe
DELETE  /passwords/delete-password/{id}  # Suppression d'un mot de passe
DELETE  /passwords/delete-all-passwords  # Suppression de tous les mots de passe
DELETE  /nuke                     # Suppression du compte et de tout ses mots de passe
```

### Authentification
Toutes les routes sont protégées via le header :
```http
Authorization: Bearer <token>
```

## 📱 Structure de l'Application

### Écrans Principaux
- **Login** : Connexion utilisateur
- **Register** : Création de compte
- **Home** : Liste des mots de passe avec recherche
- **Tools** : Outils (générateur, analyseur, export)
- **Settings** : Paramètres et gestion de compte

### Composants Clés
- **PasswordDetailsDialog** : Affichage détaillé d'un mot de passe
- **AddPasswordDialog** : Ajout d'un nouveau mot de passe
- **EditPasswordDialog** : Modification d'un mot de passe
- **PasswordGeneratorDialog** : Générateur de mots de passe
- **PasswordHealthDialog** : Analyseur de santé des mots de passe

## 🔒 Sécurité

- **Chiffrement local** : EncryptedSharedPreferences pour le stockage sécurisé du token
- **Chiffrement serveur** : AES-256-CBC est utilisé pour chiffrer les mots de passe stockés
- **Authentification JWT** : Tokens sécurisés pour l'API

## 🚀 Fonctionnalités Avancées

- **Export JSON** : Sauvegarde complète des données en local
- **Analyse de force** : Évaluation automatique de la sécurité des mots de passe
- **Interface intuitive** : Design moderne et accessible
- **Performance optimisée** : Chargement asynchrone et mise en cache

---

## 🧠 À propos

SentiPass est un projet personnel développé par [@TheObtey](https://github.com/TheObtey) dans le cadre de son **BTS SIO SLAM** et de sa montée en compétences en développement mobile et backend.

### Technologies Maîtrisées
- **Android Development** avec Kotlin et Jetpack Compose
- **Backend Development** avec Node.js et Express.js
- **Base de données** avec MariaDB
- **Sécurité** avec JWT et chiffrement AES