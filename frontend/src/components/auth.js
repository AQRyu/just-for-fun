// auth.js

export const handleLogout = async () => {
  localStorage.removeItem("user");
  console.log("Logout successfully!");
};
