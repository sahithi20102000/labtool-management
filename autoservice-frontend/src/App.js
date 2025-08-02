import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import CreateAppointment from "./CreateAppointment";
import AppointmentList from "./AppointmentList";
import Home from "./Home";

function App() {
  return (
    <Router>
      <div
        style={{
          backgroundImage:
            "url(https://i.etsystatic.com/12739419/r/il/69ad37/3191744763/il_794xN.3191744763_ij0i.jpg)",
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
          minHeight: "100vh",
          padding: "0",
          margin: "0",
        }}
      >
        {/* DARK OVERLAY to make white text more visible */}
        <div
          style={{
            backgroundColor: "rgba(0, 0, 0, 0.6)", // Dark semi-transparent overlay
            minHeight: "100vh",
            color: "white",
            padding: "2rem",
            fontFamily: "Arial, sans-serif",
          }}
        >
          <header
            style={{
              textAlign: "center",
              marginBottom: "2rem",
              textShadow: "2px 2px 6px #000",
            }}
          >
            <h1>AutoService Manager</h1>
            <nav style={{ marginTop: "1rem" }}>
              <Link
                to="/"
                style={{
                  color: "white",
                  marginRight: "1rem",
                  textDecoration: "underline",
                }}
              >
                Home
              </Link>
              <Link
                to="/create"
                style={{
                  color: "white",
                  marginRight: "1rem",
                  textDecoration: "underline",
                }}
              >
                Create Appointment
              </Link>
              <Link
                to="/appointments"
                style={{ color: "white", textDecoration: "underline" }}
              >
                Show Appointments
              </Link>
            </nav>
          </header>

          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/create" element={<CreateAppointment />} />
            <Route path="/appointments" element={<AppointmentList />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;







