# database_introspection.py
from sqlalchemy import MetaData, create_engine, inspect
import os
from dotenv import load_dotenv

# Charger les variables d'environnement
load_dotenv()

# Récupérer l'URL de la base de données depuis les variables d'environnement
DATABASE_URL = os.getenv("DATABASE_URL")

# Créer le moteur SQLAlchemy
engine = create_engine(DATABASE_URL)

# Créer un inspecteur
inspector = inspect(engine)

# Obtenir la liste des tables (exclure les tables système de Supabase)
tables = [table for table in inspector.get_table_names() 
          if not table.startswith('pg_') and 
             not table.startswith('auth_') and 
             not table.startswith('supabase_')]

# Afficher les tables
print("Tables trouvées:", tables)

# Pour chaque table, obtenir les colonnes et leurs types
for table in tables:
    print(f"\nTable: {table}")
    columns = inspector.get_columns(table)
    for column in columns:
        print(f"  - {column['name']}: {column['type']} (Nullable: {column['nullable']})")
    
    # Obtenir la clé primaire
    pk = inspector.get_pk_constraint(table)
    if pk['constrained_columns']:
        print(f"  - Primary Key: {pk['constrained_columns']}")
    
    # Obtenir les clés étrangères
    fks = inspector.get_foreign_keys(table)
    for fk in fks:
        print(f"  - Foreign Key: {fk['constrained_columns']} -> {fk['referred_table']}.{fk['referred_columns']}")