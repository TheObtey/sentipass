# 📦 Commandes utiles – API SentiPass (systemd)

Voici une liste complète des commandes pratiques pour gérer le service systemd de ton API Node.js (`sentipass-api`), directement sur ton VPS.

---

## ▶️ Démarrer l’API

```bash
sudo systemctl start sentipass-api
```

---

## ⏹️ Arrêter l’API

```bash
sudo systemctl stop sentipass-api
```

---

## 🔁 Redémarrer l’API

```bash
sudo systemctl restart sentipass-api
```

---

## 🟢 Vérifier l’état du service

```bash
sudo systemctl status sentipass-api
```

> Donne les infos du service : actif, PID, logs récents...

---

## 📜 Voir les logs en direct

```bash
journalctl -u sentipass-api -f
```

> Le `-f` fonctionne comme `tail -f`, pour suivre les logs en temps réel.

---

## 📅 Voir les logs d’un jour spécifique

```bash
journalctl -u sentipass-api --since today
```

---

## ⚙️ Activer l’API au démarrage du VPS

```bash
sudo systemctl enable sentipass-api
```

---

## ❌ Désactiver le lancement automatique

```bash
sudo systemctl disable sentipass-api
```

---

## 📁 Emplacement du service

```bash
/etc/systemd/system/sentipass-api.service
```

---

## 🧠 Rappel du chemin de l’app

```bash
/var/www/sentipass-api
```

---

Pense à relancer `sudo systemctl daemon-reload` si tu modifies le fichier du service.
