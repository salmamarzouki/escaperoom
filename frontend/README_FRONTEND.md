# 🔐 Escape Room Frontend - Guide d'Utilisation

## 🎮 Comment Utiliser

### 1️⃣ **Page de Connexion** (`login.html`)
- Ouvrez `login.html` dans votre navigateur
- **Identifiants autorisés uniquement:**
  - 👤 **Agent Salma:** `salma` / `123456`
  - 👤 **Agent Marwa:** `marwa` / `marwa`
- Cliquez sur "UNLOCK THE ROOM"
- Vous serez redirigé vers l'application principale après 2 secondes
- ⚠️ **Attention:** Les identifiants incorrects seront rejetés!

### 2️⃣ **Application Principale** (`index.html`)
- Vous verrez 5 onglets pour tester différents services:
  - 👤 **Client** - Enregistrement de client
  - 🚪 **Room** - Configuration de salle **[NOUVELLE INTERFACE ANIMÉE 3D!]**
  - 📅 **Reservation** - Réservation
  - 🎮 **Game** - Gestion de jeu
  - 🏆 **Score** - Enregistrement de score

### 🆕 **Interface Room Animée 3D**
L'onglet Room dispose maintenant d'une **salle 3D interactive** avec:
- 🏠 **Salle 3D rotative** avec murs, sol, plafond
- 🚪 **Porte animée** qui s'ouvre/se ferme selon la disponibilité
- 🔐 **Cadenas pulsant** sur la porte
- 🕐 **Horloge et décorations** murales flottantes
- 📚 **Étagère** avec objets animés
- 💡 **Lustre** qui se balance
- 🎨 **Thèmes de difficulté:**
  - 😊 **EASY** - Ambiance lumineuse et verte
  - 😐 **MEDIUM** - Ambiance neutre et orangée
  - 😈 **HARD** - Ambiance sombre et rouge
- 📊 **Affichage en temps réel** du statut, capacité et difficulté
- ➕➖ **Boutons +/-** pour ajuster la capacité
- 🎯 **Boutons visuels** pour sélectionner la difficulté
- 🔄 **Toggle animé** pour la disponibilité

### 3️⃣ **Navigation**
- Cliquez sur les onglets pour basculer entre les services
- Chaque onglet affiche un formulaire de validation
- Remplissez les champs et cliquez sur "Validate" pour tester
- Les résultats s'affichent en dessous du formulaire

### 4️⃣ **Déconnexion**
- Cliquez sur "🚪 EXIT ROOM" en haut à droite
- Confirmez pour retourner à la page de connexion

## 🎨 Thème Escape Room

### Éléments Visuels
- **Fond sombre** avec effets de brouillard
- **Pièces de puzzle flottantes** (🧩🔑🗝️⚙️)
- **Animations de lueur** sur les éléments interactifs
- **Effets de glitch** sur le titre
- **Compte à rebours** sur la page de connexion

### Couleurs
- 🔴 **Rouge sang** (`#8b0000`) - Danger/Urgence
- 🟠 **Orange rouille** (`#cc5500`) - Avertissements
- 🟡 **Or** (`#d4af37`) - Éléments importants
- 🟢 **Émeraude** (`#10b981`) - Succès

## 🔧 Fichiers

- `login.html` - Page de connexion
- `login-styles.css` - Styles de la page de connexion
- `login.js` - Logique de connexion et animations
- `index.html` - Application principale
- `styles.css` - Styles de l'application principale
- `validation.js` - Logique de validation et gestion de session

## ✨ Fonctionnalités

### Gestion de Session
- Stockage du nom d'utilisateur dans `sessionStorage`
- Redirection automatique si non connecté
- Bouton de déconnexion avec confirmation

### Animations
- Transition fluide entre les onglets
- Effets de survol sur les boutons
- Particules animées en arrière-plan
- Pulsation des éléments importants

### Validation
- Validation en temps réel des formulaires
- Messages d'erreur détaillés
- Affichage des partitions d'équivalence
- Tests de valeurs limites

## 🚀 Pour Commencer

1. Ouvrez `login.html` dans votre navigateur
2. Connectez-vous avec n'importe quels identifiants
3. Explorez les 5 services de test
4. Testez les validations avec différentes valeurs

## 📱 Responsive

Le design s'adapte automatiquement aux écrans mobiles et tablettes.

## 🎯 Objectif

Ce frontend permet de tester interactivement les 107 cas de test du projet Black-Box Testing avec une interface immersive sur le thème des escape rooms.

---

**Bon test! 🧪🔓**

