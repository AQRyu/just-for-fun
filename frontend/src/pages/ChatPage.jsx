import { Button, Grid2, Paper, TextField, Typography } from "@mui/material";
import * as StompJs from "@stomp/stompjs";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { ChatList, MessageList } from "react-chat-elements";
import "react-chat-elements/dist/main.css";

const client = new StompJs.Client({
  brokerURL: "ws://localhost:8080/ws",
  reconnectDelay: 5000,
  heartbeatIncoming: 4000,
  heartbeatOutgoing: 4000,
});

function ChatPage() {
  const [workspaces, setWorkspaces] = useState([]);
  const [currentWorkspace, setCurrentWorkspace] = useState(null);

  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");

  useEffect(() => {
    const fetchWorkspaces = async () => {
      const token = localStorage.getItem("user");
      const response = await axios.get(
        `${process.env.REACT_APP_BACKEND_URL}/api/workspaces`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setWorkspaces(response.data);
    };
    fetchWorkspaces();

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
    <Grid2 container height="90vh">
      {/* Sidebar */}
      <Grid2 item size={3} component={Paper} elevation={3}>
        <Typography variant="h6" textAlign="center" mt={2}>
          Workspaces
        </Typography>
        <div
          style={{
            height: "70vh",
          }}
        >
          <ChatList
            className="workspace-list"
            dataSource={workspaces.map((workspace) => ({
              avatar: "https://via.placeholder.com/50",
              alt: workspace.name,
              title: workspace.name,
              subtitle: workspace.description || "No description available",
              date: new Date(),
              unread: 0,
              id: workspace.id,
            }))}
            onClick={(item) => {
              const selectedWorkspace = workspaces.find(
                (w) => w.id === item.id
              );
              setCurrentWorkspace(selectedWorkspace);
              setMessages([]);
            }}
          />
        </div>
      </Grid2>
      {/* Chat Area */}
      <Grid2 size={9}>
        <Typography variant="h6" textAlign="center" mt={2}>
          {currentWorkspace?.name || "Select a Workspace"}
        </Typography>

        <div
          style={{
            height: "80%",
            padding: "10px",
            overflowY: "auto",
            backgroundColor: "#f5f5f5",
          }}
        >
          {/* Messages */}
          <MessageList
            className="message-list"
            lockable={true}
            toBottomHeight={"100%"}
            dataSource={messages}
          />
        </div>

        {/* Input Field */}
        <div style={{ display: "flex", alignItems: "center", padding: "10px" }}>
          <TextField
            fullWidth
            placeholder="Type a message..."
            sx={{ flexGrow: 1, marginRight: "10px" }}
            variant="outlined"
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
            onKeyDown={handleKeyDown}
          />
          <Button
            variant="contained"
            color="primary"
            style={{ marginLeft: "10px" }}
            onClick={handleSendMessage}
          >
            Send
          </Button>
        </div>
      </Grid2>
    </Grid2>
    // <Container maxWidth="sm" sx={{ mt: 2 }}>
    //   <div style={{ height: "80vh" }}>
    //     <MessageList
    //       className="message-list"
    //       lockable={true}
    //       toBottomHeight={"100%"}
    //       dataSource={messages}
    //     />
    //     <Box sx={{ display: "flex", alignItems: "center", padding: "10px" }}>
    //       <TextField
    //         label="Type your message"
    //         variant="outlined"
    //         sx={{ flexGrow: 1, marginRight: "10px" }}
    //         value={newMessage}
    //         onChange={(e) => setNewMessage(e.target.value)}
    //         onKeyDown={handleKeyDown}
    //       />
    //       <IconButton>
    //         <AttachFileIcon />
    //       </IconButton>
    //       <Button
    //         variant="contained"
    //         color="primary"
    //         onClick={handleSendMessage}
    //       >
    //         Send
    //       </Button>
    //     </Box>
    //   </div>
    // </Container>
  );
}

export default ChatPage;
