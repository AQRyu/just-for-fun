import { Grid2 } from "@mui/material";
import React, { useState } from "react";
import "react-chat-elements/dist/main.css";
import Sidebar from "../components/Sidebar";
import ChatWindow from "../components/ChatWindow";
import { StompProvider } from "../context/StompContext";

function ChatPage() {
  const [selectedWorkspace, setSelectedWorkspace] = useState(null);

  return (
    <StompProvider>
      <Grid2 container spacing={0} sx={{ height: "100vh" }}>
        <Grid2 size={4}>
          <Sidebar setSelectedWorkspace={setSelectedWorkspace} />
        </Grid2>
        <Grid2 size={8} sx={{ borderLeft: "1px solid #e0e0e0" }}>
          {selectedWorkspace && (
            <ChatWindow selectedWorkspace={selectedWorkspace} />
          )}
        </Grid2>
      </Grid2>
    </StompProvider>
  );
}

export default ChatPage;
