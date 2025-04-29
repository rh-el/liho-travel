from fastapi import APIRouter, Depends, HTTPException
from sqlmodel import Session, select
from .database import get_session
from .models import *
import httpx
import asyncio

router = APIRouter()


# GET USER FROM ID MODEL
# @router.get("/users/{user_id}", response_model=User)
# def read_user(user_id: int, session: Session = Depends(get_session)):
#     user = session.get(User, user_id)
#     if not user:
#         raise HTTPException(status_code=404, detail="Utilisateur non trouvé")
#     return user


@router.get("/users/", response_model=list[User])
def read_users(session: Session = Depends(get_session)):
    users = session.exec(select(User)).all()
    return users

@router.post("/users/", response_model=User)
def create_user(user: User, session: Session = Depends(get_session)):
    session.add(user)
    session.commit()
    session.refresh(user)
    return user

@router.get("/participants/{trip_id}", response_model=List[Participant])
async def read_participants_from_trip_id(trip_id: int, session: Session = Depends(get_session)):
    get_participants_query = select(Participant).where(Participant.trip_id == trip_id)
    participants = session.exec(get_participants_query).all()
    return participants

@router.get("/accommodation/{trip_id}", response_model=List[Accommodation])
async def read_accommodation_from_trip_id(trip_id: str, session: Session = Depends(get_session)):
    get_accommodation_query = select(Accommodation).where(Accommodation.trip_id == trip_id)
    accommodation = session.exec(get_accommodation_query).all()
    return accommodation



@router.get("/trips/{trip_id}", response_model=TripWithDetails)
async def read_trip_with_details(trip_id: int, session: Session = Depends(get_session)):
    trip = session.get(Trip, trip_id)
    if not trip:
        raise HTTPException(status_code=404, detail="Trip not found")
    
    async with httpx.AsyncClient() as client:
        tasks = [
            client.get(f"http://localhost:8000/participants/{trip_id}"),
            client.get(f"http://localhost:8000/accommodation/{trip_id}")
        ]
        responses = await asyncio.gather(*tasks)
    
    participants = responses[0].json()
    accommodation = responses[1].json()

    accommodation_and_likes = []
    for acc_dict in accommodation:
        acc_obj = Accommodation(**acc_dict)
        fav_accommodation_statement = select(FavoriteAccommodation.participant_id).where(FavoriteAccommodation.accommodation_id == acc_obj.id)
        fav_accommodation = session.exec(fav_accommodation_statement).all()
        
        enriched_acc = acc_obj.model_dump()
        enriched_acc["is_liked_by"] = fav_accommodation
        # hard code for getting like status from client request
        # atm, user is always id 5
        # implement auth with jwt later
        enriched_acc["is_liked_by_user"] = 5 in fav_accommodation
        accommodation_and_likes.append(enriched_acc)
    
    return TripWithDetails(
        id = trip.id,
        name = trip.name,
        created_by = trip.created_by,
        nb_participants = trip.nb_participants,
        destination = trip.destination,
        start_date = trip.start_date,
        end_date = trip.end_date,
        description = trip.description,
        created_at = trip.created_at,
        updated_at = trip.updated_at,
        participants = participants,
        accommodation = accommodation_and_likes,
    )

@router.post("/accommodation/{accommodation_id}/like")
def like_accommodation_id(
    accommodation_id: int,
    session: Session = Depends(get_session)
    ):
    accommodation = session.exec(select(Accommodation).where(Accommodation.id == accommodation_id)).first()
    if not accommodation:
        raise HTTPException(status_code=404, detail="Accommodation not found")
    
    existing_like = session.exec(
        select(FavoriteAccommodation)
        .where(FavoriteAccommodation.accommodation_id == accommodation_id)
        .where(FavoriteAccommodation.participant_id == 5) # REPLACE BY CURRENT LOGGED IN USER
    ).first()

    if existing_like:
        favorite_accommodation = session.get(FavoriteAccommodation, existing_like.id)
        session.delete(favorite_accommodation)
        session.commit()
        return {"message": "Accommodation disliked", "favorite_accommodation": favorite_accommodation}

    favorite_accommodation = FavoriteAccommodation(
        accommodation_id=accommodation_id,
        participant_id=5 # REPLACE BU CURRENT LOGGED IN USER
    )

    session.add(favorite_accommodation)
    session.commit()
    session.refresh(favorite_accommodation)
    return {"message": "Accommodation liked", "favorite_accommodation": favorite_accommodation}


