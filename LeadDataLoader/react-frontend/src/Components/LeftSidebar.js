import React from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faHome,
  faFileUpload,
  faHistory,
} from "@fortawesome/free-solid-svg-icons";
import "./LeftSidebar.css";

const LeftSidebar = () => {
  return (
    <div className="left-sidebar">
      <Link to="/home" className="sidebar-item">
        <FontAwesomeIcon icon={faHome} className="sidebar-icon" />
        <span className="sidebar-text">Home</span>
      </Link>
      <Link to="/upload" className="sidebar-item">
        <FontAwesomeIcon icon={faFileUpload} className="sidebar-icon" />
        <span className="sidebar-text">Upload Leads</span>
      </Link>
      <Link to="/tracker" className="sidebar-item">
        <FontAwesomeIcon icon={faHistory} className="sidebar-icon" />
        <span className="sidebar-text">History</span>
      </Link>
      <div className="sidebar-divider"></div>
      {/* Add more sidebar items if needed */}
    </div>
  );
};

export default LeftSidebar;
