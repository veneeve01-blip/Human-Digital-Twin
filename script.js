
let twinData = null;

async function loadTwinData() {
    const userId = localStorage.getItem("userId");

    if (!userId) {
        console.log("No user ID found.");
        return;
    }

    try {
        const response = await fetch(
            `http://localhost:8080/api/twin/${userId}`
        );

        if (!response.ok) {
            throw new Error("Could not load twin data.");
        }

        twinData = await response.json();

        console.log("Twin data loaded:", twinData);

        const userNameElement = document.getElementById("userName");

        if (twinData.user && userNameElement) {
            userNameElement.textContent = twinData.user.name;
        }

        updateDashboard();
        updateActivityChart();
    } catch (error) {
        console.error("Twin data error:", error);
    }
}

function updateDashboard() {
    if (!twinData) return;

    const activities = twinData.activities || [];
    const habits = twinData.habits || [];
    const moods = twinData.moods || [];
    const sleep = twinData.sleep || [];

    let energyScore = 50;

    if (moods.length > 0) {
        const latestMood = moods[moods.length - 1];

        if (latestMood.energyLevel === "High") {
            energyScore = 90;
        } else if (latestMood.energyLevel === "Medium") {
            energyScore = 70;
        } else if (latestMood.energyLevel === "Low") {
            energyScore = 40;
        }
    }

    let focusScore = 50;

    if (moods.length > 0) {
        const latestMood = moods[moods.length - 1];

        if (latestMood.stressLevel === "Low") {
            focusScore = 85;
        } else if (latestMood.stressLevel === "Medium") {
            focusScore = 65;
        } else if (latestMood.stressLevel === "High") {
            focusScore = 40;
        }
    }

    let wellbeingScore = 50;

    if (moods.length > 0) {
        const latestMood = moods[moods.length - 1];

        if (latestMood.mood === "Happy") {
            wellbeingScore = 90;
        } else if (latestMood.mood === "Neutral") {
            wellbeingScore = 70;
        } else if (
            latestMood.mood === "Sad" ||
            latestMood.mood === "Stressed"
        ) {
            wellbeingScore = 40;
        }
    }

    let productivityScore = 40;

    if (activities.length > 0) {
        productivityScore += 20;
    }

    if (habits.length > 0) {
        productivityScore += 20;
    }

    if (sleep.length > 0) {
        productivityScore += 20;
    }

    productivityScore = Math.min(productivityScore, 100);

    const energyValue = document.getElementById("energyValue");
    const energyText = document.getElementById("energyText");
    const focusValue = document.getElementById("focusValue");
    const focusText = document.getElementById("focusText");
    const wellbeingValue = document.getElementById("wellbeingValue");
    const wellbeingText = document.getElementById("wellbeingText");
    const productivityValue = document.getElementById("productivityValue");
    const productivityText = document.getElementById("productivityText");

    if (energyValue) {
        energyValue.textContent = energyScore + "%";
    }

    if (energyText) {
        if (energyScore >= 80) {
            energyText.textContent = "High energy";
        } else if (energyScore >= 60) {
            energyText.textContent = "Moderate energy";
        } else {
            energyText.textContent = "Low energy";
        }
    }

    if (focusValue) {
        focusValue.textContent = focusScore + "%";
    }

    if (focusText) {
        if (focusScore >= 80) {
            focusText.textContent = "Good concentration";
        } else if (focusScore >= 60) {
            focusText.textContent = "Moderate concentration";
        } else {
            focusText.textContent = "Needs improvement";
        }
    }

    if (wellbeingValue) {
        wellbeingValue.textContent = wellbeingScore + "%";
    }

    if (wellbeingText) {
        if (wellbeingScore >= 80) {
            wellbeingText.textContent = "Looking positive";
        } else if (wellbeingScore >= 60) {
            wellbeingText.textContent = "Fairly positive";
        } else {
            wellbeingText.textContent = "Needs attention";
        }
    }

    if (productivityValue) {
        productivityValue.textContent = productivityScore + "%";
    }

    if (productivityText) {
        if (productivityScore >= 80) {
            productivityText.textContent = "Strong progress";
        } else if (productivityScore >= 60) {
            productivityText.textContent = "Good progress";
        } else {
            productivityText.textContent = "Building consistency";
        }
    }

    const stateEnergy = document.getElementById("stateEnergy");
    const stateFocus = document.getElementById("stateFocus");
    const stateActivity = document.getElementById("stateActivity");
    const stateOverall = document.getElementById("stateOverall");

    if (stateEnergy) {
        if (energyScore >= 80) {
            stateEnergy.textContent = "High";
        } else if (energyScore >= 60) {
            stateEnergy.textContent = "Moderate";
        } else {
            stateEnergy.textContent = "Low";
        }
    }

    if (stateFocus) {
        if (focusScore >= 80) {
            stateFocus.textContent = "Good";
        } else if (focusScore >= 60) {
            stateFocus.textContent = "Moderate";
        } else {
            stateFocus.textContent = "Low";
        }
    }

    if (stateActivity) {
        stateActivity.textContent =
            activities.length > 0 ? "Active" : "Inactive";
    }

    if (stateOverall) {
        const overallScore =
            (energyScore + focusScore + wellbeingScore + productivityScore) / 4;

        if (overallScore >= 80) {
            stateOverall.textContent = "Excellent";
        } else if (overallScore >= 60) {
            stateOverall.textContent = "Stable";
        } else {
            stateOverall.textContent = "Needs attention";
        }
    }

    console.log("Dashboard updated using real Twin data.");
    console.log("Energy:", energyScore);
    console.log("Focus:", focusScore);
    console.log("Well-being:", wellbeingScore);
    console.log("Productivity:", productivityScore);
}

