import { Box, Button, Container, TextField, Typography } from "@mui/material";
import React, { useState } from "react";
import api from "../context/api";

function CreateWorkspacePage() {
  const [workspaceName, setWorkspaceName] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const response = await api.post(`/api/workspaces`, {
        name: workspaceName,
      });

      console.log("Workspace created:", response.data);
    } catch (error) {
      if (error.response) {
        setError(error.response.data.message || "Failed to create workspace");
        console.error(
          "Error creating workspace (server responded):",
          error.response.data
        );
      } else if (error.request) {
        setError("An error occurred. Please try again later.");
        console.error("Error creating workspace (no response):", error.request);
      } else {
        setError("An error occurred. Please try again later.");
        console.error("Error creating workspace (setup):", error.message);
      }
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
