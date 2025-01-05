import React from "react";
import { Link, Route, Routes } from "react-router-dom";
import Chat from "./components/Chat";
import Profile from "./components/Profile";
import Home from "./pages/Home";

function App() {
  return (
    <div>
      <nav>
        <Link to="/profile">Profile</Link>
        <Link to="/chat">Chat</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/chat" element={<Chat />} />
        <Route path="*" element={<p>Page not Found</p>} />{" "}
      </Routes>
    </div>
  );
}

export default App;
