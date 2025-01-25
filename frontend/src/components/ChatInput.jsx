import React, { useState } from "react";
import { Box, IconButton } from "@mui/material";
import { Send } from "@mui/icons-material";
import { Input } from "react-chat-elements";
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

  return (
    <Box p={1} borderTop="1px solid #e0e0e0">
      <Input
        placeholder="Type a message"
        multiline={true}
        value={inputValue}
        rightButtons={
          <IconButton
            color="primary"
            aria-label="send"
            onClick={handleSendMessage}
          >
            <Send />
          </IconButton>
        }
        onChange={(e) => setInputValue(e.target.value)}
        onPressEnter={handleSendMessage}
      />
    </Box>
  );
}

export default ChatInput;
