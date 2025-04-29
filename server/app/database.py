from sqlmodel import SQLModel, create_engine, Session
import os
from dotenv import load_dotenv

# Charger les variables d'environnement
load_dotenv()

# Récupérer l'URL de la base de données depuis les variables d'environnement
DATABASE_URL = os.getenv("DATABASE_URL")

# Créer le moteur SQLAlchemy
engine = create_engine(DATABASE_URL)

# Fonction pour obtenir une session de base de données
def get_session():
    with Session(engine) as session:
        yield session