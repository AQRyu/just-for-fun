import React, { useState } from "react";

function CreateGroupForm() {
  const [groupName, setGroupName] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const token = localStorage.getItem("user");

      const response = await fetch(
        `${process.env.REACT_APP_BACKEND_URL}/api/groupchat/groups`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({ name: groupName }),
        }
      );

      if (response.ok) {
        const data = await response.json();
        console.log("Group created:", data);
      } else {
        const errorData = await response.json();
        setError(errorData.message || "Failed to create group");
      }
    } catch (error) {
      console.error("Error creating group:", error);
      setError("An error occurred. Please try again later.");
    }
  };

  return (
    <div>
      <h2>Create New Group</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="groupName">Group Name:</label>
          <input
            type="text"
            id="groupName"
            value={groupName}
            onChange={(e) => setGroupName(e.target.value)}
            required
          />
        </div>
        {error && <div style={{ color: "red" }}>{error}</div>}
        <button type="submit">Create Group</button>
      </form>
    </div>
  );
}

export default CreateGroupForm;
