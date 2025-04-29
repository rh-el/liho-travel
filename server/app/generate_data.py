from sqlmodel import SQLModel, Session, create_engine
from datetime import datetime, date, timedelta
import random
from faker import Faker
import os
from typing import List, Optional
from models import User, Trip, Activity, Accommodation, FavoriteAccommodation, Participant, Train, Flight, Car

# Configurez l'URL de connexion à votre base de données Supabase
DATABASE_URL = os.environ.get("DATABASE_URL", "postgresql://postgres:$Lolidays2025@db.vlcakogpaxupzutlwwch.supabase.co:5432/postgres")
engine = create_engine(DATABASE_URL)

fake = Faker()

def generate_test_data():
    with Session(engine) as session:
        # Créer 2 utilisateurs
        users = []
        for i in range(2):
            user = User(
                username=fake.user_name(),
                first_name=fake.first_name(),
                last_name=fake.last_name(),
                email=fake.email(),
                password=fake.password(),
                is_admin=False
            )
            session.add(user)
            session.flush()  # Pour obtenir l'ID généré
            users.append(user)
        
        # Générer 10 voyages par utilisateur
        all_trips = []
        for user in users:
            for i in range(10):
                start_date = fake.date_between(start_date='-1y', end_date='+1y')
                end_date = start_date + timedelta(days=random.randint(3, 14))
                
                trip = Trip(
                    name=f"Voyage à {fake.city()}",
                    created_by=user.id,
                    nb_participants=8,  # Nous allons ajouter 8 participants
                    destination=fake.country(),
                    start_date=start_date,
                    end_date=end_date,
                    description=fake.paragraph()
                )
                session.add(trip)
                session.flush()
                all_trips.append(trip)
                
                # 5 activités par voyage
                for j in range(5):
                    activity_date = start_date + timedelta(days=random.randint(0, (end_date - start_date).days))
                    activity = Activity(
                        name=fake.bs(),
                        url=fake.url() if random.random() > 0.3 else None,
                        price=round(random.uniform(0, 200), 2) if random.random() > 0.2 else None,
                        date=activity_date,
                        trip_id=trip.id
                    )
                    session.add(activity)
                
                # 10 propositions d'hébergement par voyage
                accommodations = []
                for j in range(10):
                    accommodation = Accommodation(
                        trip_id=trip.id,
                        url=fake.url() if random.random() > 0.3 else None,
                        additional_information=fake.paragraph() if random.random() > 0.5 else None,
                        is_booked=False
                    )
                    session.add(accommodation)
                    session.flush()
                    accommodations.append(accommodation)
                
                # 8 participants par voyage
                participants = []
                for j in range(8):
                    # 50% de chance que le participant soit un utilisateur existant
                    user_id = users[random.randint(0, 1)].id if j < 2 else None
                    
                    # Dates d'arrivée et de départ
                    arrival_offset = random.randint(0, 2)
                    departure_offset = random.randint(0, 2)
                    arrival_date = start_date - timedelta(days=arrival_offset)
                    departure_date = end_date + timedelta(days=departure_offset)
                    
                    participant = Participant(
                        user_id=user_id,
                        trip_id=trip.id,
                        username=fake.user_name(),
                        arrival_date=arrival_date,
                        departure_date=departure_date
                    )
                    session.add(participant)
                    session.flush()
                    participants.append(participant)
                
                # Votes pour les hébergements préférés
                for participant in participants:
                    # Chaque participant vote pour 1 à 3 hébergements
                    num_votes = random.randint(1, 3)
                    voted_accommodations = random.sample(accommodations, num_votes)
                    
                    for accommodation in voted_accommodations:
                        favorite = FavoriteAccommodation(
                            accommodation_id=accommodation.id,
                            participant_id=participant.id
                        )
                        session.add(favorite)
        
        # 4 propositions de voitures pour le premier voyage
        first_trip = all_trips[0]
        first_trip_participants = session.query(Participant).filter(Participant.trip_id == first_trip.id).all()
        
        for i in range(4):
            driver = random.choice(first_trip_participants)
            departure_date = first_trip.start_date - timedelta(hours=random.randint(1, 5))
            departure_datetime = datetime.combine(departure_date, datetime.min.time()) + timedelta(hours=random.randint(7, 16))
            
            car = Car(
                trip_id=first_trip.id,
                driver_id=driver.id,
                departure_date_time=departure_datetime,
                from_location=fake.city(),
                available_seats=random.randint(1, 4)
            )
            session.add(car)
        
        # 2 propositions de train pour le premier voyage
        for i in range(2):
            departure_date = first_trip.start_date - timedelta(hours=random.randint(1, 10))
            departure_datetime = datetime.combine(departure_date, datetime.min.time()) + timedelta(hours=random.randint(7, 18))
            arrival_datetime = departure_datetime + timedelta(hours=random.randint(2, 7))
            
            train = Train(
                trip_id=first_trip.id,
                from_location=fake.city(),
                to_location=first_trip.destination,
                departure_date_time=departure_datetime,
                arrival_date_time=arrival_datetime,
                price=round(random.uniform(30, 200), 2)
            )
            session.add(train)
        
        # 3 propositions de vols pour le deuxième voyage
        second_trip = all_trips[1]
        for i in range(3):
            departure_date = second_trip.start_date - timedelta(hours=random.randint(12, 48))
            departure_datetime = datetime.combine(departure_date, datetime.min.time()) + timedelta(hours=random.randint(5, 22))
            flight_duration = timedelta(hours=random.randint(1, 8))
            arrival_datetime = departure_datetime + flight_duration
            
            airlines = ["Air France", "Lufthansa", "British Airways", "KLM", "Emirates", "Qatar Airways"]
            flight = Flight(
                trip_id=second_trip.id,
                company_name=random.choice(airlines),
                from_location=fake.city(),
                to_location=second_trip.destination,
                departure_date_time=departure_datetime,
                arrival_date_time=arrival_datetime,
                flight_number=f"{random.choice(['AF', 'BA', 'LH', 'KL', 'EK', 'QR'])}{random.randint(100, 9999)}",
                price=round(random.uniform(100, 1000), 2)
            )
            session.add(flight)
        
        # Commit toutes les données
        session.commit()
        print("Données générées avec succès!")

if __name__ == "__main__":
    # Créer les tables (à décommenter si les tables n'existent pas encore)
    # SQLModel.metadata.create_all(engine)
    
    # Générer les données
    generate_test_data()