from contextlib import asynccontextmanager
from fastapi import FastAPI
from .routes import router
from .database import engine

from fastapi import APIRouter, Depends, HTTPException
from sqlmodel import Session, select
from .database import get_session
from .models import *
from fastapi.middleware.cors import CORSMiddleware


@asynccontextmanager
async def lifespan(app: FastAPI):
    SQLModel.metadata.create_all(engine)
    yield

app = FastAPI(title="Mon API FastAPI avec SQLModel et Supabase")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3000"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Créer les tables dans la base de données au démarrage
# DEPRECATED -> lifespan func
# @app.on_event("startup")
# def on_startup():
#     SQLModel.metadata.create_all(engine)

# Inclure les routes
app.include_router(router)

@app.get("/")
def read_root():
    return {"message": "Bienvenue sur mon API"}

