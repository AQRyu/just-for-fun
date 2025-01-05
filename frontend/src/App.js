import React from "react";
import { Link, Route, Routes } from "react-router-dom";
import Chat from "./components/Chat";
import Profile from "./components/Profile";
import Home from "./pages/Home";
import Login from "./pages/Login";

function App() {
  return (
    <div>
      <nav>
        <Link to="/profile">Profile</Link>
        <Link to="/chat">Chat</Link>
        <Link to="/login">Login</Link>
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
