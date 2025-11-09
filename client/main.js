function main() {
  document.querySelector(".weather").innerHTML = "";

  const loading = document.querySelector(".loading");
  loading.style.display = "block"; // show spinner immediately

  const socket = new WebSocket("ws://localhost:8080/ws/weather");

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

  socket.onopen = () => {
    console.log("connection established");
    const input = document.querySelector("input").value;
    socket.send(input);
  };

  socket.onmessage = (msg) => {
    loading.style.display = "none"; // hide spinner once data arrives
    const data = JSON.parse(msg.data);
    displayData(data);
  };

  socket.onclose = () => {
    console.log("connection destroyed");
  };
}
