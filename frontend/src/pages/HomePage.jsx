import { Button, Container, Typography } from "@mui/material";
import React from "react";
import { useNavigate } from "react-router-dom";

export default function HomePage() {
  const navigate = useNavigate();

  const handleNavigateToChat = () => {
    navigate("/chat");
  };

  return (
    <Container maxWidth="sm" sx={{ mt: "20vh", textAlign: "center" }}>
      <Typography variant="h4" gutterBottom>
        Welcome to the Chat App!
      </Typography>
      <Button
        variant="contained"
        color="primary"
        size="large"
        onClick={handleNavigateToChat}
      >
        Enter Chat
      </Button>
    </Container>
  );
}
