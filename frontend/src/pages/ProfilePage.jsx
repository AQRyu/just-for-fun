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

function Profile() {
  const [profile, setProfile] = useState(null);
  const [nickName, setNickName] = useState("");
  const [email, setEmail] = useState("");
  const [bio, setBio] = useState("");
  const [profilePictureURL, setProfilePictureURL] = useState("");
  const [redirectToLogin, setRedirectToLogin] = useState(false);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const token = localStorage.getItem("user");

        if (!token) {
          console.error("No token found. Please log in.");
          setRedirectToLogin(true);
          return;
        }

        const url = `${process.env.REACT_APP_BACKEND_URL}/me`;
        const response = await fetch(url, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (!response.ok) {
          if (response.status === 401) {
            console.error("Unauthorized. Token may be expired.");
            localStorage.removeItem("user");
            setRedirectToLogin(true);
            return;
          }
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        setProfile(data);
        setNickName(data.nickName || "");
        setEmail(data.email || "");
        setBio(data.bio || "");
        setProfilePictureURL(data.profilePictureURL || "");
      } catch (error) {
        console.error("Error fetching profile:", error);
      }
    };

    fetchProfile();
  }, []);

  const updateProfile = async () => {
    try {
      const token = localStorage.getItem("user");
      if (!token) {
        console.error("No token found for update. Redirecting to login.");
        setRedirectToLogin(true);
        return;
      }

      const url = `${process.env.REACT_APP_BACKEND_URL}/me`;
      const response = await fetch(url, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          nickName,
          email,
          bio,
          profilePictureURL,
        }),
      });

      if (!response.ok) {
        if (response.status === 401) {
          console.error("Unauthorized. Token may be expired.");
          localStorage.removeItem("user");
          setRedirectToLogin(true);
          return;
        }
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const updatedProfile = await response.json();
      setProfile(updatedProfile);
    } catch (error) {
      console.error("Error updating profile:", error);
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

export default Profile;
