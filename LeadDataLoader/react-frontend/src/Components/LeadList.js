import React, { useEffect, useState } from "react";
import "./LeadList.css"; // Import the CSS file for LeadList styles
import LeftSidebar from "./LeftSidebar";
import TopBar from "./TopBar";

const LeadList = () => {
  const [leads, setLeads] = useState([]);

  useEffect(() => {
    // Fetch leads data from the API
    fetch("http://localhost:8081/api/leads")
      .then((response) => response.json())
      .then((data) => {
        setLeads(data);
      })
      .catch((error) => {
        console.error("Error fetching leads:", error);
      });
  }, []);

  // Define table columns and data
  const columns = [
    { Header: "ID", accessor: "id" },
    { Header: "First Name", accessor: "firstName" },
    { Header: "Last Name", accessor: "lastName" },
    { Header: "Email", accessor: "email" },
    { Header: "Phone", accessor: "phone" },
    { Header: "Status", accessor: "status" },
    { Header: "Assigned To", accessor: "assignedTo" },
  ];

  // Sample lead data

  return (
    <div className="app-container">
      <TopBar />
      <div className="container">
        <LeftSidebar />
        <div className="container2">
          <h1 className="title">Lead List</h1>
          <br></br>

          <table className="lead-table">
            <thead>
              <tr>
                {columns.map((column) => (
                  <th key={column.Header}>{column.Header}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {leads.map((lead) => (
                <tr key={lead.id}>
                  {columns.map((column) => (
                    <td key={column.Header}>{lead[column.accessor]}</td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default LeadList;
