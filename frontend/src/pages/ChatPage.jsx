import AttachFileIcon from "@mui/icons-material/AttachFile";
import { Box, Button, Container, IconButton, TextField } from "@mui/material";
import * as StompJs from "@stomp/stompjs";
import React, { useEffect, useState } from "react";
import { MessageList } from "react-chat-elements";
import "react-chat-elements/dist/main.css";

const client = new StompJs.Client({
  brokerURL: "ws://localhost:8080/ws",
  reconnectDelay: 5000,
  heartbeatIncoming: 4000,
  heartbeatOutgoing: 4000,
});

function ChatPage() {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");

  useEffect(() => {
    client.activate();

    client.onConnect = () => {
      client.subscribe("/topic/newMessage", (message) => {
        const newMessage = JSON.parse(message.body);
        setMessages((prevMessages) => [
          ...prevMessages,
          {
            position: newMessage.sender === "user" ? "right" : "left",
            type: "text",
            text: newMessage.content,
            date: new Date(newMessage.timestamp),
          },
        ]);
      });
    };

    client.onStompError = (frame) => {
      console.error("Broker reported error: ", frame.headers["message"]);
      console.error("Additional details: ", frame.body);
    };

    return () => {
      if (client.active) {
        client.deactivate();
      }
    };
  }, []);

  const handleSendMessage = () => {
    if (newMessage.trim() !== "") {
      client.publish({
        destination: "/app/sendMessage",
        body: JSON.stringify({
          sender: "user",
          content: newMessage,
        }),
      });
      setNewMessage("");
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      handleSendMessage();
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: 2 }}>
      <div style={{ height: "80vh" }}>
        <MessageList
          className="message-list"
          lockable={true}
          toBottomHeight={"100%"}
          dataSource={messages}
        />
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
          <Button
            variant="contained"
            color="primary"
            onClick={handleSendMessage}
          >
            Send
          </Button>
        </Box>
      </div>
    </Container>
  );
}

export default ChatPage;