function updateActivityChart() {
    if (!twinData) return;

    const activities = twinData.activities || [];
    const bars = document.querySelectorAll(".chart .bar");

    if (!bars.length) return;

    const activityByDay = {
        Mon: 0,
        Tue: 0,
        Wed: 0,
        Thu: 0,
        Fri: 0,
        Sat: 0,
        Sun: 0
    };

    activities.forEach(activity => {
        if (!activity.date) return;

        const activityDate = new Date(activity.date);

        const day = activityDate.toLocaleDateString("en-US", {
            weekday: "short",
            timeZone: "UTC"
        });

        let duration = 30;

        if (activity.duration) {
            const number = parseFloat(activity.duration);

            if (!isNaN(number)) {
                duration = number;
            }
        }

        if (activityByDay[day] !== undefined) {
            activityByDay[day] += duration;
        }
    });

    let maxDuration = 0;

    Object.values(activityByDay).forEach(duration => {
        if (duration > maxDuration) {
            maxDuration = duration;
        }
    });

    bars.forEach(bar => {
        const day = bar.dataset.day;
        const duration = activityByDay[day] || 0;

        let height = 5;

        if (maxDuration > 0) {
            height = (duration / maxDuration) * 100;
            height = Math.max(height, 8);
        }

        bar.style.height = height + "%";
    });

    console.log("Activity chart updated:", activityByDay);
}

function showNotification() {
    const notification = document.getElementById("notification");

    if (!notification) return;

    notification.classList.add("show");

    setTimeout(() => {
        notification.classList.remove("show");
    }, 3000);
}

function showAnalysis() {
    alert(
        "AI Analysis\n\n" +
        "Your recent activity shows a stable pattern. " +
        "Your productivity is expected to remain consistent " +
        "over the next few days."
    );
}

function changePeriod() {
    const select = document.getElementById("periodSelect");

    if (!select) return;

    const selected = select.value;

    if (selected === "Last 30 days") {
        console.log("Showing activity for the last 30 days.");
    } else {
        console.log("Showing activity for the last 7 days.");
    }
}

const navLinks = document.querySelectorAll(".nav-link");

navLinks.forEach(link => {
    link.addEventListener("click", function(event) {
        if (this.getAttribute("href") === "#") {
            event.preventDefault();
        }

        navLinks.forEach(item => {
            item.classList.remove("active");
        });

        if (this.closest(".navigation")) {
            this.classList.add("active");
        }
    });
});

window.addEventListener("load", () => {
    const userName = localStorage.getItem("userName");
    const userNameElement = document.getElementById("userName");

    if (userName && userNameElement) {
        userNameElement.textContent = userName;
    }

    loadTwinData();

    console.log("Human Digital Twin dashboard loaded.");
});
async function loadDashboardHabits() {

    const container = document.getElementById("dashboardHabits");
    const userId = localStorage.getItem("userId");

    if (!container || !userId) return;

    try {
        const response = await fetch(`http://localhost:8080/api/habits/user/${userId}`);

        if (!response.ok) {
            throw new Error("Unable to load habits");
        }

        const habits = await response.json();

        container.innerHTML = "";

        if (habits.length === 0) {
            container.textContent = "No habits added yet. Start building your routine!";
            return;
        }

        const summary = document.createElement("p");
        const completed = habits.filter(habit =>
            habit.status && habit.status.toLowerCase() === "completed"
        ).length;

        summary.textContent = `${completed} of ${habits.length} habits completed`;
        container.appendChild(summary);

        habits.forEach(habit => {
            const item = document.createElement("p");

            item.textContent =
                `${habit.habitName} — ${habit.status}`;

            container.appendChild(item);
        });

    } catch (error) {
        container.textContent = "Unable to load your habits right now.";
    }
}

loadDashboardHabits();