import {
  Alert,
  Box,
  Button,
  Container,
  TextField,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const LoginPage = () => {
  const { handleLogin } = useAuth();
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const response = await axios.post(
        `${process.env.REACT_APP_BACKEND_URL}/auth/login`,
        { username, password }
      );

      handleLogin(response.data.jwt);
      const redirectPath = localStorage.getItem("redirectPath") || "/";
      localStorage.removeItem("redirectPath");
      navigate(redirectPath);
    } catch (error) {
      if (error.response) {
        setError(error.response.data.message || "Login failed");
        console.error("Login error (server responded):", error.response.data);
      } else if (error.request) {
        setError("An error occurred. Please try again later.");
        console.error("Login error (no response):", error.request);
      } else {
        setError("An error occurred. Please try again later.");
        console.error("Login error (setup):", error.message);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: "20vh", textAlign: "center" }}>
      <Typography variant="h4" gutterBottom>
        Login
      </Typography>
      {error && <Alert severity="error">{error}</Alert>}
      <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
        <TextField
          margin="normal"
          required
          fullWidth
          id="username"
          label="Username"
          name="username"
          autoComplete="username"
          autoFocus
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <TextField
          margin="normal"
          required
          fullWidth
          name="password"
          label="Password"
          type="password"
          id="password"
          autoComplete="current-password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
          disabled={loading}
        >
          {loading ? "Logging in..." : "Login"}
        </Button>
      </Box>
    </Container>
  );
};

export default LoginPage;
