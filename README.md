⚙️ 1. Prérequis

Java 17+

Gradle 8+ ou Maven 3.9+

MySQL (ou H2 pour les tests)

Spring Boot CLI (optionnel)

Postman / cURL pour tester les endpoints

🧰 2. Installation & exécution locale
Cloner le dépôt :
git clone TODO_URL_REPO
cd project-manager-multi-tenant-back

Configurer la base de données :

Dans src/main/resources/application-dev.properties

▶️ 3. Lancer le projet
Avec Gradle :
./gradlew bootRun

Vérifier :

Ouvre http://localhost:8080/swagger-ui/index.html

🧪 4. Profils disponibles
Profil	Description
dev	Environnement de développement local (MockMVC + H2)
prod	Déploiement Render ou serveur distant
test	Tests unitaires / intégration (MockMVC + H2)

🔑 5. Authentification & Sécurité
JWT (JSON Web Token)

Login : /auth/login

Register : /auth/register

Chaque requête protégée doit contenir :


Demo link Swager: https://project-manager-multi-tenant-back.onrender.com/swagger-ui/index.html