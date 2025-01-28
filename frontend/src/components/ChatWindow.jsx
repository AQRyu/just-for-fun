// components/ChatWindow.js
import React, { useCallback, useEffect, useRef, useState } from "react";
import { Box } from "@mui/material";
import ChatHeader from "./ChatHeader";
import MessageList from "./MessageList";
import ChatInput from "./ChatInput";
import api from "../context/api";
import { useStomp } from "../context/StompContext";

const ChatWindow = ({ selectedWorkspace }) => {
  const [chatMessages, setChatMessages] = useState([]);
  const { subscribeToChat, unsubscribeFromChat } = useStomp();
  const messageListRef = useRef(null); // Create a ref for the MessageList container

  const onMessageReceived = useCallback((data) => {
    try {
      setChatMessages((prevMessages) => [
        ...prevMessages,
        {
          content: data.content,
          sender: data.sender,
          timestamp: data.timestamp,
        },
      ]);
    } catch (error) {
      console.error("Error parsing incoming message:", error, data);
    }
  }, []);

  useEffect(() => {
    let subscription;

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

      subscription = subscribeToChat(selectedWorkspace.id, onMessageReceived);
    }

    return () => {
      setChatMessages([]);
      if (subscription) {
        unsubscribeFromChat(selectedWorkspace?.id);
      }
    };
  }, [
    selectedWorkspace,
    onMessageReceived,
    subscribeToChat,
    unsubscribeFromChat,
  ]);

  useEffect(() => {
    if (messageListRef.current) {
      messageListRef.current.scrollTop = messageListRef.current.scrollHeight;
    }
  }, [chatMessages]);

  return (
    <Box sx={{ height: "100vh", display: "flex", flexDirection: "column" }}>
      <ChatHeader selectedWorkspace={selectedWorkspace} />
      <Box ref={messageListRef} flexGrow={1} style={{ overflowY: "auto" }}>
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
