import pandas as pd
import joblib
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_absolute_error

data = pd.read_csv("training_data.csv")

features = [
    "activity_minutes",
    "habit_done",
    "mood",
    "stress",
    "sleep_hours"
]

targets = [
    "energy",
    "focus",
    "wellbeing",
    "productivity"
]

X = data[features]

for target in targets:
    y = data[target]

    X_train, X_test, y_train, y_test = train_test_split(
        X,
        y,
        test_size=0.2,
        random_state=42
    )

    model = RandomForestRegressor(
        n_estimators=100,
        random_state=42
    )

    model.fit(X_train, y_train)

    predictions = model.predict(X_test)

    error = mean_absolute_error(y_test, predictions)

    joblib.dump(model, f"{target}_model.pkl")

    print(f"{target.capitalize()} model trained successfully!")
    print(f"Mean Absolute Error: {round(error, 2)}")
    print(f"Model saved as {target}_model.pkl")
    print()

print("All ML models trained successfully!")