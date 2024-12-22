import AttachFileIcon from "@mui/icons-material/AttachFile";
import {
  Avatar,
  Badge,
  Box,
  Button,
  IconButton,
  TextField,
  Typography,
} from "@mui/material";
import React, { useState } from "react";
import { MessageList } from "react-chat-elements"; // If using react-chat-elements

function ChatUI() {
  const [currentMessage, setCurrentMessage] = useState("");
  const [messages, setMessages] = useState([
    // Initial example messages (replace with your actual message data)
    {
      id: 1,
      position: "right",
      type: "text",
      text: "Hello!",
      date: new Date(),
    },
    {
      id: 2,
      position: "left",
      type: "text",
      text: "Hi there!",
      date: new Date(),
    },
  ]);
  const [newMessageCount, setNewMessageCount] = useState(0); // For notifications (optional)

  const handleSendMessage = () => {
    if (currentMessage.trim() !== "") {
      const newMessage = {
        id: messages.length + 1,
        position: "right", // Or 'left' depending on the sender
        type: "text",
        text: currentMessage,
        date: new Date(),
      };
      setMessages([...messages, newMessage]);
      setCurrentMessage(""); // Clear input field
    }
  };

  // Example using react-chat-elements (adapt your data structure)
  const messageDataForLibrary = messages.map((message) => ({
    position: message.position,
    type: message.type,
    text: message.text,
    date: message.date,
  }));

  return (
    <Box sx={{ display: "flex", flexDirection: "column", height: "500px" }}>
      {/* Chat messages display area */}
      <Box sx={{ flexGrow: 1, overflow: "auto", padding: "10px" }}>
        {/* Using react-chat-elements */}
        <MessageList
          className="message-list"
          lockable={true}
          toBottomHeight={"100%"}
          dataSource={messageDataForLibrary}
        />

        {/* OR: Custom message rendering (if not using the library)
                {messages.map(message => (
                    <MyMessage key={message.id} message={message} /> // Use your custom message component
                ))}
                */}
      </Box>

      {/* Input area */}
      <Box sx={{ display: "flex", alignItems: "center", padding: "10px" }}>
        <TextField
          label="Type your message"
          variant="outlined"
          sx={{ flexGrow: 1, marginRight: "10px" }}
          value={currentMessage}
          onChange={(e) => setCurrentMessage(e.target.value)}
          onKeyDown={(e) => {
            // Allow sending with Enter key
            if (e.key === "Enter") {
              handleSendMessage();
            }
          }}
        />
        <IconButton>
          <AttachFileIcon />
        </IconButton>
        <Button variant="contained" color="primary" onClick={handleSendMessage}>
          Send
        </Button>
      </Box>

      {/* Optional: Notification Badge */}
      {newMessageCount > 0 && (
        <Badge badgeContent={newMessageCount} color="error">
          <Box
            sx={{ height: "20px", width: "20px", backgroundColor: "lightgray" }}
          >
            {/* Placeholder for icon */}
          </Box>
        </Badge>
      )}
    </Box>
  );
}

// Custom message component (if not using react-chat-elements)
const MyMessage = ({ message }) => (
  <Box
    sx={{
      display: "flex",
      alignItems: "flex-start",
      marginBottom: "10px",
      justifyContent: message.position === "right" ? "flex-end" : "flex-start",
    }}
  >
    {message.position === "left" && <Avatar>User</Avatar>}
    <Box
      sx={{
        backgroundColor: message.position === "right" ? "#DCF8C6" : "#fff",
        padding: "10px",
        borderRadius: "10px",
        marginLeft: message.position === "left" ? "10px" : 0,
        marginRight: message.position === "right" ? "10px" : 0,
      }}
    >
      <Typography variant="body1">{message.text}</Typography>
      <Typography variant="caption">
        {message.date.toLocaleTimeString()}
      </Typography>
    </Box>
    {message.position === "right" && <Avatar>Me</Avatar>}
  </Box>
);

export default ChatUI;
