import { Button, List, ListItem, TextField } from "@mui/material";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const GroupManagementPage = () => {
  const { groupId } = useParams();
  const navigate = useNavigate();
  const { isAuthenticated, handleLogout } = useAuth();
  const [members, setMembers] = useState([]);
  const [newMemberId, setNewMemberId] = useState("");
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
      return;
    }

    const fetchMembers = async () => {
      try {
        const token = localStorage.getItem("user");
        const response = await axios.get(
          `${process.env.REACT_APP_BACKEND_URL}/api/groupchat/groups/${groupId}/members`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setMembers(response.data);
      } catch (err) {
        setError(
          "Error fetching group members. Are you authorized to manage this group?"
        );
        console.error("Error fetching group members:", err);
        if (err.response && err.response.status === 401) {
          handleLogout();
        }
      }
    };

    fetchMembers();
  }, [groupId, isAuthenticated, navigate, handleLogout]);

  const handleAddMember = async () => {
    try {
      const token = localStorage.getItem("user");
      const response = await axios.post(
        `${process.env.REACT_APP_BACKEND_URL}/api/groupchat/groups/${groupId}/members`,
        [newMemberId],
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setMembers(response.data);
      debugger;
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
        `${process.env.REACT_APP_BACKEND_URL}/api/groupchat/groups/${groupId}/members`,
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
    <div>
      <h1>Manage Group {groupId}</h1>

      {error && <div style={{ color: "red" }}>{error}</div>}

      <h2>Members</h2>
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
      ) : (
        <p>Loading members... Or No members found.</p>
      )}

      <h2>Add Member</h2>
      <TextField
        label="Member ID"
        value={newMemberId}
        onChange={(e) => setNewMemberId(e.target.value)}
      />
      <Button onClick={handleAddMember}>Add Member</Button>
    </div>
  );
};

export default GroupManagementPage;
