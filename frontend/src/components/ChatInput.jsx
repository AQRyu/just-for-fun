import React, { useState } from "react";
import { Box, IconButton, TextField } from "@mui/material";
import { Send } from "@mui/icons-material";
import { useStomp } from "../context/StompContext";
import { useAuth } from "../context/AuthContext";

function ChatInput({ selectedWorkspace }) {
  const [inputValue, setInputValue] = useState("");
  const { sendMessageToChat } = useStomp();
  const { user } = useAuth();

  const handleSendMessage = () => {
    if (inputValue.trim() !== "" && selectedWorkspace) {
      const messagePayload = {
        content: inputValue,
        sender: user.id,
        timestamp: new Date().toISOString(),
      };
      sendMessageToChat(selectedWorkspace.id, messagePayload);
      setInputValue("");
    }
  };

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleKeyDown = (event) => {
    if (event.key === "Enter") {
      event.preventDefault();
      handleSendMessage();
    }
  };

  return (
    <Box p={1} borderTop="1px solid #e0e0e0">
      <Box sx={{ display: "flex", alignItems: "center" }}>
        <TextField
          fullWidth
          placeholder="Type a message"
          variant="outlined"
          value={inputValue}
          onChange={handleInputChange}
          onKeyDown={handleKeyDown}
        />
        <IconButton
          color="primary"
          aria-label="send"
          onClick={handleSendMessage}
          sx={{ ml: 1 }}
        >
          <Send />
        </IconButton>
      </Box>
    </Box>
  );
}

export default ChatInput;
