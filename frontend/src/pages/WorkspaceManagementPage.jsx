import {
  Alert,
  Button,
  CircularProgress,
  Container,
  Grid2,
  List,
  ListItem,
  TextField,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const WorkspaceManagementPage = () => {
  const { workspaceId } = useParams();
  const navigate = useNavigate();
  const { isAuthenticated, handleLogout } = useAuth();
  const [members, setMembers] = useState([]);
  const [newMemberId, setNewMemberId] = useState("");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
      return;
    }

    const fetchMembers = async () => {
      setLoading(true);
      try {
        const token = localStorage.getItem("user");
        const response = await axios.get(
          `${process.env.REACT_APP_BACKEND_URL}/api/workspaces/${workspaceId}/members`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setMembers(response.data);
      } catch (err) {
        setError(
          "Error fetching workspace members. Are you authorized to manage this workspace?"
        );
        console.error("Error fetching workspace members:", err);
        if (err.response && err.response.status === 401) {
          handleLogout();
        }
      } finally {
        setLoading(false);
      }
    };

    fetchMembers();
  }, [workspaceId, isAuthenticated, navigate, handleLogout]);

  const handleAddMember = async () => {
    try {
      const token = localStorage.getItem("user");
      const response = await axios.post(
        `${process.env.REACT_APP_BACKEND_URL}/api/workspaces/${workspaceId}/members`,
        [newMemberId],
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setMembers(response.data);
      setNewMemberId("");
    } catch (err) {
      setError(
        "Error adding member. Please check that the user ID is valid and that you have permission."
      );
      console.error("Error adding member:", err);
    }
  };

  const handleRemoveMember = async (memberId) => {
    try {
      const token = localStorage.getItem("user");
      await axios.delete(
        `${process.env.REACT_APP_BACKEND_URL}/api/workspaces/${workspaceId}/members`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          data: [memberId],
        }
      );
      setMembers(members.filter((member) => member.id !== memberId));
    } catch (err) {
      setError("Error removing member.");
      console.error("Error removing member:", err);
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: "10vh" }}>
      <typography>Manage Workspace {workspaceId}</typography>

      {loading && (
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            marginTop: "1rem",
          }}
        >
          <CircularProgress />
        </div>
      )}

      {error && (
        <Alert severity="error" sx={{ mt: 2 }}>
          {error}
        </Alert>
      )}

      <Grid2 container spacing={2} sx={{ mt: 2 }}>
        <Grid2 item xs={12} md={6}>
          {members && members.length > 0 ? (
            <List>
              {members.map((member) => (
                <ListItem key={member.id}>
                  {member.username}
                  <Button onClick={() => handleRemoveMember(member.id)}>
                    Remove
                  </Button>
                </ListItem>
              ))}
            </List>
          ) : !loading ? (
            <Typography variant="body1">No members found.</Typography>
          ) : null}
        </Grid2>
      </Grid2>

      <Grid2 item xs={12} md={8}>
        <TextField
          label="Member ID"
          variant="outlined"
          fullWidth
          value={newMemberId}
          onChange={(e) => setNewMemberId(e.target.value)}
        />
      </Grid2>
      <Grid2 item xs={12} md={4}>
        <Button onClick={handleAddMember} variant="contained" fullWidth>
          Add Member
        </Button>
      </Grid2>
    </Container>
  );
};

export default WorkspaceManagementPage;
