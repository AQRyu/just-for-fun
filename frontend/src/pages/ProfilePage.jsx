import {
  Avatar,
  Box,
  Button,
  Container,
  TextField,
  Typography,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import api from "../context/api";

function ProfilePage() {
  const [profile, setProfile] = useState(null);
  const [nickName, setNickName] = useState("");
  const [email, setEmail] = useState("");
  const [bio, setBio] = useState("");
  const [profilePictureURL, setProfilePictureURL] = useState("");
  const [redirectToLogin, setRedirectToLogin] = useState(false);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const response = await api.get(`/me`);
        const data = response.data;
        setProfile(data);
        setNickName(data.nickName || "");
        setEmail(data.email || "");
        setBio(data.bio || "");
        setProfilePictureURL(data.profilePictureURL || "");
      } catch (error) {
        if (error.response) {
          if (error.response.status === 401) {
            console.error("Unauthorized. Token may be expired.");
            localStorage.removeItem("user");
            setRedirectToLogin(true);
            return;
          }
          console.error(
            "Error fetching profile (server responded):",
            error.response.data
          );
        } else if (error.request) {
          console.error("Error fetching profile (no response):", error.request);
        } else {
          console.error("Error fetching profile (setup):", error.message);
        }
      }
    };

    fetchProfile();
  }, []);

  const updateProfile = async () => {
    try {
      const response = await api.put(`/me`, {
        nickName,
        email,
        bio,
        profilePictureURL,
      });

      setProfile(response.data);
    } catch (error) {
      if (error.response) {
        if (error.response.status === 401) {
          console.error("Unauthorized. Token may be expired.");
          localStorage.removeItem("user");
          setRedirectToLogin(true);
          return;
        }
        console.error(
          "Error updating profile (server responded):",
          error.response.data
        );
      } else if (error.request) {
        console.error("Error updating profile (no response):", error.request);
      } else {
        console.error("Error updating profile (setup):", error.message);
      }
    }
  };

  if (redirectToLogin) {
    return <Navigate to="/login" />;
  }

  if (!profile) {
    return <div>Loading profile...</div>;
  }

  return (
    <Container maxWidth="sm" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" align="center" gutterBottom>
        Profile
      </Typography>
      <Box
        sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}
      >
        <Avatar
          alt="Profile Picture"
          src={profilePictureURL || ""}
          sx={{ width: 100, height: 100, mb: 2 }}
        />
        <TextField
          label="Nickname"
          value={nickName}
          onChange={(e) => setNickName(e.target.value)}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Email"
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Bio"
          multiline
          rows={4}
          value={bio}
          onChange={(e) => setBio(e.target.value)}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Profile Picture URL"
          value={profilePictureURL}
          onChange={(e) => setProfilePictureURL(e.target.value)}
          fullWidth
          margin="normal"
        />
        <Button variant="contained" onClick={updateProfile} sx={{ mt: 2 }}>
          Update Profile
        </Button>
      </Box>
    </Container>
  );
}

export default ProfilePage;
