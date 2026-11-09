from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import joblib

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=[
        "http://127.0.0.1:5500",
        "http://localhost:5500"
    ],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)

energy_model = joblib.load("energy_model.pkl")
focus_model = joblib.load("focus_model.pkl")
wellbeing_model = joblib.load("wellbeing_model.pkl")
productivity_model = joblib.load("productivity_model.pkl")


@app.get("/")
def home():
    return {
        "message": "Human Digital Twin AI Service is working!"
    }


@app.post("/predict")
def predict(data: dict):

    input_data = [[
        data["activity_minutes"],
        data["habit_done"],
        data["mood"],
        data["stress"],
        data["sleep_hours"]
    ]]

    energy = energy_model.predict(input_data)[0]
    focus = focus_model.predict(input_data)[0]
    wellbeing = wellbeing_model.predict(input_data)[0]
    productivity = productivity_model.predict(input_data)[0]

    return {
        "predicted_energy": round(float(energy), 2),
        "predicted_focus": round(float(focus), 2),
        "predicted_wellbeing": round(float(wellbeing), 2),
        "predicted_productivity": round(float(productivity), 2)
    }