@router.get("/participants/{trip_id}", response_model=list[Participant])
def read_participants_from_trip_id(*, trip_id: int, session: Session = Depends(get_session)):
    statement = select(Participant).where(Participant.trip_id == trip_id)
    participants = session.exec(statement).all()
    if not participants:
        raise HTTPException(status_code=404, detail="No participants found for this trip")
    return participants



@router.get("/trips", response_model=list[Trip])
def read_trips(session: Session = Depends(get_session)):
    trips = session.exec(select(Trip)).all()
    return trips


@router.post("/trips", response_model=Trip)
def create_trip(trip: Trip, session: Session = Depends(get_session)):
    session.add(trip)
    session.commit()
    session.refresh(trip)
    return trip





@router.get("/activities", response_model=list[Activity])
def read_activities(session: Session = Depends(get_session)):
    activities = session.exec(select(Activity)).all()
    return activities



@router.post("/activities", response_model=Activity)
def create_activity(activity: Activity, session: Session = Depends(get_session)):
    session.add(activity)
    session.commit()
    session.refresh(activity)
    return activity



@router.get("/accommodation", response_model=list[Accommodation])
def read_accommodation(session: Session = Depends(get_session)):
    accommodation = session.exec(select(Accommodation)).all()
    return accommodation

@router.post("/accommodation", response_model=Accommodation)
def create_accommodation(accommodation: Accommodation, session: Session = Depends(get_session)):
    session.add(accommodation)
    session.commit()
    session.refresh(accommodation)
    return accommodation



@router.get("/favorite_accommodation", response_model=list[FavoriteAccommodation])
def read_favorite_accommodation(session: Session = Depends(get_session)):
    favorite_accommodation = session.exec(select(FavoriteAccommodation)).all()
    return favorite_accommodation

@router.post("/favorite_accommodation", response_model=FavoriteAccommodation)
def create_favorite_accommodation(favorite_accommodation: FavoriteAccommodation, session: Session = Depends(get_session)):
    session.add(favorite_accommodation)
    session.commit()
    session.refresh(favorite_accommodation)
    return favorite_accommodation



@router.get("/participants", response_model=list[Participant])
def read_participants(session: Session = Depends(get_session)):
    participants = session.exec(select(Participant)).all()
    return participants


@router.get("/participants/{trip_id}", response_model=list[Participant])
def read_participants_from_trip_id(*, trip_id: int, session: Session = Depends(get_session)):
    statement = select(Participant).where(Participant.trip_id == trip_id)
    participants = session.exec(statement).all()
    if not participants:
        raise HTTPException(status_code=404, detail="No participants found for this trip")
    return participants


@router.post("/participants", response_model=Participant)
def create_participant(participant: Participant, session: Session = Depends(get_session)):
    session.add(participant)
    session.commit()
    session.refresh(participant)
    return participant



@router.get("/trains", response_model=list[Train])
def read_trains(session: Session = Depends(get_session)):
    trains = session.exec(select(Train)).all()
    return trains

@router.post("/trains", response_model=Train)
def create_train(train: Train, session: Session = Depends(get_session)):
    session.add(train)
    session.commit()
    session.refresh(train)
    return train



@router.get("/flights", response_model=list[Flight])
def read_flights(session: Session = Depends(get_session)):
    flights = session.exec(select(Flight)).all()
    return flights

@router.post("/flights", response_model=Flight)
def create_flight(flight: Flight, session: Session = Depends(get_session)):
    session.add(flight)
    session.commit()
    session.refresh(flight)
    return flight



@router.get("/cars", response_model=list[Car])
def read_cars(session: Session = Depends(get_session)):
    cars = session.exec(select(Car)).all()
    return cars

@router.post("/cars", response_model=Car)
def create_car(car: Car, session: Session = Depends(get_session)):
    session.add(car)
    session.commit()
    session.refresh(car)
    return car