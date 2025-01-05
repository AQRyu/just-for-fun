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
    <div>
      <input
        type="text"
        value={nickName}
        onChange={(e) => setNickName(e.target.value)}
        placeholder="Nickname"
      />
      <input
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="Email"
      />
      <textarea
        value={bio}
        onChange={(e) => setBio(e.target.value)}
        placeholder="Bio"
      />
      <input
        type="text"
        value={profilePictureURL}
        onChange={(e) => setProfilePictureURL(e.target.value)}
        placeholder="Profile Picture URL"
      />
      <button onClick={updateProfile}>Update Profile</button>
    </div>
  );
}

export default Profile;
