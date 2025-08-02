import React, { useEffect, useState } from "react";
import axios from "axios";

function AppointmentList() {
  const [appointments, setAppointments] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [editData, setEditData] = useState({
    customerName: "",
    serviceType: "",
    appointmentDate: "",
  });

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchAppointments = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/appointments");
      setAppointments(response.data);
    } catch (error) {
      console.error("Error fetching appointments:", error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/appointments/${id}`);
      fetchAppointments(); // Refresh the list
    } catch (error) {
      console.error("Error deleting appointment:", error);
    }
  };

  const handleEditClick = (appointment) => {
    setEditingId(appointment.id);
    setEditData({
      customerName: appointment.customerName || "",
      serviceType: appointment.serviceType || "",
      appointmentDate: appointment.appointmentDate || "",
    });
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setEditData({ ...editData, [name]: value });
  };

  const handleUpdate = async () => {
    try {
      await axios.put(`http://localhost:8080/api/appointments/${editingId}`, editData);
      setEditingId(null);
      fetchAppointments();
    } catch (error) {
      console.error("Error updating appointment:", error);
    }
  };

  return (
    <div
      style={{
        maxWidth: "700px",
        margin: "auto",
        backgroundColor: "rgba(0, 0, 0, 0.6)",
        padding: "20px",
        borderRadius: "10px",
        color: "white",
        boxShadow: "0 0 10px rgba(0,0,0,0.5)",
      }}
    >
      <h2 style={{ textAlign: "center", marginBottom: "1rem" }}>Appointments</h2>

      {appointments.length === 0 ? (
        <p>No appointments found.</p>
      ) : (
        <>
          <div style={{ display: "flex", fontWeight: "bold", borderBottom: "2px solid white", paddingBottom: "10px" }}>
            <div style={{ flex: 1 }}>Customer Name</div>
            <div style={{ flex: 1 }}>Service</div>
            <div style={{ flex: 1 }}>Date</div>
            <div style={{ flex: 2 }}>Actions</div>
          </div>

          <ul style={{ listStyleType: "none", padding: 0 }}>
            {appointments.map((app) => (
              <li
                key={app.id}
                style={{
                  display: "flex",
                  alignItems: "center",
                  padding: "10px 0",
                  borderBottom: "1px solid gray",
                }}
              >
                {editingId === app.id ? (
                  <>
                    <input
                      type="text"
                      name="customerName"
                      value={editData.customerName}
                      onChange={handleEditChange}
                      style={{ flex: 1, marginRight: "10px" }}
                    />
                    <input
                      type="text"
                      name="serviceType"
                      value={editData.serviceType}
                      onChange={handleEditChange}
                      style={{ flex: 1, marginRight: "10px" }}
                    />
                    <input
                      type="date"
                      name="appointmentDate"
                      value={editData.appointmentDate}
                      onChange={handleEditChange}
                      style={{ flex: 1, marginRight: "10px" }}
                    />
                    <button onClick={handleUpdate} style={{ marginRight: "10px" }}>Save</button>
                    <button onClick={() => setEditingId(null)}>Cancel</button>
                  </>
                ) : (
                  <>
                    <div style={{ flex: 1 }}>{app.customerName}</div>
                    <div style={{ flex: 1 }}>{app.serviceType}</div>
                    <div style={{ flex: 1 }}>{app.appointmentDate}</div>
                    <div style={{ flex: 2 }}>
                      <button onClick={() => handleEditClick(app)} style={{ marginRight: "10px" }}>
                        Edit
                      </button>
                      <button onClick={() => handleDelete(app.id)}>Delete</button>
                    </div>
                  </>
                )}
              </li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
}

export default AppointmentList;


