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

function ChatUI({ messages, sendMessage, newMessage, setNewMessage }) {
  // Receive props
  const [currentMessage, setCurrentMessage] = useState("");

  const [newMessageCount, setNewMessageCount] = useState(0); // For notifications (optional)

  const handleSendMessage = () => {
    if (newMessage.trim() !== "") {
      // Prevent sending empty messages
      sendMessage(newMessage);
      setNewMessage(""); // Clear the input field. This is crucial!
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter" && !e.shiftKey) {
      // Send only if Enter is pressed without Shift
      e.preventDefault(); // Prevent newline character in the textfield
      handleSendMessage();
    }
  };

  const messageDataForLibrary = messages.map((message) => ({
    position: message.sender === "user" ? "right" : "left",
    type: "text",
    text: message.content,
    date: new Date(message.timestamp),
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
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          onKeyDown={handleKeyDown}
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
