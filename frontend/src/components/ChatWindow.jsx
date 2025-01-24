// components/ChatWindow.js
import React, { useEffect, useState } from "react";
import {
  Grid,
  Typography,
  TextField,
  Button,
  Box,
  Paper,
  List,
  ListItem,
  ListItemText,
  Divider,
  IconButton,
} from "@mui/material";
import { MessageList as RCEMessageList } from "react-chat-elements";
import { Send as SendIcon } from "@mui/icons-material";
import ChatHeader from "./ChatHeader";
import MessageList from "./MessageList";
import ChatInput from "./ChatInput";
import { useStomp } from "../context/StompContext";

const ChatWindow = ({ selectedWorkspace }) => {
  const { subscribe } = useStomp();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");

  const handleKeyDown = (e) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      handleSendMessage();
    }
  };

  const handleSendMessage = () => {
    if (newMessage.trim() !== "") {
      // client.publish({
      //   destination: `/app/workspaces/${currentWorkspace.id}/sendMessage`,
      //   body: JSON.stringify({
      //     sender: "user",
      //     content: newMessage,
      //   }),
      // });
      setNewMessage("");
    }
  };

  // useEffect(() => {
  //   if (selectedWorkspace) {
  //     const unsubscribe = subscribe(
  //       `/topic/workspaces/${selectedWorkspace.id}/messages`,
  //       (message) => {
  //         setMessages((prevMessages) => [...prevMessages, message]);
  //       }
  //     );
  //     return () => unsubscribe();
  //   }
  // }, [selectedWorkspace, subscribe]);

  return (
    <Box sx={{ height: "100vh", display: "flex", flexDirection: "column" }}>
      <ChatHeader selectedWorkspace={selectedWorkspace} />
      <Box flexGrow={1} style={{ overflowY: "auto" }}>
        <MessageList selectedWorkspace={selectedWorkspace} />
      </Box>
      <ChatInput
        selectedWorkspace={selectedWorkspace}
        onSendMessage={(message) => /* Will be handled in ChatInput */ null}
      />
    </Box>
  );
};

export default ChatWindow;
