// components/ChatWindow.js
import React, { useEffect, useState } from "react";
import { Box } from "@mui/material";
import ChatHeader from "./ChatHeader";
import MessageList from "./MessageList";
import ChatInput from "./ChatInput";
import api from "../context/api";

const ChatWindow = ({ selectedWorkspace }) => {
  const [chatMessages, setChatMessages] = useState([]);

  useEffect(() => {
    if (selectedWorkspace) {
      const fetchMessages = async () => {
        try {
          const response = await api.get(
            `/api/workspaces/${selectedWorkspace.id}/messages`
          );
          setChatMessages(response.data);
          console.log(response.data);
        } catch (error) {
          console.error("Error fetching messages:", error);
          setChatMessages([]);
        }
      };
      fetchMessages();
    } else {
      setChatMessages([]);
    }
  }, [selectedWorkspace]);

  return (
    <Box sx={{ height: "100vh", display: "flex", flexDirection: "column" }}>
      <ChatHeader selectedWorkspace={selectedWorkspace} />
      <Box flexGrow={1} style={{ overflowY: "auto" }}>
        <MessageList
          selectedWorkspace={selectedWorkspace}
          messages={chatMessages}
        />
      </Box>
      <ChatInput selectedWorkspace={selectedWorkspace} />
    </Box>
  );
};

export default ChatWindow;
