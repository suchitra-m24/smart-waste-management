# Smart Waste Management System 🌍
A civic tech solution for waste reporting
built with Spring Boot + React
## ✨ Features
- 📍 Report garbage with GPS map location
- 🚛 Collectors track complaints on map
- 🌟 Eco points reward system
- 🎁 Redeem rewards with eco points
- 📊 Admin analytics dashboard
- 🏆 Eco champions leaderboard
- 📈 Live statistics
## 👥 User Roles
- **Citizen** → Report waste, earn points, redeem rewards
- **Collector** → View assignments on map, mark collected
- **Admin** → Analytics, assign collectors, manage rewards
## 🛠️ Tech Stack
### Backend
- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Hibernate / JPA
- MySQL
### Frontend
- React 18
- Bootstrap 5
- Leaflet.js (Maps)
- Chart.js (Analytics)
- Axios
  
## 📁 Project Structure
smart-waste/-
├── backend/
│   └── src/main/java/com/smartwaste/
│       ├── controller/
│       │   ├── AuthController.java
│       │   ├── ComplaintController.java
│       │   ├── RewardController.java
│       │   ├── UserController.java
│       │   └── PhotoController.java
│       ├── model/
│       │   ├── User.java
│       │   ├── Complaint.java
│       │   ├── Reward.java
│       │   └── Redemption.java
│       ├── repository/
│       ├── service/
│       └── security/
│
└── frontend/
    └── src/
        ├── pages/
        │   ├── citizen/
        │   │   ├── CitizenDashboard.jsx
        │   │   ├── ReportWaste.jsx
        │   │   └── MyComplaints.jsx
        │   ├── collector/
        │   │   └── CollectorDashboard.jsx
        │   ├── admin/
        │   │   ├── AdminDashboard.jsx
        │   │   ├── ManageComplaints.jsx
        │   │   └── ManageRewards.jsx
        │   ├── Home.jsx
        │   ├── Login.jsx
        │   └── Register.jsx
        ├── components/
        │   └── Navbar.jsx
        └── services/
            └── api.js
            
## ⚙️ Setup
### Backend
1. Create MySQL database: smart_waste_db
2. Update application.properties
3. Run SmartWasteBackendApplication.java
4. API runs on http://localhost:8080
### Frontend
1. cd frontend
2. npm install
3. npm start
4. App runs on http://localhost:3000
## 📡 API Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/auth/register | Register |
| POST | /api/auth/login | Login |
| POST | /api/complaints/report | Report waste |
| GET | /api/complaints/all | All complaints |
| PUT | /api/complaints/{id}/assign/{cId} | Assign |
| PUT | /api/complaints/{id}/status | Update status |
| GET | /api/complaints/stats | Statistics |
| GET | /api/rewards/all | All rewards |
| POST | /api/rewards/redeem/{id} | Redeem reward |
| GET | /api/users/leaderboard | Leaderboard |

## 👩‍💻 Developer
**Suchitra M** — Full Stack Developer
