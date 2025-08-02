import React, { useState } from "react";
import axios from "axios";

function CreateAppointment({ onCreated }) {
  const [customerName, setCustomerName] = useState("");
  const [serviceType, setServiceType] = useState("");
  const [appointmentDate, setAppointmentDate] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const newAppointment = { customerName, serviceType, appointmentDate };
      await axios.post("http://localhost:8080/api/appointments", newAppointment);
      alert("Appointment created!");
      setCustomerName("");
      setServiceType("");
      setAppointmentDate("");
      if (onCreated) onCreated(); // callback to notify creation
    } catch (error) {
      console.error("Error creating appointment:", error);
      alert("Failed to create appointment.");
    }
  };

  return (
    <div
      style={{
        maxWidth: "500px",
        margin: "auto",
        backgroundColor: "rgba(0, 0, 0, 0.6)",
        padding: "20px",
        borderRadius: "10px",
        color: "white",
        boxShadow: "0 0 10px rgba(0,0,0,0.5)",
      }}
    >
      <h2 style={{ textAlign: "center", marginBottom: "1rem" }}>Create Appointment</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: "10px" }}>
          <label>Customer Name:</label><br />
          <input
            type="text"
            value={customerName}
            onChange={(e) => setCustomerName(e.target.value)}
            required
            style={{ width: "100%", padding: "8px", borderRadius: "4px", border: "none" }}
          />
        </div>
        <div style={{ marginBottom: "10px" }}>
          <label>Service Type:</label><br />
          <input
            type="text"
            value={serviceType}
            onChange={(e) => setServiceType(e.target.value)}
            required
            style={{ width: "100%", padding: "8px", borderRadius: "4px", border: "none" }}
          />
        </div>
        <div style={{ marginBottom: "10px" }}>
          <label>Appointment Date:</label><br />
          <input
            type="date"
            value={appointmentDate}
            onChange={(e) => setAppointmentDate(e.target.value)}
            required
            style={{ width: "100%", padding: "8px", borderRadius: "4px", border: "none" }}
          />
        </div>
        <button
          type="submit"
          style={{
            width: "100%",
            backgroundColor: "#007bff",
            color: "white",
            padding: "10px",
            borderRadius: "5px",
            border: "none",
            cursor: "pointer",
          }}
        >
          Create
        </button>
      </form>
    </div>
  );
}

export default CreateAppointment;
