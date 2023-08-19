import React, { useState } from "react";
import "./Login.css"; // Import the CSS file for Login styles
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  var [errorMessage, setErrorMessage] = useState("");
  var isValid = true;

  const handleLogin = (e) => {
    e.preventDefault();
    if (username.trim() === "") {
      setErrorMessage("Username is required");
      isValid = false;
    } else if (password.trim() === "") {
      setErrorMessage("password is required");
      isValid = false;
    }
    // Create the login request payload
    if (isValid) {
      const loginData = {
        username: username,
        password: password,
      };
      console.log(loginData);
      // Send the login request to the backend

      fetch("http://localhost:8081/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginData),
      })
        .then((response) => response.json())
        .then((data) => {
          // Check if login is successful
          if (data.success) {
            // Check if user is of admin role
            if (data.role === "admin") {
              // Redirect to the home page
              sessionStorage.setItem("username", username);
              navigate("/home");
            } else {
              // Display an error message for non-admin users
              setErrorMessage("You must be an admin to login.");
            }
          } else {
            // Display an error message for invalid credentials
            setErrorMessage("Invalid username or password.");
          }
        })
        .catch((error) => {
          // Handle any errors
          console.error(error);
        });
    }
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <h1>Login</h1>
        <form onSubmit={handleLogin} className="login-form">
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="form-control"
            placeholder="Username"
          />

          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="form-control"
            placeholder="Password"
          />
          {errorMessage && <p className="error-message">{errorMessage}</p>}

          <button type="submit" className="login-button">
            Login
          </button>
          {/* <p className="error-message">*Note : Only admins can login</p> */}
        </form>
      </div>
    </div>
  );
};

export default Login;
