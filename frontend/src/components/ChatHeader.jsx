import React from "react";
import { Box, Avatar, Typography, IconButton } from "@mui/material";
import { ArrowBack, MoreVert } from "@mui/icons-material";

function ChatHeader({ selectedWorkspace }) {
  if (!selectedWorkspace) {
    return (
      <Box p={2} borderBottom="1px solid #e0e0e0">
        Select a chat
      </Box>
    );
  }

  return (
    <Box
      p={2}
      display="flex"
      alignItems="center"
      borderBottom="1px solid #e0e0e0"
    >
      <IconButton>
        <ArrowBack />
      </IconButton>
      <Avatar
        alt={selectedWorkspace.alt}
        src={selectedWorkspace.avatar}
        sx={{ mr: 1 }}
      />
      <Typography variant="h6">{selectedWorkspace.title}</Typography>
      <Box flexGrow={1} />
      <IconButton>
        <MoreVert />
      </IconButton>
    </Box>
  );
}

export default ChatHeader;
