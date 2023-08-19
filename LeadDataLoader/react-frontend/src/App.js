import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LeadList from "./Components/LeadList";
import Login from "./Components/Login";
import UploadTracker from "./Components/UploadTracker";
import UploadLeads from "./Components/UploadLeads";

const AppRoutes = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/home" element={<LeadList />} />
        <Route path="/upload" element={<UploadLeads />} />
        <Route path="/tracker" element={<UploadTracker />} />
      </Routes>
    </Router>
  );
};

export default AppRoutes;
