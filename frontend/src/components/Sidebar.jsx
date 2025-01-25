// components/Sidebar.js
import React, { useEffect, useState } from "react";
import {
  TextField,
  Box,
  ListItem,
  ListItemText,
  Divider,
  List,
  InputAdornment,
} from "@mui/material";
import { ChatItem } from "react-chat-elements";
import api from "../context/api";
import SearchIcon from "@mui/icons-material/Search";

const Sidebar = ({ setSelectedWorkspace }) => {
  const handleWorkspaceClick = (workspace) => {
    setSelectedWorkspace(workspace);
  };

  const [searchText, setSearchText] = useState("");
  const [filteredWorkspaces, setFilteredChats] = useState([]);

  useEffect(() => {
    const fetchWorkspaces = async () => {
      const response = await api.get(`/api/workspaces`);
      setFilteredChats(response.data);
    };
    fetchWorkspaces();
  }, []);

  const handleSearchChange = (event) => {
    const text = event.target.value.toLowerCase();
    setSearchText(text);

    console.log("search " + text);
  };

  return (
    <Box sx={{ width: "100%", height: "100vh", bgcolor: "#f4f5f7" }}>
      <TextField
        fullWidth
        placeholder="Search"
        variant="outlined"
        size="small"
        value={searchText}
        onChange={handleSearchChange}
        slotProps={{
          input: {
            startAdornment: (
              <InputAdornment position="start">
                <SearchIcon />
              </InputAdornment>
            ),
          },
        }}
      />
      <Divider />
      {filteredWorkspaces?.map((workspace) => (
        <ChatItem
          key={workspace.id}
          avatar={"/placeholder/50"}
          alt={workspace.name}
          title={workspace.name}
          subtitle={workspace.description || "No description available"}
          date={new Date()}
          unread={0}
          onClick={() => handleWorkspaceClick(workspace)}
        />
      ))}
    </Box>
  );
};

export default Sidebar;
