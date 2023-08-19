import React from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSignOutAlt } from "@fortawesome/free-solid-svg-icons";
import "./TopBar.css";

const TopBar = () => {
  return (
    <div className="top-bar">
      <div className="app-name">LeadDataLoader</div>
      <Link to={"/"} className="logout-button">
        <FontAwesomeIcon icon={faSignOutAlt} className="logout-icon" />
        Logout
      </Link>
    </div>
  );
};

export default TopBar;
