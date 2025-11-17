// 1. Create the socket connection ONCE at the global level.
const socket = new WebSocket("ws://localhost:8080/ws/weather");

// --- WebSocket Event Listeners ---

socket.onopen = () => {
    console.log("Connection established");
    
    // Use the new ID to find the input
    const input = document.querySelector("#city-input").value;
    if (input) {
        socket.send(input);
    }
};

socket.onmessage = (msg) => {
    console.log("Raw data received:", msg.data);
    const data = JSON.parse(msg.data);

    if (data.type === "clientCount") {
        var msgText = "Connected Clients: " + data.count;
        document.getElementById("connected_clients").innerText = msgText;
    } 
    else if (data.location) {
        const loading = document.querySelector(".loading");
        loading.style.display = "none";
        displayData(data);
    } 
    else {
        console.warn("Received unknown message format:", data);
    }
};

socket.onclose = () => {
    console.log("Connection destroyed");
};

// --- Helper Functions ---

function displayData(msg) {
    const div = document.querySelector(".weather");
    div.innerHTML = ""; 

    const items = [
        { title: "Location", value: msg.location.name },
        { title: "Region", value: msg.location.region },
        { title: "Country", value: msg.location.country },
        { title: "Local Time", value: msg.location.localtime },
        { title: "Condition", value: msg.current.condition.text },
    ];

    items.forEach((item) => {
        const card = document.createElement("div");
        card.className = "weather-card";
        card.innerHTML = `<h1>${item.title}</h1><h2>${item.value}</h2>`;
        div.append(card);
    });

    const img = document.createElement("img");
    img.src = msg.current.condition.icon;
    div.lastChild.append(img);
}

/**
 * This function is now correctly called by your button's onclick.
 */
function sendNewCity() {
    document.querySelector(".weather").innerHTML = "";
    const loading = document.querySelector(".loading");
    loading.style.display = "block";

    // Use the new ID to find the input
    const input = document.querySelector("#city-input").value;

    socket.send(input);
}