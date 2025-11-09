# Weather Service - Real-Time Weather Updates

A real-time weather service application built with WebSocket technology. This project demonstrates Service-Oriented Architecture (SOA) principles with a Java WebSocket server and a web-based client dashboard.

## 🌟 Features

- **Real-time Weather Updates**: Get live weather data updates every 5 seconds via WebSocket
- **Multi-client Support**: Multiple clients can connect simultaneously
- **Dynamic City Selection**: Users can request weather data for any city/country
- **Interactive Dashboard**: Clean, modern web interface for displaying weather information
- **RESTful API Integration**: Fetches data from WeatherAPI.com

## 🛠️ Tech Stack

### Server

- **Java 17/23**
- **Spring Boot 3.5.7**
- **Tyrus WebSocket Server** (JSR-356 implementation)
- **Maven** (Build tool)
- **WeatherAPI.com** (External weather data provider)

### Client

- **HTML5**
- **CSS3**
- **JavaScript** (Vanilla JS with WebSocket API)

## 📋 Prerequisites

- **Java 17 or higher** (JDK)
- **Maven 3.6+**
- **WeatherAPI.com API Key** ([Get one here](https://www.weatherapi.com/))
- A modern web browser with WebSocket support

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd ds
```

### 2. Configure API Key

Create a `.env` file in the `server` directory:

```bash
cd server
```

Create `.env` file with your WeatherAPI.com key:

```
WEATHER_API_KEY=your_api_key_here
```

### 3. Build the Server

```bash
cd server
mvn clean install
```

### 4. Run the Server

```bash
mvn spring-boot:run
```

Or run the main class directly:

```bash
java -cp target/classes org.example.soa_ds.WeatherServer
```

The server will start on `ws://localhost:8080/ws/weather`

### 5. Open the Client

Open `client/dashboard.html` in your web browser, or use a local web server:

```bash
# Using Python
cd client
python -m http.server 8000

# Then navigate to http://localhost:8000/dashboard.html
```

## 📖 Usage

1. **Start the server** (see steps above)
2. **Open the client dashboard** in your browser
3. **Enter a city or country name** in the input field
4. **Click "Get Updates"** to start receiving real-time weather data
5. Weather information will update automatically every 5 seconds

## 📁 Project Structure

```
ds/
├── client/
│   ├── dashboard.html    # Main client interface
│   ├── main.css          # Stylesheet
│   ├── main.js           # WebSocket client logic
│   └── dummy.json        # Sample data
│
├── server/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── org/example/soa_ds/
│   │       │       ├── WeatherServer.java      # WebSocket server main class
│   │       │       ├── WeatherEndPoint.java    # WebSocket endpoint handler
│   │       │       └── WeatherThread.java      # Weather API polling thread
│   │       └── resources/
│   │           └── application.properties      # Spring Boot configuration
│   ├── pom.xml           # Maven dependencies
│   └── .env              # API key configuration (create this)
│
└── README.md
```

## 🔧 Configuration

### Server Port

The server runs on port `8080` by default. To change it, modify `WeatherServer.java`:

```java
public static Server server = new Server("localhost", 8080, "/ws", null, WeatherEndPoint.class);
```

### Update Frequency

Weather data is fetched every 5 seconds. To change this, modify `WeatherThread.java`:

```java
Thread.sleep(5000); // Change this value (in milliseconds)
```

## 🌐 API Endpoints

- **WebSocket Endpoint**: `ws://localhost:8080/ws/weather`
  - **On Open**: Client connects and receives a unique ID
  - **On Message**: Server receives city name and starts fetching weather data
  - **On Close**: Client disconnects

## 📝 Notes

- The server supports multiple concurrent WebSocket connections
- Weather data is broadcasted to all connected clients
- The API key must be stored in a `.env` file in the server directory
- Make sure `.env` is in `.gitignore` (it should be automatically excluded)

## 🤝 Contributing

This is a school project. For questions or issues, please contact the project owner.

## 📄 License

This project is for educational purposes only.

## 🙏 Acknowledgments

- Weather data provided by [WeatherAPI.com](https://www.weatherapi.com/)
- Built with Spring Boot and Tyrus WebSocket Server
