import React, { useState } from "react";
import TopBar from "./TopBar";
import LeftSidebar from "./LeftSidebar";
import "./UploadLeads.css"; // Import the CSS file for UploadLeads styles

const UploadLeads = () => {
  const [file, setFile] = useState(null);
  const [badRecords, setBadRecords] = useState([]);
  const [showBadRecords, setShowBadRecords] = useState(false); // New state for controlling visibility
  const userName = sessionStorage.getItem("username");

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };
  const handleFormSubmit = (event) => {
    event.preventDefault();
    if (file) {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("username", userName);

      fetch("http://localhost:8081/api/leads/upload", {
        method: "POST",
        body: formData,
      })
        .then((response) => response.json())
        .then((data) => {
          console.log("Upload success:", data);
          setBadRecords(data);
          setShowBadRecords(true); // Show the container3 div
        })
        .catch((error) => {
          console.error("Upload error:", error);
        });
    }
  };

  const handleDownloadBadRecords = () => {
    fetch("http://localhost:8081/api/leads/download-bad-records")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Download failed");
        }
        return response.blob();
      })
      .then((blob) => {
        const url = window.URL.createObjectURL(new Blob([blob]));
        const a = document.createElement("a");
        a.href = url;
        a.download = "bad_records.csv";
        a.click();
      })
      .catch((error) => {
        console.error("Download error:", error);
      });
  };

  return (
    <div className="app-container">
      <TopBar />

      <div className="container">
        <LeftSidebar />
        <div className="container2">
          <p className="title">Upload Leads</p>

          <form className="upload-form" onSubmit={handleFormSubmit}>
            <label htmlFor="fileInput" className="file-label">
              Choose CSV file to upload:
            </label>
            <input
              id="fileInput"
              type="file"
              accept=".csv"
              onChange={handleFileChange}
            />
            <br></br>
            <button type="submit" className="submit-button">
              Upload
            </button>
          </form>
        </div>
        {showBadRecords && ( // Check the showBadRecords state before rendering container3
          <div className="container3">
            <h2>Bad Records</h2>
            <table className="lead-table">
              <thead>
                <tr>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Status</th>
                  <th>Assigned To</th>
                </tr>
              </thead>
              <tbody>
                {badRecords.map((record, index) => (
                  <tr key={index}>
                    <td>{record.firstName}</td>
                    <td>{record.lastName}</td>
                    <td>{record.email}</td>
                    <td>{record.phone}</td>
                    <td>{record.status}</td>
                    <td>{record.assignedTo}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            <br></br>
            <button onClick={handleDownloadBadRecords}>
              Download Bad Records
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default UploadLeads;
