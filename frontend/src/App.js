import React from "react";
import { Link, NavLink, Route, Routes } from "react-router-dom";
import { handleLogout } from "./components/auth";
import Chat from "./components/Chat";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Profile from "./pages/Profile";

function App() {
  return (
    <div>
      <nav>
        <Link to="/profile">Profile</Link>
        <Link to="/chat">Chat</Link>
        <Link to="/login">Login</Link>
        <NavLink
          to="/"
          onClick={handleLogout}
          style={({ isActive }) => ({
            color: isActive ? "red" : "blue",
          })}
        >
          Logout
        </NavLink>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/chat" element={<Chat />} />
        <Route path="*" element={<p>Page not Found</p>} />{" "}
      </Routes>
    </div>
  );
}

export default App;
