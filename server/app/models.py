from pydantic import BaseModel
from sqlmodel import SQLModel, Field, Relationship
from typing import Optional, List
from datetime import datetime, date

class User(SQLModel, table=True):
    __tablename__ = "users"

    id: Optional[int] = Field(default=None, primary_key=True)
    username: str = Field(min_length=2, max_length=30, unique=True)
    first_name: str
    last_name: str
    email: str = Field(unique=True)
    password: str
    is_admin: bool = Field(default=False)
    created_at: Optional[datetime] = Field(default_factory=datetime.now)
    updated_at: Optional[datetime] = Field(default=None, sa_column_kwargs={"onupdate": datetime.now})

    # Relations
    trips: List["Trip"] = Relationship(back_populates="creator")
    participations: Optional[List["Participant"]] = Relationship(back_populates="user")

class Trip(SQLModel, table=True):
    __tablename__ = "trips"
    
    id: Optional[int] = Field(default=None, primary_key=True)
    name: str
    created_by: int = Field(foreign_key="users.id")
    nb_participants: int
    destination: str
    start_date: date
    end_date: date
    description: Optional[str] = None
    created_at: Optional[datetime] = Field(default_factory=datetime.now)
    updated_at: Optional[datetime] = Field(default=None, sa_column_kwargs={"onupdate": datetime.now})
    
    # Relations
    creator: User = Relationship(back_populates="trips")
    activities: List["Activity"] = Relationship(back_populates="trip")
    accommodation: List["Accommodation"] = Relationship(back_populates="trip")
    participants: List["Participant"] = Relationship(back_populates="trip")
    trains: List["Train"] = Relationship(back_populates="trip")
    flights: List["Flight"] = Relationship(back_populates="trip")
    cars: List["Car"] = Relationship(back_populates="trip")

class Activity(SQLModel, table=True):
    __tablename__ = "activities"
    
    id: Optional[int] = Field(default=None, primary_key=True)
    name: str
    url: Optional[str] = None
    price: Optional[float] = None
    date: date
    trip_id: int = Field(foreign_key="trips.id")
    created_at: datetime = Field(default_factory=datetime.now)
    updated_at: Optional[datetime] = Field(default=None, sa_column_kwargs={"onupdate": datetime.now})

    # Relations
    trip: Trip = Relationship(back_populates="activities")


class Accommodation(SQLModel, table=True):
    __tablename__ = "accommodation"
    
    id: Optional[int] = Field(default=None, primary_key=True)
    trip_id: int = Field(foreign_key="trips.id")
    name: str = "Crookston Castle"
    address: str = "170 Brockburn Rd, Glasgow"
    capacity: int = 12
    url: Optional[str] = None
    additional_information: Optional[str] = None
    is_booked: bool = False
    price: Optional[int] = None
    created_at: datetime = Field(default_factory=datetime.now)
    updated_at: Optional[datetime] = None
    
    # Relations
    trip: Trip = Relationship(back_populates="accommodation")
    favorites: List["FavoriteAccommodation"] = Relationship(back_populates="accommodation")


class FavoriteAccommodation(SQLModel, table=True):
    __tablename__ = "favorite_accommodation"
    
    id: Optional[int] = Field(default=None, primary_key=True)
    accommodation_id: int = Field(foreign_key="accommodation.id")
    participant_id: int = Field(foreign_key="participants.id")
    
    # Relations
    accommodation: Accommodation = Relationship(back_populates="favorites")
    participant: "Participant" = Relationship(back_populates="favorite_accommodation")


class Participant(SQLModel, table=True):
    __tablename__ = "participants"
    
    id: Optional[int] = Field(default=None, primary_key=True)
    user_id: Optional[int] = Field(foreign_key="users.id")
    trip_id: int = Field(foreign_key="trips.id")
    username: str
    arrival_date: date
    departure_date: date
    
    # Relations
    trip: Trip = Relationship(back_populates="participants")
    favorite_accommodation: List[FavoriteAccommodation] = Relationship(back_populates="participant")
    user: Optional[User] = Relationship(back_populates="participations")
    car: Optional["Car"] = Relationship(back_populates="driver")


class Train(SQLModel, table=True):
    __tablename__ = "trains"
    
    id: Optional[int] = Field(default=None, primary_key=True)
    trip_id: int = Field(foreign_key="trips.id")
    from_location: str
    to_location: str
    departure_date_time: datetime
    arrival_date_time: datetime
    price: float
    
    # Relations
    trip: Trip = Relationship(back_populates="trains")


class Flight(SQLModel, table=True):
    __tablename__ = "flights"
    
    id: Optional[int] = Field(default=None, primary_key=True)
    trip_id: int = Field(foreign_key="trips.id")
    company_name: str
    from_location: str
    to_location: str
    departure_date_time: datetime
    arrival_date_time: datetime
    flight_number: str
    price: float
    
    # Relations
    trip: Trip = Relationship(back_populates="flights")


class Car(SQLModel, table=True):
    __tablename__ = "cars"
    
    id: Optional[int] = Field(default=None, primary_key=True)
    trip_id: int = Field(foreign_key="trips.id")
    driver_id: int = Field(foreign_key="participants.id")
    departure_date_time: datetime
    from_location: str
    available_seats: int
    
    # Relations
    trip: Trip = Relationship(back_populates="cars")
    driver: Participant = Relationship(back_populates="car")

class AccommodationAndLikes(BaseModel):
    id: Optional[int] = Field(default=None, primary_key=True)
    trip_id: int = Field(foreign_key="trips.id")
    name: str = "Crookston Castle"
    address: str = "170 Brockburn Rd, Glasgow"
    capacity: int = 12
    url: Optional[str] = None
    additional_information: Optional[str] = None
    is_booked: bool = False
    price: Optional[int] = None
    created_at: datetime = Field(default_factory=datetime.now)
    updated_at: Optional[datetime] = None
    is_liked_by: List[int]
    is_liked_by_user: bool
    
class TripWithDetails(BaseModel):
    # Propriétés du Trip
    id: int
    name: str
    created_by: int
    nb_participants: Optional[int]
    destination: str
    start_date: date
    end_date: date
    description: Optional[str] = None
    created_at: Optional[datetime]
    updated_at: Optional[datetime]
    
    participants: List[Participant]
    accommodation: List[AccommodationAndLikes]