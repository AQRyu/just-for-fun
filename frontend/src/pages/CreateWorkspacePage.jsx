import { Box, Button, Container, TextField, Typography } from "@mui/material";
import React, { useState } from "react";

function CreateWorkspacePage() {
  const [workspaceName, setWorkspaceName] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const token = localStorage.getItem("user");

      const response = await fetch(
        `${process.env.REACT_APP_BACKEND_URL}/api/workspaces`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({ name: workspaceName }),
        }
      );

      if (response.ok) {
        const data = await response.json();
        console.log("Workspace created:", data);
      } else {
        const errorData = await response.json();
        setError(errorData.message || "Failed to create workspace");
      }
    } catch (error) {
      console.error("Error creating workspace:", error);
      setError("An error occurred. Please try again later.");
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: 8 }}>
      {" "}
      {/* Centers content and adds top margin */}
      <Typography variant="h4" align="center" gutterBottom>
        Create New Workspace
      </Typography>
      <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
        {" "}
        {/* Form with Material UI styling */}
        <TextField
          margin="normal"
          required
          fullWidth
          id="workspaceName"
          label="Workspace Name"
          name="workspaceName"
          autoFocus
          value={workspaceName}
          onChange={(e) => setWorkspaceName(e.target.value)}
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
        >
          Create Workspace
        </Button>
      </Box>
    </Container>
  );
}

export default CreateWorkspacePage;
